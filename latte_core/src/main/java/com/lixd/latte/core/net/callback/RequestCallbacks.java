package com.lixd.latte.core.net.callback;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.lixd.latte.core.ui.loader.LatteLoader;
import com.lixd.latte.core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final IError ERROR;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private static final Handler HANDLER = new Handler(Looper.myLooper());

    public RequestCallbacks(IRequest request, IError error, ISuccess success, IFailure failure) {
        this.REQUEST = request;
        this.ERROR = error;
        this.SUCCESS = success;
        this.FAILURE = failure;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        stopLoading();
    }

    public void stopLoading() {
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.hideLoading();
            }
        }, 2000);
    }
}
