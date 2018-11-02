package com.lixd.latte.core.activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.lixd.latte.core.R;
import com.lixd.latte.core.delegates.BaseDelegate;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class ProxyActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    private void initContainer(Bundle savedInstanceState) {
        ContentFrameLayout contentFrameLayout = new ContentFrameLayout(this);
        contentFrameLayout.setId(R.id.delegate_layout_container);
        setContentView(contentFrameLayout);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_layout_container, setRootDelegate());
        }
    }


    protected abstract BaseDelegate setRootDelegate();
}
