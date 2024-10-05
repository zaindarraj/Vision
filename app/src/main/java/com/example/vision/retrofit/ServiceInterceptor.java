package com.example.vision.retrofit;

import android.util.Log;

import com.example.vision.session_management.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if(request.header("No-Authentication") == null){
            //->Need Tokens
            if(Session.getSessionManagement().accessToken == null ){
                throw new IOException();

            }
            Log.println(Log.ASSERT,"Access Token", Session.getSessionManagement().accessToken.getToken());
           request =  request.newBuilder().header("Authorization", "Bearer " + Session.getSessionManagement().accessToken.getToken()).build();
        }
        return  chain.proceed(request);
    }
}
