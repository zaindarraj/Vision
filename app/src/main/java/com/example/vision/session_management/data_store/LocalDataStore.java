package com.example.vision.session_management.data_store;

import android.util.Log;

import com.example.vision.Token;
import com.example.vision.TokenType;
import com.example.vision.session_management.models.SigninResponse;

import io.reactivex.rxjava3.core.Single;

public class LocalDataStore {




  public Single<SigninResponse>  getTokens(){
       Single<Token> accessTokenFlowable =   LocalDataStoreProvider.getAccessTokenDataStore().data().map(
                token-> token.getTokenType() == TokenType.AccessToken ? token : null
        ).firstOrError().onErrorReturn(error-> Token.newBuilder().setTokenType(TokenType.Unknown).build());
      Single<Token> refreshTokenFlowable =   LocalDataStoreProvider.getRefreshTokenDataStore().data().map(
              token-> token.getTokenType() == TokenType.RefreshToken ? token : null
      ).firstOrError().onErrorReturn(error-> Token.newBuilder().setTokenType(TokenType.Unknown).build());

     return Single.zip(accessTokenFlowable,refreshTokenFlowable, SigninResponse::new);
    }

    public Single<Boolean> setTokens(SigninResponse tokenModel) {
       Single<Token> accessToken =  LocalDataStoreProvider.getAccessTokenDataStore().updateDataAsync(token -> Single.just(Token.newBuilder().setToken(tokenModel.getAccessToken().getToken()).setTokenType(TokenType.AccessToken).build()));
        Single<Token> refreshToken =  LocalDataStoreProvider.getRefreshTokenDataStore().updateDataAsync(token -> Single.just(Token.newBuilder().setToken(tokenModel.getRefreshToken().getToken()).setTokenType(TokenType.RefreshToken).build()));
        return Single.zip(accessToken,refreshToken,(accessToken1,refreshToken1)->{
            Log.println(Log.ASSERT, "IN Local Data Source  Access : ", String.valueOf(refreshToken1.getToken()));
            return true;
        });
    }



}
