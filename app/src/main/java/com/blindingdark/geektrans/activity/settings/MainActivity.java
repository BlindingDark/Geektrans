package com.blindingdark.geektrans.activity.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.adapter.EngineInfoRecyclerViewAdapter;
import com.blindingdark.geektrans.bean.TransEngineInfo;
import com.blindingdark.geektrans.global.SeqMainSettings;
import com.blindingdark.geektrans.global.StringMainSettings;
import com.blindingdark.geektrans.global.TransEngineInfoFactory;
import com.blindingdark.geektrans.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ezy.assist.compat.SettingsCompat;


public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private RecyclerView recyclerViewEngines;
    EngineInfoRecyclerViewAdapter engineInfoRecyclerViewAdapter;
    private List<TransEngineInfo> transEngineInfos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        Set<String> defaultTransSet = SeqMainSettings.getDefaultEngines();
        // 得到当前已添加的引擎列表

        // 想改 preferences.getStringSet 要 new 一个新的
        final HashSet<String> nowTransEngines = new HashSet<>(preferences.getStringSet(StringMainSettings.NOW_ENGINE_LIST, defaultTransSet));

        for (String transEPackageName : nowTransEngines) {
            transEngineInfos.add(TransEngineInfoFactory.getTransEngineInfo(transEPackageName));
        }

        recyclerViewEngines = (RecyclerView) findViewById(R.id.recyclerViewEngines);

        recyclerViewEngines.setLayoutManager(new LinearLayoutManager(this));
        engineInfoRecyclerViewAdapter = new EngineInfoRecyclerViewAdapter(this, transEngineInfos);
        recyclerViewEngines.setAdapter(engineInfoRecyclerViewAdapter);

        // 设置分割线
        recyclerViewEngines.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        // 设置添加动画
        recyclerViewEngines.setItemAnimator(new DefaultItemAnimator());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // toolbar 设置
        toolbar.setTitle(this.getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                switch (item.getItemId()) {
                    case R.id.other_settings:
                        Intent transIntent = new Intent();
                        transIntent.setClass(MainActivity.this, OtherSettingsActivity.class);
                        MainActivity.this.startActivity(transIntent);
                        break;
                }
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> allEnginesTemp = SeqMainSettings.getAllEngines();
                allEnginesTemp.removeAll(nowTransEngines);

                if (allEnginesTemp.isEmpty()) {
                    final Snackbar snackbar = Snackbar.make(view, "暂时没有更多引擎啦~", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final List<TransEngineInfo> addableEInfoList = new ArrayList<>();
                List<String> showAddList = new ArrayList<>();
                for (String enginePackageName : allEnginesTemp) {
                    TransEngineInfo info = TransEngineInfoFactory.getTransEngineInfo(enginePackageName);
                    addableEInfoList.add(info);
                    showAddList.add(info.getEngineName());
                }


                builder.setItems(showAddList.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        nowTransEngines.add(addableEInfoList.get(arg1).getEnginePackageName());
                        editor.putStringSet(StringMainSettings.NOW_ENGINE_LIST, nowTransEngines);
                        editor.commit();
                        transEngineInfos.add(addableEInfoList.get(arg1));
                        arg0.dismiss();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        engineInfoRecyclerViewAdapter.notifyItemInserted(nowTransEngines.size());
                    }
                });
                builder.show();

            }
        });

        // 引擎卡片滑动
        //0则不执行拖动或者滑动
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * @param viewHolder 拖动的ViewHolder
             * @return
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                nowTransEngines.remove(transEngineInfos.get(position).getEnginePackageName());

                editor.putStringSet(StringMainSettings.NOW_ENGINE_LIST, nowTransEngines);
                editor.commit();

                transEngineInfos.remove(position);
                engineInfoRecyclerViewAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }


        };
        // 添加触摸事件支持
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewEngines);

        Context context = getApplicationContext();
        if(!SettingsCompat.canDrawOverlays(context)){
            Toast.makeText(context,"检测到未开启“在上层显示”权限，请前往设置开启。",Toast.LENGTH_LONG).show();
        }
    }



}
