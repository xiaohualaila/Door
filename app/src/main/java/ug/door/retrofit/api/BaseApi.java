package ug.door.retrofit.api;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;
import ug.door.model.Videos;

/**
 * Created by xyuxiao on 2016/9/23.
 */
public interface BaseApi {


    @POST("1.php")
    @Multipart
    Observable<JSONObject> uploadPhoto(
            @Query("no") String no,
            @Part List<MultipartBody.Part> file
    );

    @POST("ss")
    Observable<JSONObject> login(
            @Query("username") String username,
            @Query("password") String password
    );
    @POST("validate.php")
    Observable<JSONObject> checkIp();

    @POST("send.php")
    Observable<Videos> videosList();
}

