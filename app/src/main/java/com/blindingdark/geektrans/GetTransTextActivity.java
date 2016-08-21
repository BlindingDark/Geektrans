package com.blindingdark.geektrans;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.trans.Translator;
import com.blindingdark.geektrans.trans.youdao.Youdao;
import com.blindingdark.geektrans.trans.youdao.YoudaoTransReq;
import com.blindingdark.geektrans.trans.youdao.bean.ReadableTransResults;
import com.blindingdark.geektrans.trans.youdao.YoudaoJSONDeal;

/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public class GetTransTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    private void processIntent(Intent intent) {

        String action = intent.getAction();


        if (Intent.ACTION_PROCESS_TEXT.equals(action)) {
            // Text shared with app via Intent
            CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);


            if (!TextUtils.isEmpty(text)) {
                if (text.length() > 200) {
                    //  2016/8/20 0020 200字符提示！！！
                    Toast.makeText(getApplicationContext(), "整句已超出 200 字符", Toast.LENGTH_LONG).show();
                } else {
                    // 2016/8/21 0021 提取方法
                    Translator.trans(text.toString(), new Youdao(), myHandler);// 这里指定翻译API

                }

            }
        }
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
