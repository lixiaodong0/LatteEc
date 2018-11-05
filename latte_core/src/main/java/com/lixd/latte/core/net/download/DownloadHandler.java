package com.lixd.latte.core.net.download;

import android.os.AsyncTask;

import com.lixd.latte.core.net.RestCreator;
import com.lixd.latte.core.net.callback.IError;
import com.lixd.latte.core.net.callback.IFailure;
import com.lixd.latte.core.net.callback.IRequest;
import com.lixd.latte.core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IRequest REQUEST;
    private final IError ERROR;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;

    public DownloadHandler(String url, WeakHashMap<String, Object> params,
                           String downloadDir, String extension, String name,
                           IRequest request, IError error, ISuccess success, IFailure failure) {
        this.URL = url;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.ERROR = error;
        this.SUCCESS = success;
        this.FAILURE = failure;
    }

    public final void handlerDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        Call<ResponseBody> download = RestCreator.getRestService().download(URL, PARAMS);
        download.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    SaveFileTask saveFileTask = new SaveFileTask(REQUEST, SUCCESS);
                    saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, NAME, response.body());

                    //这里一定要注意判断，否则文件下载不全
                    if (saveFileTask.isCancelled()) {
                        if (REQUEST != null) {
                            REQUEST.onRequestEnd();
                        }
                    }
                } else {
                    if (ERROR != null) {
                        ERROR.onError(response.code(), response.message());
                    }

                    if (REQUEST != null) {
                        REQUEST.onRequestEnd();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (FAILURE != null) {
                    FAILURE.onFailure();
                }

                if (REQUEST != null) {
                    REQUEST.onRequestEnd();
                }
            }
        });
    }
}
