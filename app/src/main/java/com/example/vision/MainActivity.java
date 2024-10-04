package com.example.vision;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.vision.language.LanguageController;
import com.example.vision.language.LanguageControllerFactory;
import com.example.vision.language.LanguageLocalDataSource;
import com.example.vision.language.LanguageRepository;
import com.example.vision.session_management.data_store.LocalDataStoreProvider;
import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.sign_in.viewmodels.SignInViewModel;
import com.example.vision.sign_in.viewmodels.SignInViewModelFactory;
import com.example.vision.splash_screen.SplashScreen;

import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    public LanguageController languageController;
    Observer<String> stringObserver =  s -> {
        if(!Objects.equals(s, Locale.getDefault().getLanguage())){
            Locale.setDefault(new Locale(s));

            Resources resources = getResources();
            Configuration config =resources.getConfiguration();
            config.setLocale(new Locale(s));

            resources.updateConfiguration(config, resources.getDisplayMetrics());

            // Recreate the current activity for the changes to take effect
            recreate();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languageController = new ViewModelProvider(
                this,
                new LanguageControllerFactory(new LanguageRepository(new LanguageLocalDataSource(this)))
        ).get(LanguageController.class);

        LocalDataStoreProvider.init(this);
        languageController.code.observe(this,stringObserver);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });






    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        languageController.code.removeObserver(stringObserver);
    }
}