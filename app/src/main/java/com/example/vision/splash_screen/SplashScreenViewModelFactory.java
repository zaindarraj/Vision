package com.example.vision.splash_screen;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.vision.session_management.repo.LocalRepository;

import kotlin.Suppress;

public class SplashScreenViewModelFactory implements ViewModelProvider.Factory {

    private final LocalRepository localRepository;

    SplashScreenViewModelFactory(LocalRepository localRepository){
        Log.println(Log.ASSERT, "Is Eger" , "fsdf");

        this.localRepository = localRepository;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Log.println(Log.ASSERT, "Is Eger" , "fsdf");
        return (T) new SplashScreenViewModel(localRepository);
    }
}
