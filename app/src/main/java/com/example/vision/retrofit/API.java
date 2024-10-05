package com.example.vision.retrofit;

import com.example.vision.Profile;
import com.example.vision.profile.ProfileResponse;
import com.example.vision.session_management.models.SignInDataModel;
import com.example.vision.session_management.models.SigninResponse;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @Headers("No-Authentication: true")
    @POST("api/signIn")
    Single<Result<SigninResponse>> signIn(@Body SignInDataModel signInDataModel);

    @POST("api/getProfile")
    Single<Result<ProfileResponse>> getProfile();

    @FormUrlEncoded
    @POST("api/setUserName")
    Single<Result<ResponseBody>> setUserName(@Field("userName") String userName);
}
