package com.example.vision.profile.repository;

import com.example.vision.Profile;
import com.example.vision.profile.ProfileResponse;
import com.example.vision.profile.data_source.RemoteDataSource;
import com.example.vision.retrofit.API;
import com.example.vision.retrofit.RetrofitInstance;
import com.example.vision.session_management.models.SignInDataModel;
import com.example.vision.session_management.models.SigninResponse;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava3.Result;

public class RemoteRepository {
    RemoteDataSource remoteDataSource = new RemoteDataSource();
    Single<Result<ProfileResponse>> getProfile(){
                 return remoteDataSource.getProfile();
        }
    Single<Result<ResponseBody>> setUserName(String name){
        return remoteDataSource.setUserName(name);
    }

}
