package com.example.vision.splash_screen;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vision.session_management.SessionController;
import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.state.state.SessionState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreenViewModel extends ViewModel {
    SessionController sessionController ;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<SessionState> sessionStateMutableLiveData = new MutableLiveData<>(SessionState.Session_Unknown);




    SplashScreenViewModel(LocalRepository localRepository){
            sessionController = new SessionController(localRepository);
            Disposable disposable  = sessionController.getSessionStateObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(sessionStateMutableLiveData::postValue);
            disposables.add(disposable);
        }



        public MutableLiveData<SessionState> getSessionStateMutableLiveData() {
            return sessionStateMutableLiveData;
        }

        public void checkSessionState() {
            sessionController.checkSessionState();
        }





    @Override
    protected void onCleared() {
        //call dispose when view model is destroyed
        sessionController.dispose();
        disposables.clear();
        super.onCleared();
    }
}
