package com.example.vision.welcome_screen;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.vision.MainActivity;
import com.example.vision.R;

import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.functions.Consumer;

public class WelcomeFragment extends Fragment {

    private WelcomeViewModel mViewModel;
    Spinner spinner;
    Button signInButton;
    Observer<String> languageObserver =  s -> {
        if(Objects.equals(s, "en")){
            spinner.setSelection(0,false);

        }else{
            spinner.setSelection(1,false);

        }
    };




    public WelcomeFragment getWelcomeFragment() {
        return this;
    }

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                mViewModel.languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Optional: Customize dropdown layout
        spinner.setAdapter(adapter);


        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_welcomeFragment2_to_signInScreen);

            }
        });


        spinner.setSelection(((MainActivity) requireActivity()).languageController.code.getValue().equals("en")?0:1,false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((MainActivity) requireActivity()).languageController.setLocalCode("en");
                } else {
                    ((MainActivity) requireActivity()).languageController.setLocalCode("ar");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((MainActivity) requireActivity()).languageController.code.observe(getActivity(),languageObserver);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) requireActivity()).languageController.code.removeObserver(languageObserver);
    }
}