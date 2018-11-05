package com.lixd.latte.core.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseDelegate extends SupportFragment {

    protected Unbinder mUnbinder = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (getLayout() instanceof Integer) {
            rootView = inflater.inflate((int) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("getLayout type int & View");
        }

        if (rootView != null) {
            mUnbinder = ButterKnife.bind(this, rootView);
            onBinderView(savedInstanceState, rootView);
        }

        return rootView;
    }

    protected abstract Object getLayout();

    protected abstract void onBinderView(Bundle savedInstanceState, View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
