package com.blindingdark.geektrans.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.global.SeqMainSettings;
import com.blindingdark.geektrans.global.StringMainSettings;
import com.blindingdark.geektrans.global.ToastStyleFactory;
import com.blindingdark.geektrans.global.TransEngineFactory;
import com.blindingdark.geektrans.tools.Clip;
import com.blindingdark.geektrans.tools.EnginesCircleList;
import com.blindingdark.geektrans.tools.MyStringUnits;
import com.blindingdark.geektrans.tools.MyToast;
import com.blindingdark.geektrans.tools.SoundPlayerService;

import java.util.HashSet;
import java.util.Set;


public class TransActivity extends Activity {

    public static final int normalToast = 0;
    public static final int haveSoundToast = 1;

    SharedPreferences preferences;

    public void setToastTime(String stringToastTime) {
        double time = Double.parseDouble(stringToastTime) * 1000;

        if (time > Integer.MAX_VALUE) {
            this.toastTime = Integer.MAX_VALUE;
        } else {
            this.toastTime = (int) time;
        }

    }

    int toastTime = Toast.LENGTH_LONG;
    String nowTransEngine;
    Set<String> defaultTransSet;
    HashSet<String> nowTransEngines;
    EnginesCircleList<String> enginesCircleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        nowTransEngine = preferences
                .getString(StringMainSettings.NOW_TRANS_ENGINE,
                        StringMainSettings.YOUDAO_TRANS_ENGINE);

        defaultTransSet = SeqMainSettings.getDefaultEngines();
        // 得到当前已添加的引擎列表
        nowTransEngines = (HashSet<String>) preferences.getStringSet(StringMainSettings.NOW_ENGINE_LIST, defaultTransSet);
        enginesCircleList = new EnginesCircleList<>(nowTransEngines).setNowIndex(nowTransEngine);

        processIntent(getIntent());
        finish();
    }

    private void processIntent(Intent intent) {
        String req = intent.getStringExtra("req");
        this.trans(req);
    }


    void trans(String req) {
        this.trans(req, nowTransEngine);
    }

    void trans(String req, String nowTransEngine) {
        req = MyStringUnits.filterBlankSpace(req);

        String stringToastTime = preferences.getString(nowTransEngine + StringMainSettings.TOAST_TIME, "3.5");
        this.setToastTime(stringToastTime);

        TransEngine transEngine = TransEngineFactory.getTransEngine(nowTransEngine, preferences);
        transEngine.trans(req, soundHandler);
    }

    Result result;

    Handler soundHandler = new Handler() { // 先经过声音处理
        @Override
        public void handleMessage(Message msg) {

            result = (Result) msg.obj;

            String nowSoundEngine = preferences.getString(StringMainSettings.DEFAULT_SOUND_ENGINE, "");
            if (!"".equals(nowSoundEngine)) {
                // 发起搜索
                if (!nowSoundEngine.equals(result.getFromEngineName())) {
                    TransEngine transEngine = TransEngineFactory.getTransEngine(nowSoundEngine, preferences);
                    transEngine.trans(result.getOriginalReq(), showHandler);
                    return;
                }
            }

            Message message = new Message();

            message.what = result.getWhat();
            message.obj = result;

            showHandler.sendMessage(message);
        }

    };


    Handler showHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            final MyToast toast = new MyToast(getApplicationContext());
            Result soundResult = (Result) msg.obj;
            if (!(soundResult == result)) {// 如果经过声音过滤，就混合两次结果
                if (!soundResult.getSoundURLs().isEmpty()) {
                    result.setSoundURLs(soundResult.getSoundURLs());
                    result.setWhat(TransActivity.haveSoundToast);
                }
            }

            final Result finalResult = result;

            LayoutInflater inflater = getLayoutInflater();
            ToastStyleFactory toastStyleFactory = new ToastStyleFactory(inflater);
            String style = preferences.getString(StringMainSettings.NOW_TOAST_STYLE, "1");// 得到当前的气泡 UI 类型

            final View viewToastSimple = toastStyleFactory.getSimpleToastStyleById(style);// 没有声音的自定气泡
            final View viewToastWitSound = toastStyleFactory.getSoundToastStyleById(style);// 有声音的自定气泡

            viewToastWitSound.setOnTouchListener(getToastMoveListener(toast, viewToastWitSound));// 设置Toast触摸动作
            viewToastSimple.setOnTouchListener(getToastMoveListener(toast, viewToastSimple));

            TextView textViewToastSimpleTextResult = (TextView) viewToastSimple.findViewById(R.id.textViewToastSimpleTextResult);
            TextView textViewToastWithSoundTextResult = (TextView) viewToastWitSound.findViewById(R.id.textViewToastWithSoundTextResult);

            View.OnLongClickListener copyResultLongClickListener = new View.OnLongClickListener() { // 长按文字复制结果
                @Override
                public boolean onLongClick(View view) {
                    if (moveState.isMoved()) {
                        return false;
                    }

                    copyResult(finalResult.getStringResult());
                    Toast.makeText(getApplicationContext(), "已复制",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
            };

            View.OnLongClickListener nextEngineResultLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (moveState.isMoved()) {
                        return false;
                    }

                    trans(result.getOriginalReq(), enginesCircleList.next());
                    toast.cancel();
                    return true;
                }
            };


            String LongClickToastAct = preferences.getString(StringMainSettings.LONG_CLICK_TOAST_ACT, "1");// 得到当前的长按类型

            switch (LongClickToastAct) {
                case "0": {
                    viewToastWitSound.setOnLongClickListener(copyResultLongClickListener);
                    viewToastSimple.setOnLongClickListener(copyResultLongClickListener);
                    break;
                }
                case "1": {
                    viewToastWitSound.setOnLongClickListener(nextEngineResultLongClickListener);
                    viewToastSimple.setOnLongClickListener(nextEngineResultLongClickListener);
                    break;
                }
            }

            if (TextUtils.isEmpty(finalResult.getStringResult())) {
                textViewToastSimpleTextResult.setText("极客姬..查询..失败...");
                toast.setView(viewToastSimple).setDuration(2000).show();
                return;
            }

            switch (msg.what) {
                case normalToast: {
                    // 通用气泡样式
                    textViewToastSimpleTextResult.setText(finalResult.getStringResult());
                    toast.setView(viewToastSimple).setDuration(toastTime).show();
                    break;
                }
                case haveSoundToast: {
                    // 带声音的样式
                    //  2016/8/23 0023  在自定义布局里面添加文字
                    textViewToastWithSoundTextResult.setText(finalResult.getStringResult());
                    // 添加自定义气泡声音点击事件
                    viewToastWitSound.findViewById(R.id.buttonPlaySound).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putStringArrayListExtra("soundList", finalResult.getSoundURLs());
                            intent.setClass(TransActivity.this, SoundPlayerService.class);
                            startService(intent);
                            stopService(intent);

                        }
                    });
                    toast.setView(viewToastWitSound).setDuration(toastTime).show();
                    break;
                }
            }
            // 复制到剪贴板
            copyResultByPref(finalResult.getStringResult());
            super.handleMessage(msg);
        }
    };

    final MoveState moveState = new MoveState(); // 表示是否被移动过，只有在不移动只长按的时候才触发

    View.OnTouchListener getToastMoveListener(final MyToast toast, final View toastView) {
        return new View.OnTouchListener() {
            final WindowManager.LayoutParams mParams = toast.getParams();
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            int removeToastCount = 2;// 双击关闭

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toast.pause();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下事件
                        //获取初始位置
                        initialX = mParams.x;
                        initialY = mParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE://移动事件
                        int deltaX = (int) (event.getRawX() - initialTouchX);
                        int deltaY = (int) (event.getRawY() - initialTouchY);

                        if ((deltaX * deltaX) + (deltaY * deltaY) > 100) {// 移动范围大于阈值时，才是移动事件
                            mParams.x = initialX + deltaX;
                            mParams.y = initialY - deltaY;
                            //更新界面
                            windowManager.updateViewLayout(toastView, mParams);//这是更新View在界面的位置
                            removeToastCount = 2;
                            moveState.setMoved(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP://抬起事件
                        if (!moveState.isMoved()) {
                            removeToastCount--;
                            if (removeToastCount == 0) {
                                toast.cancel();// 双击关闭
                            }
                        }
                        moveState.setMoved(false);
                        toast.start();
                        break;
                }
                return false; // return false 表示后面还有其他事件发生，要传递下去
            }
        };

    }

    private boolean isAutoCopyOpen() {
        return preferences.getBoolean(StringMainSettings.IS_AUTO_COPY_OPEN, true);
    }

    void copyResultByPref(String result) {
        // 复制到剪贴板
        //自动复制
        if (isAutoCopyOpen()) {
            Clip.copyResult(result, (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
        }
    }

    void copyResult(String result) {
        // 复制到剪贴板
        Clip.copyResult(result, (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
    }

    class MoveState {
        boolean moved = false;

        public void setMoved(boolean moved) {
            this.moved = moved;
        }

        public boolean isMoved() {
            return moved;
        }
    }

}

