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
import android.view.View;
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
            switch (msg.what) {
                case normalToast: {
                    // 通用气泡样式
                    Result result = (Result) msg.obj;
                    if (TextUtils.isEmpty(result.getStringResult())) {
                        MyToast.makeText(getApplicationContext(), "极客姬..查询..失败...", 2000).show();
                    } else {
                        MyToast.makeText(getApplicationContext(), result.getStringResult(), toastTime).show();
                        Clip.copyResult(result.getStringResult(), (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                    }
                    break;
                }
                case haveSoundToast: {
                    final Result result = (Result) msg.obj;
                    if (TextUtils.isEmpty(result.getStringResult())) {
                        MyToast.makeText(getApplicationContext(), "极客姬..查询..失败...", 2000).show();
                    } else {
                        LayoutInflater inflater = getLayoutInflater();
                        View view = inflater.inflate(R.layout.jinshan_toast, null);// 得到自定气泡

                        //  2016/8/23 0023  在自定义布局里面添加文字
                        TextView tVResult = (TextView) view.findViewById(R.id.textViewJinshanResult);
                        tVResult.setText(result.getStringResult());
                        // 添加自定义气泡点击事件

                        view.findViewById(R.id.buttonJinshanSound).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putStringArrayListExtra("soundList", result.getSoundURLs());
                                intent.setClass(TransActivity.this, SoundPlayer.class);
                                startService(intent);
                                stopService(intent);

                            }
                        });


                        MyToast jinshanToast = new MyToast(getApplicationContext());
                        jinshanToast.setView(view).setDuration(toastTime).show();
                        Clip.copyResult(result.getStringResult(), (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                    }
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };
}

