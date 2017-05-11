package com.blindingdark.geektrans.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public class GetTransTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processIntent(getIntent());
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        String action = intent.getAction();
        String text = null;

        if (Intent.ACTION_PROCESS_TEXT.equals(action)) {
            // Text shared with app via Intent
            text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();
        }

        if (Intent.ACTION_SEND.equals(action)) {
            // Text shared with app via Intent
            text = intent.getStringExtra(Intent.EXTRA_TEXT);
        }

        if (!TextUtils.isEmpty(text)) {
            Intent transIntent = new Intent();
            transIntent.putExtra("req", text);
                     /* 指定intent要启动的类 */
            transIntent.setClass(this, TransActivity.class);
                     /* 启动一个新的Activity */
            this.startActivity(transIntent);
        }

    }


}
