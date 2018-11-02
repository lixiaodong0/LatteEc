package com.lixd.latte.core.util.dimen;

import android.util.DisplayMetrics;

import com.lixd.latte.core.app.Latte;

public class DimenUtil {

    public static final int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public static final int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    private static final DisplayMetrics getDisplayMetrics() {
        return Latte.getApplicationContext().getResources().getDisplayMetrics();
    }
}
