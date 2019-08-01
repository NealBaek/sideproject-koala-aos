package com.ksdigtalnomad.koala.net;

import net.ooddy.buddycoin.BuildConfig;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenericFactory {
    private static final int MAX_CONNECTION_COUNT = 10;

    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofitObject().create(serviceClass);
    }

    private static Retrofit getRetrofitObject() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(MAX_CONNECTION_COUNT);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new AddTokenInterceptor())
                .dispatcher(dispatcher)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL + BuildConfig.SERVER_PORT + BuildConfig.API_PREFIX)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
