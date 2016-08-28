package com.blindingdark.geektrans.activitys.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.activitys.StringMainSettings;
import com.blindingdark.geektrans.tools.Number;
import com.blindingdark.geektrans.trans.youdao.Youdao;
import com.blindingdark.geektrans.trans.youdao.YoudaoSettingsString;

import java.util.HashSet;
import java.util.Set;

public class YoudaoSettingsActivity extends AppCompatActivity {

    EditText editTextKey;
    EditText editTextKeyfrom;
    EditText editTextDivLine;
    EditText editTextToastTime;

    Switch switchYoudaoSoundRemix;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youdao_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editTextKey = (EditText) findViewById(R.id.edtTxtApiKey);
        String key = preferences.getString(YoudaoSettingsString.youdaoKey, "");
        editTextKey.setText(key);
        editTextKey.addTextChangedListener(keyTextWatcher);

        editTextKeyfrom = (EditText) findViewById(R.id.edtTxtApiKeyfrom);
        String keyfrom = preferences.getString(YoudaoSettingsString.youdaoKeyfrom, "");
        editTextKeyfrom.setText(keyfrom);
        editTextKeyfrom.addTextChangedListener(keyfromTextWatcher);

        editTextDivLine = (EditText) findViewById(R.id.edtTxtDivisionLine);
        String divLine = preferences.getString(YoudaoSettingsString.divisionLine, YoudaoSettingsString.defaultDivLine);
        editTextDivLine.setText(divLine);
        editTextDivLine.addTextChangedListener(divTextWatcher);

        editTextToastTime = (EditText) findViewById(R.id.edtTxtYoudaoToastTime);
        String youdaoToastTime = preferences.getString(YoudaoSettingsString.youdaoToastTime, YoudaoSettingsString.youdaoDefToastTime);
        editTextToastTime.setText(youdaoToastTime);

        editTextToastTime.addTextChangedListener(toastTimeWatcher);

        switchYoudaoSoundRemix = (Switch) findViewById(R.id.switchYoudaoSoundRemix);
        String nowSoundEngine = preferences.getString(StringMainSettings.defaultSoundEngine, "");
        if (nowSoundEngine.equals(Youdao.engineName)) {
            switchYoudaoSoundRemix.setChecked(true);
        } else {
            switchYoudaoSoundRemix.setChecked(false);
        }

        switchYoudaoSoundRemix.setOnCheckedChangeListener(youdaoSoundRemixChangeListener);

    }

    CompoundButton.OnCheckedChangeListener youdaoSoundRemixChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                editor.putString(StringMainSettings.defaultSoundEngine, Youdao.engineName);
            }
            if (!isChecked) {
                if (preferences.getString(StringMainSettings.defaultSoundEngine, "").equals(Youdao.engineName)) {
                    editor.putString(StringMainSettings.defaultSoundEngine, "");
                }
            }
            editor.commit();
        }
    };

    TextWatcher keyTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(YoudaoSettingsString.youdaoKey, editTextKey.getText().toString());
            editor.commit();

        }
    };
    TextWatcher keyfromTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(YoudaoSettingsString.youdaoKeyfrom, editTextKeyfrom.getText().toString());
            editor.commit();
        }
    };

    TextWatcher divTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(YoudaoSettingsString.divisionLine, editTextDivLine.getText().toString());
            editor.commit();

        }
    };

    TextWatcher toastTimeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String time = editTextToastTime.getText().toString();

            // 检测是否是合法数字

            if (Number.isLegalToastTime(time)) {
                editor.putString(YoudaoSettingsString.youdaoToastTime, time);
                editor.commit();
            }


        }
    };

}
