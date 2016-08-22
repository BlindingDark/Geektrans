package com.blindingdark.geektrans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;


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
                    Intent transIntent = new Intent();
                    transIntent.putExtra("req", text.toString());
                     /* 指定intent要启动的类 */
                    transIntent.setClass(this, TransActivity.class);
                     /* 启动一个新的Activity */
                    this.startActivity(transIntent);
                    /* 关闭当前的Activity */
                    this.finish();
                }

            }
        }
        finish();
    }


}
