package com.lixd.latte.core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public abstract class BaseInterceptor implements Interceptor {

    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        final Request request = chain.request();
        final HttpUrl httpUrl = request.url();
        final int requestParamsCount = httpUrl.querySize();
        for (int i = 0; i < requestParamsCount; i++) {
            String key = httpUrl.queryParameterName(i);
            String value = httpUrl.queryParameterValue(i);
            params.put(key, value);
        }
        return params;
    }

    protected String getUrlParameters(Chain chain, String key) {
        return chain.request().url().queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        FormBody body = (FormBody) chain.request().body();
        final int requestParamsCount = body.size();
        for (int i = 0; i < requestParamsCount; i++) {
            String key = body.name(i);
            String value = body.value(i);
            params.put(key, value);
        }
        return params;
    }

    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }

}
