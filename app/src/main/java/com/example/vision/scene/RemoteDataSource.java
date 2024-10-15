package com.example.vision.scene;

import android.util.Log;

import com.example.vision.retrofit.API;
import com.example.vision.retrofit.RetrofitInstance;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava3.Result;

public class RemoteDataSource {
    private final API api = RetrofitInstance.getInstance();



    Single<Result<DetectedObjects>> predict(File file){
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        Log.println(Log.ASSERT, "adadasdasd", filePart.body().contentType().toString());
        return  api.predict(filePart);
    }
}
