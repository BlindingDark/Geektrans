package com.blindingdark.geektrans.activitys;

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
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.tools.Clip;
import com.blindingdark.geektrans.tools.MyStringUnits;
import com.blindingdark.geektrans.tools.MyToast;
import com.blindingdark.geektrans.tools.SoundPlayer;
import com.blindingdark.geektrans.trans.Translator;
import com.blindingdark.geektrans.trans.baidu.Baidu;
import com.blindingdark.geektrans.trans.baidu.BaiduSettingsString;
import com.blindingdark.geektrans.trans.baidu.bean.BaiduSettings;
import com.blindingdark.geektrans.trans.bing.Bing;
import com.blindingdark.geektrans.trans.bing.StringBingSettings;
import com.blindingdark.geektrans.trans.bing.bean.BingSettings;
import com.blindingdark.geektrans.trans.jinshan.Jinshan;
import com.blindingdark.geektrans.trans.jinshan.StringJinshanSettings;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;
import com.blindingdark.geektrans.trans.youdao.Youdao;
import com.blindingdark.geektrans.trans.youdao.YoudaoSettingsString;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

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
    }

    // 加引擎的地方
    void trans(String req) {
        req = MyStringUnits.filterBlankSpace(req);

        String nowTransEngine = preferences.getString(StringMainSettings.nowTransEngine, StringMainSettings.youdaoTransEngine);

        switch (nowTransEngine) {
            case StringMainSettings.youdaoTransEngine: {
                String stringToastTime = preferences.getString(YoudaoSettingsString.youdaoToastTime, YoudaoSettingsString.youdaoDefToastTime);
                this.setToastTime(stringToastTime);
                Translator.trans(req, new Youdao(new YoudaoSettings(preferences)), myHandler, preferences);
                break;
            }
            case StringMainSettings.baiduTransEngine: {
                String stringToastTime = preferences.getString(BaiduSettingsString.baiduToastTime, BaiduSettingsString.defBaiduToastTime);
                this.setToastTime(stringToastTime);
                Translator.trans(req, new Baidu(new BaiduSettings(preferences)), myHandler, preferences);
                break;
            }

            case StringMainSettings.jinshanTransEngine: {
                String stringToastTime = preferences.getString(StringJinshanSettings.jinshanToastTime, StringJinshanSettings.defJinshanToastTime);
                this.setToastTime(stringToastTime);
                Translator.trans(req, new Jinshan(new JinshanSettings(preferences)), myHandler, preferences);
                break;
            }

            case StringMainSettings.bingTransEngine: {
                String stringToastTime = preferences.getString(StringBingSettings.bingToastTime, StringBingSettings.defBingToastTime);
                this.setToastTime(stringToastTime);
                Translator.trans(req, new Bing(new BingSettings(preferences)), myHandler, preferences);
                break;
            }
        }


    }

    private void processIntent(Intent intent) {
        String req = intent.getStringExtra("req");
        this.trans(req);
        finish();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {

            final MyToast toast = new MyToast(getApplicationContext());
            final Result result = (Result) msg.obj;
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
                    if (TextUtils.isEmpty(result.getStringResult())) {
                        //MyToast.makeText(getApplicationContext(), "极客姬..查询..失败...", 2000).show();
                        textViewToastSimpleTextResult.setText("极客姬..查询..失败...");
                        toast.setView(viewToastSimple).setDuration(2000).show();
                    } else {
                        // MyToast.makeText(getApplicationContext(), result.getStringResult(), toastTime).show();
                        textViewToastSimpleTextResult.setText(result.getStringResult());
                        toast.setView(viewToastSimple).setDuration(toastTime).show();
                        // 复制到剪贴板
                        Clip.copyResult(result.getStringResult(), (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                    }
                    break;
                }
                case haveSoundToast: {
                    // 带声音的样式
                    if (TextUtils.isEmpty(result.getStringResult())) {
                        textViewToastSimpleTextResult.setText("极客姬..查询..失败...");
                        toast.setView(viewToastSimple).setDuration(2000).show();
                    } else {
                        //  2016/8/23 0023  在自定义布局里面添加文字
                        textViewToastWithSoundTextResult.setText(result.getStringResult());
                        // 添加自定义气泡声音点击事件
                        viewToastWitSound.findViewById(R.id.buttonPlaySound).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putStringArrayListExtra("soundList", result.getSoundURLs());
                                intent.setClass(TransActivity.this, SoundPlayer.class);
                                startService(intent);
                                stopService(intent);

                            }
                        });
                        toast.setView(viewToastWitSound).setDuration(toastTime).show();
                        // 复制到剪贴板
                        Clip.copyResult(result.getStringResult(), (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
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
                        mParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        mParams.y = initialY - (int) (event.getRawY() - initialTouchY);
                        //更新界面
                        windowManager.updateViewLayout(toastView, mParams);//这是更新View在界面的位置
                        removeToastCount = 2;
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

