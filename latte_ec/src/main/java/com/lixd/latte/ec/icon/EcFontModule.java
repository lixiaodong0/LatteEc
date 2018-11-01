package com.lixd.latte.ec.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

public class EcFontModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }
    @Override
    public Icon[] characters() {
        return EcIcons.values();
    }
}
