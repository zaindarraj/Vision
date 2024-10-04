package com.example.vision.language;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.splash_screen.SplashScreenViewModel;

public class LanguageControllerFactory  implements ViewModelProvider.Factory {

    private final LanguageRepository languageRepository;

    public LanguageControllerFactory(LanguageRepository languageRepository){

        this.languageRepository = languageRepository;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LanguageController(languageRepository);
    }
}