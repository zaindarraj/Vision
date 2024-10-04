package com.example.vision.session_management.repo.remote;


import com.example.vision.session_management.data_store.RemoteDataSource;
import com.example.vision.session_management.models.SigninResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava3.Result;


public class RemoteRepository {


    private final RemoteDataSource remoteDataSource = new RemoteDataSource();

    public Single<Result<SigninResponse>> signIn(String userName, String password){
    return remoteDataSource.signIn(userName,password);
    }






}
