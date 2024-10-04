package com.example.vision.session_management.data_store;

import android.content.Context;
import android.util.Log;

import androidx.datastore.core.Serializer;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.example.vision.Token;
import com.example.vision.TokenType;
import com.example.vision.session_management.models.SigninResponse;
import com.example.vision.session_management.models.TokenModel;
import com.example.vision.session_management.models.TokenSerializer;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;

public class LocalDataStore {




  public Flowable<Token>  getAccessToken(){
       return  LocalDataStoreProvider.getAccessTokenDataStore().data().map(
                token->{
                    Log.println(Log.ASSERT,"While reading tokens" , String.valueOf(token.getTokenType()));
                    return token.getTokenType() == TokenType.AccessToken ? token : null;}
        );
    }

    public Single<Boolean> setTokens(SigninResponse tokenModel) {
       Single<Token> accessToken =  LocalDataStoreProvider.getAccessTokenDataStore().updateDataAsync(token -> Single.just(Token.newBuilder().setToken(tokenModel.getAccessToken().getToken()).setTokenType(TokenType.AccessToken).build()));
        Single<Token> refreshToken =  LocalDataStoreProvider.getRefreshTokenDataStore().updateDataAsync(token -> Single.just(Token.newBuilder().setToken(tokenModel.getRefreshToken().getToken()).setTokenType(TokenType.RefreshToken).build()));
        return Single.zip(accessToken,refreshToken,(accessToken1,refreshToken1)->{
            Log.println(Log.ASSERT, "IN Local Data Source  Access : ", String.valueOf(accessToken1.getTokenType()));
            return true;
        });
    }



}
