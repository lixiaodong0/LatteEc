package com.lixd.latte.core.net;

import android.content.Context;

import com.lixd.latte.core.net.callback.IError;
import com.lixd.latte.core.net.callback.IFailure;
import com.lixd.latte.core.net.callback.IRequest;
import com.lixd.latte.core.net.callback.ISuccess;
import com.lixd.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private String mUrl;
    private WeakHashMap<String, Object> mParams;
    private IRequest mRequest;
    private IError mError;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private RequestBody mRequestBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private String mName;
    private String mExtension;
    private String mDownloadDir;

    public RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public RestClientBuilder parmas(WeakHashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }

    public RestClientBuilder parmas(String key, Object value) {
        if (mParams == null) {
            mParams = new WeakHashMap<>();
        }
        mParams.put(key, value);
        return this;
    }

    public RestClientBuilder raw(String json) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), json);
        return this;
    }

    public RestClientBuilder onRequest(IRequest request) {
        this.mRequest = request;
        return this;
    }


    public RestClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public RestClientBuilder error(IError error) {
        this.mError = error;
        return this;
    }

    public RestClientBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.LineSpinFadeLoaderIndicator;
        return this;
    }

    public RestClientBuilder download(String downloadDir, String fileName, String extension) {
        this.mDownloadDir = downloadDir;
        this.mName = fileName;
        this.mExtension = extension;
        return this;
    }

    private void checkParams() {
        if (mParams == null) {
            mParams = new WeakHashMap<>();
        }
    }

    public RestClient build() {
        checkParams();
        return new RestClient(mUrl, mParams, mRequestBody, mRequest, mSuccess,
                mError, mFailure, mContext, mLoaderStyle, mFile, mDownloadDir, mExtension, mName);
    }

}
