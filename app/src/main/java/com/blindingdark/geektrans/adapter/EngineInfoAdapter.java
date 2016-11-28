package com.blindingdark.geektrans.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.bean.TransEngineInfo;
import com.blindingdark.geektrans.global.StringMainSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlindingDark on 2016/11/28 0028.
 */

/**
 * 自定义Adapter
 * Created by LGL on 2016/3/10.
 */
public class EngineInfoAdapter extends BaseAdapter {

    private List<TransEngineInfo> transEngineInfos;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    //构造方法
    public EngineInfoAdapter(Context context, List<TransEngineInfo> transEngineInfos) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = this.sharedPreferences.edit();
        this.transEngineInfos = transEngineInfos;
        mInflater = LayoutInflater.from(context);
    }

    //返回长度
    @Override
    public int getCount() {
        return transEngineInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return transEngineInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EngineSettingsViewHolder engineSettingsViewHolder = null;
        //判断是否有缓存
        if (convertView == null) {
            engineSettingsViewHolder = new EngineSettingsViewHolder();
            //通过LayoutInflater去实例化布局
            convertView = mInflater.inflate(R.layout.item_engine_info, null);
            engineSettingsViewHolder.engineName = (TextView) convertView.findViewById(R.id.engineName);
            engineSettingsViewHolder.button = (Button) convertView.findViewById(R.id.buttonJumpToSettings);
            engineSettingsViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            //管理 checkBox 列表
            checkBoxes.add(engineSettingsViewHolder.checkBox);

            //给这行内容设置一组 view 布局
            convertView.setTag(engineSettingsViewHolder);
        } else {
            //通过TAG找到缓存的布局
            engineSettingsViewHolder = (EngineSettingsViewHolder) convertView.getTag();
            // 这里不知为啥得到的缓存有问题，要强行设置为 false
            engineSettingsViewHolder.checkBox.setChecked(false);
        }
        //设置布局中要显示的东西

        // 从引擎列表里获取当前位置的引擎名字，并设置
        engineSettingsViewHolder.engineName.setText(transEngineInfos.get(position).getEngineName());

        // 跳转到对应的设置界面
        final int finalPosition = position;
        engineSettingsViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transIntent = new Intent();
                     /* 指定intent要启动的类 */
                try {
                    transIntent.setClass(context, Class.forName(transEngineInfos.get(finalPosition).getEngineSettingsActivityPackageName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                     /* 启动一个新的Activity */
                context.startActivity(transIntent);
            }
        });

        // 引擎选择

        // 现在所选的引擎包名
        String nowTransEngine = sharedPreferences.getString(StringMainSettings.NOW_TRANS_ENGINE, StringMainSettings.YOUDAO_TRANS_ENGINE);
        // 如果当前显示的引擎是默认引擎
        if (nowTransEngine.equals(transEngineInfos.get(finalPosition).getEnginePackageName())) {
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
                    editor.putString(StringMainSettings.NOW_TRANS_ENGINE, transEngineInfos.get(finalPosition).getEnginePackageName());
                    editor.commit();
                }
                thisCheckBox.setChecked(true);
            }
        });


        return convertView;
    }

    public final class EngineSettingsViewHolder {
        public TextView engineName;
        public CheckBox checkBox;
        public Button button;
    }
}