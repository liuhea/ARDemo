package com.lh.aliar;

import com.alibaba.ailabs.ar.activity.ArApplication;

/**
 * Created by weisheng.wws on 2018/5/18.
 */

public class XTApplication extends ArApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: We need to access your key and secret to communicate with ar server.
        init("24910706", "76eeeb123b6ad355f927ab217bbe0d37");
    }
}
