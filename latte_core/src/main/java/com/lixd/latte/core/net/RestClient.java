package com.lixd.latte.core.net;

import android.content.Context;

import com.lixd.latte.core.net.callback.IError;
import com.lixd.latte.core.net.callback.IFailure;
import com.lixd.latte.core.net.callback.IRequest;
import com.lixd.latte.core.net.callback.ISuccess;
import com.lixd.latte.core.net.callback.RequestCallbacks;
import com.lixd.latte.core.ui.loader.LatteLoader;
import com.lixd.latte.core.ui.loader.LoaderStyle;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final RequestBody REQUEST_BODY;
    private final IRequest REQUEST;
    private final IError ERROR;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;

    RestClient(String url, WeakHashMap<String, Object> params, RequestBody requestBody,
               IRequest request, ISuccess success, IError error, IFailure failure,
               Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.ERROR = error;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.REQUEST_BODY = requestBody;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }


    private void request(HttpMethod method) {

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (CONTEXT != null && LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        Call<String> call = null;
        switch (method) {
            case GET:
                call = RestCreator.getRestService().get(URL, PARAMS);
                break;
            case POST:
                call = RestCreator.getRestService().post(URL, PARAMS);
                break;
            case POST_RAW:
                call = RestCreator.getRestService().postRaw(URL, REQUEST_BODY);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(new RequestCallbacks(REQUEST, ERROR, SUCCESS, FAILURE));
        }
    }


    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        request(HttpMethod.DOWNLOAD);
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }
}
