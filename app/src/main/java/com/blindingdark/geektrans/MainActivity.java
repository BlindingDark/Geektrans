package com.blindingdark.geektrans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent transIntent = new Intent();
                     /* 指定intent要启动的类 */
        transIntent.setClass(this, YoudaoSettingsActivity.class);
                     /* 启动一个新的Activity */
        this.startActivity(transIntent);
                    /* 关闭当前的Activity */
        this.finish();
    }
}
