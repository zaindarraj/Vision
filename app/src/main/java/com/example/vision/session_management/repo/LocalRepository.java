package com.example.vision.session_management.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vision.Token;
import com.example.vision.session_management.data_store.LocalDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class LocalRepository {
    //We need local data source -> Data Store
    public LocalRepository(Context context){

        localDataStore = new LocalDataStore(context);
    }
    public Single<Token> getAccessToken() {
        return localDataStore.accessTokenFlowable;
    }

    LocalDataStore localDataStore;
}
