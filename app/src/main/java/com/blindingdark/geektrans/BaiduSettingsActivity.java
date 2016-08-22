package com.blindingdark.geektrans;

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

import com.blindingdark.geektrans.trans.baidu.BaiduSettingsString;


public class BaiduSettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText editTextBDAPPID;
    EditText editTextBDKey;
    Spinner spinnerBDFrom;
    Spinner spinnerBDTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editTextBDAPPID = (EditText) findViewById(R.id.BDAPPID);
        String BDAPPID = preferences.getString(BaiduSettingsString.baiduAppId, "");
        editTextBDAPPID.setText(BDAPPID);
        editTextBDAPPID.addTextChangedListener(bdAPPIDTextWatcher);


        editTextBDKey = (EditText) findViewById(R.id.BDKey);
        String key = preferences.getString(BaiduSettingsString.baiduKey, "");
        editTextBDKey.setText(key);
        editTextBDKey.addTextChangedListener(bdKeyTextWatcher);


        spinnerBDFrom = (Spinner) findViewById(R.id.BDLangFromSpinner);

        spinnerBDFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String from = parent.getItemAtPosition(position).toString();
                String fromCode = from.split("-")[1];
                editor.putString(BaiduSettingsString.baiduFrom, fromCode);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        spinnerBDTo = (Spinner) findViewById(R.id.BDLangToSpinner);
        spinnerBDTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String from = parent.getItemAtPosition(position).toString();
                String fromCode = from.split("-")[1];
                editor.putString(BaiduSettingsString.baiduTo, fromCode);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    TextWatcher bdAPPIDTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(BaiduSettingsString.baiduAppId, editTextBDAPPID.getText().toString());
            editor.commit();
        }
    };

    TextWatcher bdKeyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(BaiduSettingsString.baiduKey, editTextBDKey.getText().toString());
            editor.commit();
        }
    };
}
