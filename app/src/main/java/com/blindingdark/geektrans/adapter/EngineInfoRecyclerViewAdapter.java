package com.blindingdark.geektrans.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.bean.TransEngineInfo;
import com.blindingdark.geektrans.global.StringMainSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlindingDark on 2016/11/29 0029.
 */

public class EngineInfoRecyclerViewAdapter extends android.support.v7.widget.RecyclerView.Adapter<EngineInfoRecyclerViewAdapter.EngineInfoViewHolder> {


    private List<TransEngineInfo> transEngineInfos;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    //构造方法
    public EngineInfoRecyclerViewAdapter(Context context, List<TransEngineInfo> transEngineInfos) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = this.sharedPreferences.edit();
        this.transEngineInfos = transEngineInfos;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public EngineInfoRecyclerViewAdapter.EngineInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = mInflater.inflate(R.layout.item_engine_info, null);
        //实例化布局
        EngineInfoRecyclerViewAdapter.EngineInfoViewHolder engineInfoViewHolder = new EngineInfoRecyclerViewAdapter.EngineInfoViewHolder(convertView);

        //管理 checkBox 列表
        checkBoxes.add(engineInfoViewHolder.checkBox);

        return engineInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(EngineInfoRecyclerViewAdapter.EngineInfoViewHolder engineSettingsViewHolder, int position) {
        final TransEngineInfo info = transEngineInfos.get(position);
        // 这里不知为啥得到的缓存有问题，要强行设置为 false
        engineSettingsViewHolder.checkBox.setChecked(false);
        //设置布局中要显示的东西

        // 从引擎列表里获取当前位置的引擎名字，并设置
        engineSettingsViewHolder.engineName.setText(info.getEngineName());


        engineSettingsViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transIntent = new Intent();
                     /* 指定intent要启动的类 */
                try {
                    /* 跳转 */
                    transIntent.setClass(context, Class.forName(info.getEngineSettingsActivityPackageName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                     /* 启动一个新的Activity */
                context.startActivity(transIntent);
            }
        });

        // 引擎选择

        // 现在所选的引擎包名
        String nowTransEngine = sharedPreferences.getString(StringMainSettings.NOW_TRANS_ENGINE, StringMainSettings.DEFAULT_NOW_TRANS_ENGINE);
        // 如果当前显示的引擎是默认引擎
        if (nowTransEngine.equals(info.getEnginePackageName())) {
            engineSettingsViewHolder.checkBox.setChecked(true);
        }

        final CheckBox thisCheckBox = engineSettingsViewHolder.checkBox;
        engineSettingsViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thisCheckBox.isChecked()) {
                    for (CheckBox cb : checkBoxes) {
                        if (null != cb) {
                            cb.setChecked(false);
                        }
                    }
                    editor.putString(StringMainSettings.NOW_TRANS_ENGINE, info.getEnginePackageName());
                    editor.commit();
                }
                thisCheckBox.setChecked(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transEngineInfos.size();
    }

    public class EngineInfoViewHolder extends RecyclerView.ViewHolder {

        public TextView engineName;
        public CheckBox checkBox;
        public Button button;

        public EngineInfoViewHolder(View itemView) {
            super(itemView);
            engineName = (TextView) itemView.findViewById(R.id.engineName);
            button = (Button) itemView.findViewById(R.id.buttonJumpToSettings);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }
}
