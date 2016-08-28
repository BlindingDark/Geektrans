package com.blindingdark.geektrans.activitys.settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.tools.Number;
import com.blindingdark.geektrans.trans.bing.StringBingSettings;

public class BingSettingsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText editTextBingAppId;
    EditText editTextBingKey;
    EditText editTextBingToastTime;
    Spinner spinnerBingFrom;
    Spinner spinnerBingTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bing_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editTextBingAppId = (EditText) findViewById(R.id.editTextBingAppId);
        String bingAppId = preferences.getString(StringBingSettings.bingAppId, "");
        editTextBingAppId.setText(bingAppId);
        editTextBingAppId.addTextChangedListener(bingAppIdTextWatcher);

        editTextBingKey = (EditText) findViewById(R.id.editTextBingKey);
        String bingKey = preferences.getString(StringBingSettings.bingKey,"");
        editTextBingKey.setText(bingKey);
        editTextBingKey.addTextChangedListener(bingKeyTextWatcher);

        editTextBingToastTime = (EditText) findViewById(R.id.editTextBingToastTime);
        String bingToastTime = preferences.getString(StringBingSettings.bingToastTime,StringBingSettings.defBingToastTime);
        editTextBingToastTime.setText(bingToastTime);
        editTextBingToastTime.addTextChangedListener(bingToastTimeTextWatcher);


        spinnerBingFrom = (Spinner) findViewById(R.id.spinnerBingLangFrom);
        String defFrom = preferences.getString(StringBingSettings.bingFrom, "0_auto");
        spinnerBingFrom.setSelection(Integer.parseInt(defFrom.split("_")[0]));

        spinnerBingFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String from = parent.getItemAtPosition(position).toString();
                String fromCode = from.split("_")[1];
                editor.putString(StringBingSettings.bingFrom,  position + "_" + fromCode);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerBingTo = (Spinner) findViewById(R.id.spinnerBingLangTo);
        String defTo = preferences.getString(StringBingSettings.bingTo, "0_0");
        spinnerBingTo.setSelection(Integer.parseInt(defTo.split("_")[0]));

        spinnerBingTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String to = parent.getItemAtPosition(position).toString();
                String toCode = to.split("_")[1];
                editor.putString(StringBingSettings.bingTo,  position + "_" + toCode);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    TextWatcher bingAppIdTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(StringBingSettings.bingAppId, editTextBingAppId.getText().toString());
            editor.commit();
        }
    };

    TextWatcher bingKeyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(StringBingSettings.bingKey, editTextBingKey.getText().toString());
            editor.commit();
        }
    };

    TextWatcher bingToastTimeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String time = editTextBingToastTime.getText().toString();
            // 检测是否是合法数字
            if (Number.isLegalToastTime(time)) {
                editor.putString(StringBingSettings.bingToastTime, time);
                editor.commit();
            }

        }
    };
}
