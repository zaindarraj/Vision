package com.example.vision.retrofit;

import com.example.vision.Profile;
import com.example.vision.alerts.Alert;
import com.example.vision.alerts.Alerts;
import com.example.vision.profile.ProfileResponse;
import com.example.vision.scene.DetectedObjects;
import com.example.vision.session_management.models.SignInDataModel;
import com.example.vision.session_management.models.SigninResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @Headers("No-Authentication: true")
    @POST("api/signIn")
    Single<Result<SigninResponse>> signIn(@Body SignInDataModel signInDataModel);

    @POST("api/getProfile")
    Single<Result<ProfileResponse>> getProfile();

    @FormUrlEncoded
    @POST("api/setUserName")
    Single<Result<ResponseBody>> setUserName(@Field("userName") String userName);


    @Multipart
    @POST("api/predict")
    Single<Result<DetectedObjects>> predict(@Part() MultipartBody.Part image);
    @POST("api/alerts")
    Single<Result<Alerts>> alerts();
}
