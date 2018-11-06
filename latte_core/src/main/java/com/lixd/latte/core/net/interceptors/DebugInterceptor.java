package com.lixd.latte.core.net.interceptors;

import android.support.annotation.RawRes;

import com.lixd.latte.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, @RawRes int debugRawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = debugRawId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl httpUrl = chain.request().url();
        if (httpUrl.url().toString().contains(DEBUG_URL)) {
            return debugResponse(chain);
        } else {
            return chain.proceed(chain.request());
        }

    }

    private Response debugResponse(Chain chain) {
        final String json = FileUtil.getRawFile(DEBUG_RAW_ID);
        return getResponse(chain, json);
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .request(chain.request())
                .build();
    }
}
