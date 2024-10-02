package com.example.vision.session_management;
import com.example.vision.Token;

public class Session {


    public static Session getSessionManagement() {
        return SESSION;
    }

    private static final Session SESSION = new Session();


    public Token accessToken;
    public Token refreshToken;


    public void signOut(){
        accessToken = null;
        refreshToken = null;
    }
}
