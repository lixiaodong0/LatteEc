package com.lixd.latteec.example;

import android.os.Bundle;
import android.view.View;

import com.lixd.latte.core.delegates.LatteDelegate;
import com.lixd.latte.core.net.RestClient;
import com.lixd.latte.core.net.callback.IError;
import com.lixd.latte.core.net.callback.IFailure;
import com.lixd.latte.core.net.callback.IRequest;
import com.lixd.latte.core.net.callback.ISuccess;

public class ExampleDelegate extends LatteDelegate {
    @Override
    protected Object getLayout() {
        return R.layout.delegate_example;
    }

    @Override
    protected void onBinderView(Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("https://www.baidu.com/?tn=news")
                .loader(getContext())
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(_mActivity, "" + response, Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build()
                .get();
    }
}
