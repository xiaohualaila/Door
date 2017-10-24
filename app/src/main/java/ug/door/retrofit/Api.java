package ug.door.retrofit;


import ug.door.retrofit.api.BaseApi;

/**
 * Created by xyuxiao on 2016/9/23.
 */
public class Api {
    private static BaseApi baseApi;

    public static BaseApi getBaseApi(String url) {
            baseApi = RetrofitClient.createApi(BaseApi.class,
                    RetrofitClient.getRxRetrofit(url));
        return baseApi;
    }
    //获取ObjectJson
    public static BaseApi getBaseApiWithOutFormat(String url) {
        baseApi = RetrofitClient.createApi(BaseApi.class,
            //    RetrofitClient.getRxRetrofitWithoutFormat(ConnectUrl.BASE_URL));
                RetrofitClient.getRxRetrofitWithoutFormat(url));
        return baseApi;
    }

    //返回JSONArray
    public static BaseApi getArray(String url) {
        baseApi = RetrofitClient.createApi(BaseApi.class,
                RetrofitClient.getRxRetrofitWithArray(url));
        return baseApi;
    }

}
