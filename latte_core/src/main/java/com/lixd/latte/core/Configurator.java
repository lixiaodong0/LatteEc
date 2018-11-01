package com.lixd.latte.core;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

public class Configurator {
    private static final HashMap<String, Object> LATTE_CONIFGS = new HashMap<>();

    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        LATTE_CONIFGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public final void configure() {
        initIconify();
        LATTE_CONIFGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    final HashMap<String, Object> getLatteConifgs() {
        return LATTE_CONIFGS;
    }

    public static final Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONIFGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }


    private final void initIconify() {
        if (ICONS.size() > 0) {
            Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }


    private final void checkConfiguration() {
        boolean isReady = (boolean) LATTE_CONIFGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("configuration is not ready,call configure");
        }
    }

    public <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LATTE_CONIFGS.get(key.name());
    }

}
