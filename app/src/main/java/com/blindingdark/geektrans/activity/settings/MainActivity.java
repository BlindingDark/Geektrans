package com.blindingdark.geektrans.activity.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.adapter.EngineInfoAdapter;
import com.blindingdark.geektrans.bean.TransEngineInfo;
import com.blindingdark.geektrans.global.StringMainSettings;
import com.blindingdark.geektrans.global.TransEngineInfoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ListView listViewEngines;
    private EngineInfoAdapter engineInfoAdapter;
    private List<TransEngineInfo> transEngineInfos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        // 得到当前已添加的引擎列表
        Set<String> defaultTransSet = new TreeSet<>();
        defaultTransSet.add(StringMainSettings.YOUDAO_TRANS_ENGINE);
        defaultTransSet.add(StringMainSettings.JINSHAN_TRANS_ENGINE);
        defaultTransSet.add(StringMainSettings.BAIDU_TRANS_ENGINE);
        defaultTransSet.add(StringMainSettings.BING_TRANS_ENGINE);

        TreeSet<String> transSet = (TreeSet<String>) preferences.getStringSet(StringMainSettings.NOW_ENGINE_LIST, defaultTransSet);

        for (String transEPackageName : transSet) {
            transEngineInfos.add(TransEngineInfoFactory.getTransEngineInfo(transEPackageName));
        }

        listViewEngines = (ListView) findViewById(R.id.listViewEngines);
        engineInfoAdapter = new EngineInfoAdapter(this, transEngineInfos);

        listViewEngines.setAdapter(engineInfoAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transEngineInfos.add(TransEngineInfoFactory.getTransEngineInfo(StringMainSettings.BAIDU_TRANS_ENGINE));
                engineInfoAdapter.notifyDataSetChanged();


/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

}
