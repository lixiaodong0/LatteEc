package com.lixd.latte.core.net;

import android.content.Context;

import com.lixd.latte.core.net.callback.IError;
import com.lixd.latte.core.net.callback.IFailure;
import com.lixd.latte.core.net.callback.IRequest;
import com.lixd.latte.core.net.callback.ISuccess;
import com.lixd.latte.core.net.callback.RequestCallbacks;
import com.lixd.latte.core.net.download.DownloadHandler;
import com.lixd.latte.core.ui.loader.LatteLoader;
import com.lixd.latte.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final File FILE;
    private final LoaderStyle LOADER_STYLE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;


    RestClient(String url, WeakHashMap<String, Object> params, RequestBody requestBody,
               IRequest request, ISuccess success, IError error, IFailure failure,
               Context context, LoaderStyle loaderStyle, File file,
               String downloadDir, String extension, String name) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.ERROR = error;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.REQUEST_BODY = requestBody;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }


    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (CONTEXT != null && LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        Call<String> call = null;
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, REQUEST_BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, REQUEST_BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
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

    public final void postRaw() {
        request(HttpMethod.POST_RAW);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void putRaw() {
        request(HttpMethod.PUT_RAW);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        //文件下载跟请求不一样，需要单独处理
        final DownloadHandler downloadHandler = new DownloadHandler(URL, PARAMS,
                DOWNLOAD_DIR, EXTENSION, NAME, REQUEST, ERROR, SUCCESS, FAILURE);
        downloadHandler.handlerDownload();
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }
}
