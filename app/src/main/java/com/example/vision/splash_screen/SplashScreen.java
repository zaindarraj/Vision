package com.example.vision.splash_screen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vision.R;
import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.state.state.SessionState;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreen extends Fragment {
    SplashScreenViewModel splashScreenViewModel;

    Observer<SessionState> sessionStateObserver =  sessionState -> {
        switch (sessionState){
            case Session_Ready:
                NavHostFragment.findNavController(this).navigate(R.id.action_splashScreen_to_homeFragment);
                break;
            case Session_Empty:
                NavHostFragment.findNavController(this).navigate(R.id.action_splashScreen_to_welcomeFragment2);
                break;
        }

    };



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
        splashScreenViewModel  = new ViewModelProvider(
                this,
                new SplashScreenViewModelFactory(new LocalRepository())
        ).get(SplashScreenViewModel.class);
        splashScreenViewModel.getSessionStateMutableLiveData().observe(this,sessionStateObserver
        );


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        splashScreenViewModel.getSessionStateMutableLiveData().removeObserver(sessionStateObserver);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}