package com.lixd.latteec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lixd.latte.core.app.Latte;
import com.lixd.latte.core.net.interceptors.DebugInterceptor;
import com.lixd.latte.ec.icon.EcFontModule;

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withInterceptor(new DebugInterceptor("index", 0))
                .withApiHost("http://www.baidu.com")
                .withIcon(new FontAwesomeModule())
                .withIcon(new EcFontModule())
                .configure();
    }
}
