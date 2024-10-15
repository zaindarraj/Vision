package com.example.vision.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vision.session_management.SessionController;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    MutableLiveData<Boolean> loggedOut = new MutableLiveData<>(false);

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SessionController sessionController  = new SessionController();


    HomeViewModel(){
     compositeDisposable.add( sessionController.getSessionStateObservable().subscribe(res->{
         switch (res){
             case Session_Empty:
                 loggedOut.postValue(true);
         }
     }))  ;
    }

    void logout(){
        sessionController.signOut();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}