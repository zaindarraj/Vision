package com.example.vision.splash_screen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vision.R;
import com.example.vision.session_management.repo.LocalRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreen extends Fragment {




    public SplashScreen() {
        // Required empty public constructor
    }


    public static SplashScreen newInstance(String param1, String param2) {
        SplashScreen fragment = new SplashScreen();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreenViewModel splashScreenViewModel = new ViewModelProvider(
                this,
                new SplashScreenViewModelFactory(new LocalRepository(getActivity()))
        ).get(SplashScreenViewModel.class);
        splashScreenViewModel.getSessionStateMutableLiveData().observe(this, sessionState -> {
            Log.println(Log.ASSERT, "Session State", sessionState.toString());
        }
        );

        splashScreenViewModel.checkSessionState();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }
}