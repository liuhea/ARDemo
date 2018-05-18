package com.example.weishengwws.myapplication;

import com.alibaba.ailabs.ar.activity.ArApplication;

/**
 * Created by weisheng.wws on 2018/5/18.
 */

public class MyApplication extends ArApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: We need to access your key and secret to communicate with ar server.
        init("24888883", "7c3e72021b6aba8b109baabf320c578c");
    }
}
