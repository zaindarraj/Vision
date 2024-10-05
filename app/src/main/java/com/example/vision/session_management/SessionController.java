package com.example.vision.session_management;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.vision.Token;
import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.session_management.repo.Repository;
import com.example.vision.state.state.SessionState;

import java.util.Optional;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SessionController {
    private final Repository repository;
    private final BehaviorSubject<SessionState> sessionStateObservable= BehaviorSubject.create();
CompositeDisposable compositeDisposable = new CompositeDisposable();

    Consumer<Token> accessTokenConsumer = token -> {

        sessionStateObservable.onNext(SessionState.Session_Ready);
    };


    public void sign(String email, String password){
        repository.signIn(email,password, result -> {
            if(result.error() != null){
                Log.println(Log.ASSERT, "While Signing In Error was",  result.error().toString());
                sessionStateObservable.onNext(SessionState.Session_Empty);
            }else{
                if(result.response().body()!= null){
                 compositeDisposable.add(  repository.setLocals(result.response().body()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                         writtenBool->{
                             if(writtenBool){
                                 sessionStateObservable.onNext(SessionState.Session_Ready);
                             }else {
                                 sessionStateObservable.onNext(SessionState.Session_Empty);
                             }
                         },
                         erroBool->{
                             sessionStateObservable.onNext(SessionState.Session_Empty);

                         }
                 )) ;

                }else{
                    sessionStateObservable.onNext(SessionState.Session_Empty);
                }
            }

        }, error->{});
    }
    public SessionController( ){
            repository = new Repository(new LocalRepository());
    }


    public void checkSessionState() {
        repository.fetchAccessToken(accessTokenConsumer, throwable -> {
            Log.println(Log.ASSERT, "accessTokenConsumer", throwable.toString());
            sessionStateObservable.onNext(SessionState.Session_Empty);
    });
    }

    public String getRefreshToken(){
        return repository.getRefreshToken();
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
        compositeDisposable.dispose();
        sessionStateObservable.onNext(SessionState.Session_Empty);
    }
}
