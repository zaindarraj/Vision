package com.example.vision.session_management.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vision.Token;
import com.example.vision.session_management.data_store.LocalDataStore;
import com.example.vision.session_management.models.SigninResponse;
import com.example.vision.session_management.models.TokenModel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class LocalRepository {

    public Single<SigninResponse> getAccessToken() {
        return localDataStore.getTokens();
    }


    Single<Boolean> setTokens(SigninResponse tokenModel){
        return localDataStore.setTokens(tokenModel);
    }

    LocalDataStore localDataStore = new LocalDataStore();


    public Single<Boolean> signout(){
       return localDataStore.signout();
    }
}
