package com.example.vision.retrofit;

import com.example.vision.session_management.models.SignInDataModel;
import com.example.vision.session_management.models.SigninResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("api/signIn")
    Single<Result<SigninResponse>> signIn(@Body SignInDataModel signInDataModel);
}
