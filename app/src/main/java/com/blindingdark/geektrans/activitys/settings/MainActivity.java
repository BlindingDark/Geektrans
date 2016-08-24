package com.blindingdark.geektrans.activitys.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;


import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.activitys.StringMainSettings;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //加引擎的地方
    CheckBox checkBoxYoudao;
    CheckBox checkBoxBaidu;
    CheckBox checkBoxJinshan;
    ArrayList<CheckBox> checkBoxesEngines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        //加引擎的地方
        checkBoxYoudao = (CheckBox) findViewById(R.id.checkBoxUseYoudao);
        checkBoxBaidu = (CheckBox) findViewById(R.id.checkBoxUseBaidu);
        checkBoxJinshan = (CheckBox) findViewById(R.id.checkBoxUseJinshan);
        checkBoxesEngines.add(checkBoxBaidu);
        checkBoxesEngines.add(checkBoxYoudao);
        checkBoxesEngines.add(checkBoxJinshan);

        String nowTransEngine = preferences.getString(StringMainSettings.nowTransEngine, StringMainSettings.youdaoTransEngine);

        //加引擎的地方，一定要修改按钮和单选框点击事件！！！
        if (nowTransEngine.equals(StringMainSettings.youdaoTransEngine)) {
            checkBoxYoudao.setChecked(true);
        }

        if (nowTransEngine.equals(StringMainSettings.baiduTransEngine)) {
            checkBoxBaidu.setChecked(true);
        }

        if (nowTransEngine.equals(StringMainSettings.jinshanTransEngine)) {
            checkBoxJinshan.setChecked(true);
        }
    }

    // 设置跳转事件

    public void jumpToBaiduSettings(View view) {
        Intent transIntent = new Intent();
                     /* 指定intent要启动的类 */
        transIntent.setClass(this, BaiduSettingsActivity.class);
                     /* 启动一个新的Activity */
        this.startActivity(transIntent);
                    /* 关闭当前的Activity */
    }

    public void jumpToYoudaoSettings(View view) {
        Intent transIntent = new Intent();
                     /* 指定intent要启动的类 */
        transIntent.setClass(this, YoudaoSettingsActivity.class);
                     /* 启动一个新的Activity */
        this.startActivity(transIntent);
                    /* 关闭当前的Activity */
    }

    public void jumpToJinshanSettings(View view) {
        Intent transIntent = new Intent();
                     /* 指定intent要启动的类 */
        transIntent.setClass(this, JinshanSettingsActivity.class);
                     /* 启动一个新的Activity */
        this.startActivity(transIntent);
                    /* 关闭当前的Activity */

    }

    // 单选框点击事件

    public void chooseYoudao(View view) {
        if (checkBoxYoudao.isChecked()) {
            for (CheckBox box : checkBoxesEngines) {
                box.setChecked(false);
            }
            editor.putString(StringMainSettings.nowTransEngine, StringMainSettings.youdaoTransEngine);
            editor.commit();
        }
        checkBoxYoudao.setChecked(true);
    }

    public void chooseBaidu(View view) {
        if (checkBoxBaidu.isChecked()) {
            for (CheckBox box : checkBoxesEngines) {
                box.setChecked(false);
            }
            editor.putString(StringMainSettings.nowTransEngine, StringMainSettings.baiduTransEngine);
            editor.commit();
        }
        checkBoxBaidu.setChecked(true);

    }


    public void chooseJinshan(View view) {
        if (checkBoxJinshan.isChecked()) {
            for (CheckBox box : checkBoxesEngines) {
                box.setChecked(false);
            }
            editor.putString(StringMainSettings.nowTransEngine, StringMainSettings.jinshanTransEngine);
            editor.commit();
        }
        checkBoxJinshan.setChecked(true);
    }
}
