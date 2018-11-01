package com.lixd.latte.ec.icon;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon {
    coffee('\ue757'),
    hot_fill('\ue7f1');

    char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
