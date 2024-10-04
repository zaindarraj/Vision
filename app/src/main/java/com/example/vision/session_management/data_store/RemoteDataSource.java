package com.example.vision.session_management.data_store;

import com.example.vision.retrofit.API;
import com.example.vision.retrofit.RetrofitInstance;
import com.example.vision.session_management.models.SignInDataModel;
import com.example.vision.session_management.models.SigninResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava3.Result;


public class RemoteDataSource {

    private final API api = RetrofitInstance.getInstance();

    public Single<Result<SigninResponse>> signIn(String userName, String password){
        return api.signIn(new SignInDataModel(userName, password));
    }
}
