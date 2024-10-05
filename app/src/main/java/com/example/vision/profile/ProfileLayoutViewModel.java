package com.example.vision.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vision.Profile;
import com.example.vision.profile.repository.Repository;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfileLayoutViewModel extends ViewModel {
    MutableLiveData<String> userName = new MutableLiveData<>("User Name");
    MutableLiveData<String> email = new MutableLiveData<>("Email");
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    Repository repository= new Repository();


    void setUserName(String name){
        repository.setUserName(name, stringResult -> {
            Log.println(Log.ASSERT, "Adasdsa", String.valueOf(stringResult.error()));
           if( stringResult.response()!=null){
               userName.postValue(name);
           }
        }, error->{
            Log.println(Log.ASSERT, "Adasdsa", String.valueOf(error.toString()));

        });
    }

    ProfileLayoutViewModel(){
     compositeDisposable.add( repository.getProfileObservable().subscribe(profile -> {
         userName.postValue(profile.getFullName());
         email.setValue(profile.getEmail());
         repository.dispose();
     },error->{

     }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public void getProfile(){
        repository.getProfile();
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

}