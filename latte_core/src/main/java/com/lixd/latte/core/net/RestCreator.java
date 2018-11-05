package com.lixd.latte.core.net;

import com.lixd.latte.core.app.ConfigType;
import com.lixd.latte.core.app.Configurator;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {


    public static final RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    public static final WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }


    /**
     * 全局 retrofit 构造
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = Configurator.getInstance().getConfiguration(ConfigType.API_HOST);
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * 全局 okhttp 构造
     */
    private static final class OkhttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Configurator.getInstance().getConfiguration(ConfigType.INTERCEPTORS);

        private static final OkHttpClient.Builder addInterceptors() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 全局 RestService 构造
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT.create(RestService.class);
    }
}
