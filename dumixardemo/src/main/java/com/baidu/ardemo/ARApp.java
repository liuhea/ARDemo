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
        DuMixARConfig.setAppId("11239118");
        // 设置API Key
        DuMixARConfig.setAPIKey("RHGFx1E2UYooUf10PvF4G3Gm");
//        // 设置Secret Key
        DuMixARConfig.setSecretKey("mpHkBdefDiLhBzZGN9Gf4kKvypAm5AWr ");
    }
}
