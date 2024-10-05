package com.example.vision.session_management.repo;

import android.util.Log;

import com.example.vision.Token;
import com.example.vision.TokenType;
import com.example.vision.session_management.Session;
import com.example.vision.session_management.models.SignInDataModel;
import com.example.vision.session_management.models.SigninResponse;
import com.example.vision.session_management.models.TokenModel;
import com.example.vision.session_management.repo.remote.RemoteRepository;


import java.util.Optional;

import javax.security.auth.callback.Callback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import
        io.reactivex.rxjava3.core.Observable
;
import retrofit2.adapter.rxjava3.Result;

public class Repository {

    private final LocalRepository localRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Session session = Session.getSessionManagement();
    private final RemoteRepository remoteRepository = new RemoteRepository();

    public Repository(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    //Called when access token is needed
    public Optional<Token> getAccessToken(){
        return Optional.ofNullable(session.accessToken);
    }

    public Single<Boolean> setLocals(SigninResponse tokenModel){
       return localRepository.setTokens(tokenModel);
    }

    void setSessionTokens(SigninResponse tokenModel){
        session.accessToken =  Token.newBuilder().setToken(tokenModel.getAccessToken().getToken()).setTokenType(TokenType.AccessToken).build();
        session.refreshToken = Token.newBuilder().setToken(tokenModel.getRefreshToken().getToken()).setTokenType(TokenType.RefreshToken).build();
    }


    public void signIn(String email, String password , Consumer<Result<SigninResponse>> onSuccess, Consumer<Throwable> onError){
        disposables.add(remoteRepository.signIn(email,password).doOnSuccess(signinResponseResult ->
        {
            if(signinResponseResult.response() != null && signinResponseResult.response().body() != null){
                setSessionTokens(signinResponseResult.response().body());
            }
        }).subscribe(onSuccess,onError));
    }

    //Called when the app starts
    public void fetchAccessToken(Consumer<Token> onSuccess, Consumer<Throwable> onError) {
        if(session.accessToken != null){

            Single<Token> single = Single.just(session.accessToken);
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onSuccess, onError);
            disposables.add(disposable);
        }else{
            Disposable disposable = localRepository.getAccessToken().map(token -> {
                        if(!token.getAccessToken().getToken().isEmpty()){
                            this.session.accessToken = Token.newBuilder().setToken(token.getAccessToken().getToken()).setTokenType(TokenType.AccessToken).build();
                            return session.accessToken;

                        }
                        if(token.getRefreshToken().getToken().isEmpty()){
                            this.session.refreshToken = Token.newBuilder().setToken(token.getRefreshToken().getToken()).setTokenType(TokenType.RefreshToken).build();

                        }
                        return  null;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            onSuccess,
                            onError
                    );
            disposables.add(disposable);
        }

    }


    public String getRefreshToken(){
        return session.refreshToken.getToken();
    }


    public void dispose() {
        session.signOut();
        disposables.clear();
    }

}