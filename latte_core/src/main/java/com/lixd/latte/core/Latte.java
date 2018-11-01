package com.lixd.latte.core;

import android.content.Context;

import java.util.HashMap;

public final class Latte {

    public static final Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static final HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConifgs();
    }


    public static final Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

}
