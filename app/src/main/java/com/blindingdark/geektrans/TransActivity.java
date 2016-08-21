package com.blindingdark.geektrans;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.blindingdark.geektrans.trans.Translator;
import com.blindingdark.geektrans.trans.youdao.Settings;
import com.blindingdark.geektrans.trans.youdao.Youdao;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

public class TransActivity extends Activity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        processIntent(getIntent());
    }

    void trans(String req) {
        String key = preferences.getString(Settings.key,"");
        String keyfrom = preferences.getString(Settings.keyfrom,"");
        String divLine = preferences.getString(Settings.divisionLine,Settings.defaultDivLine);

        Translator.trans(req, new Youdao(new YoudaoSettings(key,keyfrom,divLine)), myHandler);// 这里指定翻译API
    }

    private void processIntent(Intent intent) {
        String req = intent.getStringExtra("req");
        this.trans(req);
        finish();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String result = (String) msg.obj;
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
