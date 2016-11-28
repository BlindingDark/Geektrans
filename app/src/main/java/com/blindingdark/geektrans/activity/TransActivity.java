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
import com.blindingdark.geektrans.global.StringMainSettings;
import com.blindingdark.geektrans.tools.Clip;
import com.blindingdark.geektrans.tools.MyStringUnits;
import com.blindingdark.geektrans.tools.MyToast;
import com.blindingdark.geektrans.tools.SoundPlayer;
import com.blindingdark.geektrans.global.TransEngineFactory;

public class TransActivity extends Activity {

    public static final int normalToast = 0;
    public static final int haveSoundToast = 1;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public void setToastTime(String stringToastTime) {
        this.toastTime = (int) Float.parseFloat(stringToastTime) * 1000;
    }

    int toastTime = Toast.LENGTH_LONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        processIntent(getIntent());
        finish();
    }

    private void processIntent(Intent intent) {
        String req = intent.getStringExtra("req");
        this.trans(req);
    }

    // 加引擎的地方
    void trans(String req) {
        req = MyStringUnits.filterBlankSpace(req);

        String nowTransEngine = preferences.getString(StringMainSettings.NOW_TRANS_ENGINE, StringMainSettings.YOUDAO_TRANS_ENGINE);

        String stringToastTime = preferences.getString(nowTransEngine + StringMainSettings.TOAST_TIME, "3.5");
        this.setToastTime(stringToastTime);

        TransEngine transEngine = TransEngineFactory.getTransEngine(nowTransEngine, preferences);
        transEngine.trans(req, soundHandler);


    }

    Result result;

    Handler soundHandler = new Handler() {
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
            final View viewToastSimple = inflater.inflate(R.layout.toast_simple, null);// 得到自定气泡
            final View viewToastWitSound = inflater.inflate(R.layout.toast_with_sound, null);// 得到自定气泡

            viewToastWitSound.setOnTouchListener(getToastMoveListener(toast, viewToastWitSound));// 设置Toast触摸动作！！
            viewToastSimple.setOnTouchListener(getToastMoveListener(toast, viewToastSimple));

            TextView textViewToastSimpleTextResult = (TextView) viewToastSimple.findViewById(R.id.textViewToastSimpleTextResult);
            TextView textViewToastWithSoundTextResult = (TextView) viewToastWitSound.findViewById(R.id.textViewToastWithSoundTextResult);


            switch (msg.what) {
                case normalToast: {
                    // 通用气泡样式
                    if (TextUtils.isEmpty(finalResult.getStringResult())) {
                        textViewToastSimpleTextResult.setText("极客姬..查询..失败...");
                        toast.setView(viewToastSimple).setDuration(2000).show();
                    } else {
                        textViewToastSimpleTextResult.setText(finalResult.getStringResult());
                        toast.setView(viewToastSimple).setDuration(toastTime).show();
                        // 复制到剪贴板
                        Clip.copyResult(finalResult.getStringResult(), (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                    }
                    break;
                }
                case haveSoundToast: {
                    // 带声音的样式
                    if (TextUtils.isEmpty(finalResult.getStringResult())) {
                        textViewToastSimpleTextResult.setText("极客姬..查询..失败...");
                        toast.setView(viewToastSimple).setDuration(2000).show();
                    } else {
                        //  2016/8/23 0023  在自定义布局里面添加文字
                        textViewToastWithSoundTextResult.setText(finalResult.getStringResult());
                        // 添加自定义气泡声音点击事件
                        viewToastWitSound.findViewById(R.id.buttonPlaySound).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putStringArrayListExtra("soundList", finalResult.getSoundURLs());
                                intent.setClass(TransActivity.this, SoundPlayer.class);
                                startService(intent);
                                stopService(intent);

                            }
                        });
                        toast.setView(viewToastWitSound).setDuration(toastTime).show();
                        // 复制到剪贴板
                        Clip.copyResult(finalResult.getStringResult(), (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                    }
                    break;
                }
            }


            super.handleMessage(msg);
        }
    };

    View.OnTouchListener getToastMoveListener(final MyToast toast, final View toastView) {
        return new View.OnTouchListener() {
            final WindowManager.LayoutParams mParams = toast.getParams();
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            int removeToastCount = 2;// 双击关闭
            boolean moveFlag = false;

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
                        moveFlag = true;
                        int deltaX = (int) (event.getRawX() - initialTouchX);
                        int deltaY = (int) (event.getRawY() - initialTouchY);
                        if ((deltaX * deltaX) + (deltaY * deltaY) > 100) {
                            mParams.x = initialX + deltaX;
                            mParams.y = initialY - deltaY;
                            //更新界面
                            windowManager.updateViewLayout(toastView, mParams);//这是更新View在界面的位置
                            removeToastCount = 2;

                        } else {
                            moveFlag = false;
                        }

                        break;
                    case MotionEvent.ACTION_UP://抬起事件
                        if (!moveFlag) {
                            removeToastCount--;
                            if (removeToastCount == 0) {
                                toast.cancel();// 双击关闭
                            }
                        }
                        moveFlag = false;
                        break;
                }
                toast.start();
                return true;
            }
        };

    }

}

