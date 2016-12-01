package com.blindingdark.geektrans.activity.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by BlindingDark on 2016/12/1 0001.
 */
public class OtherSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragement()).commit();
    }


}
