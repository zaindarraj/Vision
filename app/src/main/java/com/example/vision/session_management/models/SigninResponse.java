package com.example.vision.session_management.models;

import com.example.vision.Token;

public class SigninResponse {
    TokenModel accessToken;
    TokenModel refreshToken;


    public TokenModel getAccessToken() {
        return accessToken;
    }

    public TokenModel getRefreshToken() {
        return refreshToken;
    }
}
