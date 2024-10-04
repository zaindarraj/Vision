package com.example.vision.session_management.data_store;

import android.content.Context;

import androidx.datastore.core.Serializer;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.example.vision.Token;
import com.example.vision.session_management.models.TokenSerializer;

public class LocalDataStoreProvider {

    private static RxDataStore<Token> accessTokenDataStore;

    private static RxDataStore<Token> refreshTokenDataStore;

    private static Serializer<Token> serializer = new TokenSerializer();

    private LocalDataStoreProvider(){}
    public static void init(Context context){
        if(accessTokenDataStore ==null){
            accessTokenDataStore =
                    new RxDataStoreBuilder<>(context, /* fileName= */ "accessToken.pb", serializer).build();
        }
        if(refreshTokenDataStore == null){
            refreshTokenDataStore =
                    new RxDataStoreBuilder<>(context, /* fileName= */ "refreshToken.pb", serializer).build();
        }

    }
    public static RxDataStore<Token> getAccessTokenDataStore() {
        return accessTokenDataStore;
    }

    public static RxDataStore<Token> getRefreshTokenDataStore(){
        return refreshTokenDataStore;
    }
}
