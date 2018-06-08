package com.baidu.ardemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.ar.constants.ARConfigKey;

public class MainActivity extends Activity {
    private String[] mArName;
    private ListView mListView;
    private ArrayAdapter mAdapter;
    private List<ListItemBean> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        Resources res = getResources();
        mArName = res.getStringArray(R.array.ar_name);
    }

    private void initView() {
        mListData = getListItemData();
        mListView = (ListView) findViewById(R.id.demo_list);
        mListView.addFooterView(new ViewStub(this));
        mAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, mArName);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ARActivity.class);
                ListItemBean listItemBean = mListData.get(position);
                intent.putExtra(ARConfigKey.AR_KEY, listItemBean.getARKey());
                intent.putExtra(ARConfigKey.AR_TYPE, listItemBean.getARType());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private List<ListItemBean> getListItemData() {
        List<ListItemBean> list = new ArrayList<>();
        // 2D识图
        list.add(new ListItemBean(6, ""));
        // 沙发 SLAM
        list.add(new ListItemBean(5, "10156371"));
        // 小熊 SLAM
        list.add(new ListItemBean(5, "10156381"));
        // IMU AR 请财神case
        list.add(new ListItemBean(0, "10156361"));
        // IMU 天空盒效果
        list.add(new ListItemBean(0, "10156426"));
//        // 云端识图
//        list.add(new ListItemBean(7, "", mArName[2], mArDesciption[2]));
//        // Track AR城市地图case
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[3], mArDesciption[3]));

//        // 语音
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[5], mArDesciption[5]));
//        // TTS
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[6], mArDesciption[6]));
//        // 滤镜
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[7], mArDesciption[7]));
//        // LOGO识别
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[8], mArDesciption[8]));
//        // 手势识别
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[9], mArDesciption[9]));
//        // 在线视频
//        list.add(new ListItemBean(0, "您应用的ar_key", mArName[10], mArDesciption[10]));
        return list;
    }

    private class ListItemBean {
        String mARKey;
        int mARType;
        String mName;
        String mDescription;

        public ListItemBean(int arType, String arKey, String name, String description) {
            this.mARType = arType;
            this.mARKey = arKey;
            this.mName = name;
            this.mDescription = description;
        }

        public ListItemBean(int arType, String arKey) {
            this.mARType = arType;
            this.mARKey = arKey;
        }

        public String getARKey() {
            return mARKey;
        }

        public int getARType() {
            return mARType;
        }

        public String getName() {
            return mName;
        }

        public String getDescription() {
            return mDescription;
        }
    }
}