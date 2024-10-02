package com.example.vision.session_management.data_store;

import android.content.Context;
import android.util.Log;

import androidx.datastore.core.Serializer;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.example.vision.Token;
import com.example.vision.TokenType;
import com.example.vision.session_management.models.TokenSerializer;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;

public class LocalDataStore {
    RxDataStore<Token> dataStore;
    Serializer<Token> serializer = new TokenSerializer();
    public final Single<Token> accessTokenFlowable;
    public LocalDataStore(Context context){
        dataStore =
                new RxDataStoreBuilder<>(context, /* fileName= */ "token.pb", serializer).build();
        dataStore.updateDataAsync(token -> {
            String updatedToken = "Updated Token"; // Example modification
            TokenType tokenType = TokenType.RefreshToken;
            return Single.just(Token.newBuilder(token).setToken(updatedToken).setTokenType(tokenType).build());
        });
        dataStore.updateDataAsync(token -> {
            String updatedToken = "Updated Token"; // Example modification
            TokenType tokenType = TokenType.AccessToken;
            return Single.just(Token.newBuilder(token).setToken(updatedToken).setTokenType(tokenType).build());
        });

        accessTokenFlowable =
                dataStore.data().map(token -> token.getTokenType() == TokenType.AccessToken ? token : null).firstOrError().doOnError(error->{
                    Log.println(Log.ASSERT , "mannnnnnnn" , error.getMessage());
                });
    }


}
