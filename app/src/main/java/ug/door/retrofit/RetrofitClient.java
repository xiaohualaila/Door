package ug.door.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by xyuxiao on 2016/9/23.
 */
public class RetrofitClient {
    public static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(MyOkClient.getOkHttpClient())
                .build();
    }

    public static Retrofit getRxRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(MyOkClient.getOkHttpClient())
                .build();
    }

    public static Retrofit getRxRetrofitWithoutFormat(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(MyOkClient.getOkHttpClient())
                .build();
    }

    public static Retrofit getRxRetrofitWithArray(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JsonArrayConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(MyOkClient.getOkHttpClient())
                .build();
    }

    public static <T> T createApi(Class<T> clazz, Retrofit retrofit) {
        return retrofit.create(clazz);
    }
}
