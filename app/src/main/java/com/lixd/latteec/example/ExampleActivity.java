package com.lixd.latteec.example;

import com.lixd.latte.core.activitys.ProxyActivity;
import com.lixd.latte.core.delegates.BaseDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    protected BaseDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
