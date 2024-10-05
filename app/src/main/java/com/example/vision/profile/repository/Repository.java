package com.example.vision.profile.repository;

import android.util.Log;

import com.example.vision.Errors;
import com.example.vision.Profile;
import com.example.vision.Token;
import com.example.vision.profile.ProfileResponse;
import com.example.vision.session_management.Session;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava3.Result;

public class Repository {
    BehaviorSubject<ProfileResponse> profileObservable = BehaviorSubject.create();
    RemoteRepository remoteRepository= new RemoteRepository();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BehaviorSubject<ProfileResponse> getProfileObservable() {
        return profileObservable;
    }

    public void getProfile(){
        compositeDisposable.add( remoteRepository.getProfile().subscribe(result -> {

            if(result.response().errorBody() != null){
                Gson gson = new Gson();
                Errors errors = gson.fromJson(result.response().errorBody().string(), Errors.class);


            }else{
                if (result.response() != null && result.response().body()!=null) {
                    profileObservable.onNext(result.response().body());
                }else{
                    Log.println(Log.ASSERT,"Errorsddsss" ,result.error().toString());

                }
            }

        },error->{
            Log.println(Log.ASSERT,"Errorssss" , error.toString());
        }));
    }


    public void setUserName(String userName, Consumer<Result<ResponseBody>> onSuccess, Consumer<Throwable> onError){
        compositeDisposable.add(remoteRepository.remoteDataSource.setUserName(userName).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(onSuccess,onError));
    }

   public void dispose(){
        compositeDisposable.dispose();
    }


}
