package com.baidu.ardemo;

import com.baidu.ar.bean.DuMixARConfig;
import com.baidu.ar.util.Res;

import android.app.Application;

public class ARApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Res.addResource(this);
//        正式应用
//        // 设置App Id
//        DuMixARConfig.setAppId("11292186");
//        // 设置API Key
//        DuMixARConfig.setAPIKey("vN3MEXOdIf9dDpbi3TPNjlDD");
////        // 设置Secret Key
//        DuMixARConfig.setSecretKey("QqrwelGhWkmUFMvA7KSbMGklgtOdZfTE");
//试用版  Secret Key是不需要设置的  同时assets目录中不能存在名字为aip.license的文件
        // 设置App Id
        DuMixARConfig.setAppId("12635");
        // 设置API Key
        DuMixARConfig.setAPIKey("3ff1340991485ed3b575b0fb26b94ff5");
//        // 设置Secret Key
//        DuMixARConfig.setSecretKey("QqrwelGhWkmUFMvA7KSbMGklgtOdZfTE");

    }
}
