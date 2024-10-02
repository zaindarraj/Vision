package com.example.vision.session_management;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.vision.Token;
import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.session_management.repo.Repository;
import com.example.vision.state.state.SessionState;

import java.util.Optional;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SessionController {
    private final Repository repository;
    private final BehaviorSubject<SessionState> sessionStateObservable= BehaviorSubject.create();


    Consumer<Token> accessTokenConsumer = token -> {

        sessionStateObservable.onNext(SessionState.Session_Ready);
    };

    public SessionController(LocalRepository localRepository){
            repository = new Repository(localRepository);
    }


    public void checkSessionState() {
        repository.fetchAccessToken(accessTokenConsumer, throwable -> {
            Log.println(Log.ASSERT, "accessTokenConsumer", "Token is not available");
            sessionStateObservable.onNext(SessionState.Session_Empty);
    });
    }


    public BehaviorSubject<SessionState> getSessionStateObservable() {
        return sessionStateObservable;
    }


    public Optional<Token> getAccessToken(){
        if(!repository.getAccessToken().isPresent()){
            sessionStateObservable.onNext(SessionState.Session_Empty);
            return Optional.empty();
        }
        return repository.getAccessToken();
    }

    public void dispose() {
        repository.dispose();
    }

    void signOut(){
        repository.dispose();
        sessionStateObservable.onNext(SessionState.Session_Empty);
    }
}
