package com.blindingdark.geektrans;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.trans.Youdao;
import com.blindingdark.geektrans.bean.ReadableTransResults;
import com.blindingdark.geektrans.json.YoudaoJSONDeal;

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
                    TransReqThread myTransReqThread = new TransReqThread(new Youdao(text.toString()));// 这里指定翻译API
                    new Thread(myTransReqThread).start();
                }

            }
        }
        finish();
    }

    public class TransReqThread implements Runnable {
        TransReq transReq;
        String strResult = "error";

        public TransReqThread(TransReq req) {
            transReq = req;
        }

        @Override
        public void run() {
            strResult = transReq.getTrans();

            Message message = new Message();
            message.what = 0;
            message.obj = strResult;

            GetTransTextActivity.this.myHandler.sendMessage(message);
        }
    }


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String JSONRes = (String) msg.obj;
                    if (JSONRes.equals("")) {
                        Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_LONG).show();
                    } else {
                        ReadableTransResults readableResults = YoudaoJSONDeal.getResults(JSONRes);// 这里指定解析JSON的类
                        Toast.makeText(getApplicationContext(), readableResults.toString(), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
