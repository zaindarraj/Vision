package com.example.vision.sign_in.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vision.session_management.repo.LocalRepository;

public class SignInViewModelFactory implements ViewModelProvider.Factory {
    private final LocalRepository localRepository;

    public SignInViewModelFactory(LocalRepository localRepository){
        this.localRepository = localRepository;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Log.println(Log.ASSERT, "Is Eger" , "fsdf");
        return (T) new SignInViewModel();
    }
}
