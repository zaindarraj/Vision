package com.example.vision.profile.data_source;

import com.example.vision.Profile;
import com.example.vision.profile.ProfileResponse;
import com.example.vision.retrofit.API;
import com.example.vision.retrofit.RetrofitInstance;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava3.Result;

public class RemoteDataSource {
    private final API api = RetrofitInstance.getInstance();

    public Single<Result<ProfileResponse>> getProfile(){
        return api.getProfile();
    }
    public Single<Result<ResponseBody>> setUserName(String name){
        return api.setUserName(name);
    }
}
