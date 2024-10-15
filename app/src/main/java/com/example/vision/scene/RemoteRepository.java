package com.example.vision.scene;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.adapter.rxjava3.Result;

public class RemoteRepository {
    final private  CompositeDisposable disposable = new CompositeDisposable();

    final private RemoteDataSource remoteDataSource =new RemoteDataSource();


    Single<Result<DetectedObjects>> getPrediction(File file){
        return  remoteDataSource.predict(file);
    }

}
