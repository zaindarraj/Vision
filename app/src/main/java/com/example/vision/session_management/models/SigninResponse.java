package com.example.vision.session_management.models;

import com.example.vision.Token;

public class SigninResponse {
    TokenModel accessToken;
    TokenModel refreshToken;

    public SigninResponse(Token accessToken, Token refreshToken) {
        this.accessToken = new TokenModel(accessToken.getToken());

        this.refreshToken = new TokenModel(refreshToken.getToken());
    }


    public TokenModel getAccessToken() {
        return accessToken;
    }

    public TokenModel getRefreshToken() {
        return refreshToken;
    }
}
