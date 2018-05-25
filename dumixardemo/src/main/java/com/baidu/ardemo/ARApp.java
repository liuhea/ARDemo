package com.baidu.ardemo;

import com.baidu.ar.bean.DuMixARConfig;
import com.baidu.ar.util.Res;

import android.app.Application;

public class ARApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Res.addResource(this);
        // 设置App Id
        DuMixARConfig.setAppId("11292186");
        // 设置API Key
        DuMixARConfig.setAPIKey("vN3MEXOdIf9dDpbi3TPNjlDD");
//        // 设置Secret Key
        DuMixARConfig.setSecretKey("QqrwelGhWkmUFMvA7KSbMGklgtOdZfTE");
    }
}
