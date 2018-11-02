package com.lixd.latte.core.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

public class LoaderCreator {

    private static final WeakHashMap<String, Indicator> INDICATORS = new WeakHashMap<>();

    static final AVLoadingIndicatorView create(Context context, String type) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        Indicator indicator = null;
        if (INDICATORS.containsKey(type)) {
            indicator = INDICATORS.get(type);
        } else {
            indicator = getIndicator(type);
            INDICATORS.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(indicator);
        return avLoadingIndicatorView;
    }

    private static final Indicator getIndicator(String type) {
        StringBuilder drawableClassName = new StringBuilder();
        if (!type.contains(".")) {
            String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(type);
        Class<?> drawableClass = null;
        try {
            drawableClass = Class.forName(drawableClassName.toString());
            Indicator indicator = (Indicator) drawableClass.newInstance();
            return indicator;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
