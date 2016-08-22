package com.blindingdark.geektrans;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CheckBox checkBoxYoudao;
    CheckBox checkBoxBaidu;
    ArrayList<CheckBox> checkBoxesEngines = new ArrayList<>();

    Button buttonBaiduSetting;
    Button buttonYoudaoSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();




        checkBoxYoudao = (CheckBox) findViewById(R.id.checkBoxUseYoudao);
        checkBoxBaidu = (CheckBox) findViewById(R.id.checkBoxUseBaidu);
        checkBoxesEngines.add(checkBoxBaidu);
        checkBoxesEngines.add(checkBoxYoudao);

        String nowTransEngine = preferences.getString(StringMainSettings.nowTransEngine, StringMainSettings.youdaoTransEngine);

        if (nowTransEngine.equals(StringMainSettings.youdaoTransEngine)){
            checkBoxYoudao.setChecked(true);
        }

        if (nowTransEngine.equals(StringMainSettings.baiduTransEngine)){
            checkBoxBaidu.setChecked(true);
        }

        checkBoxYoudao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (CheckBox box : checkBoxesEngines){
                        box.setChecked(false);
                    }
                    checkBoxYoudao.setChecked(true);
                    editor.putString(StringMainSettings.nowTransEngine, StringMainSettings.youdaoTransEngine);
                    editor.commit();
                }
            }
        });

        checkBoxBaidu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (CheckBox box : checkBoxesEngines){
                        box.setChecked(false);
                    }
                    checkBoxBaidu.setChecked(true);
                    editor.putString(StringMainSettings.nowTransEngine, StringMainSettings.baiduTransEngine);
                    editor.commit();
                }
            }
        });
    }

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
}
