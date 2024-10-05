package com.example.vision.sign_in;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vision.MainActivity;
import com.example.vision.R;
import com.example.vision.session_management.repo.LocalRepository;
import com.example.vision.sign_in.viewmodels.SignInViewModel;
import com.example.vision.sign_in.viewmodels.SignInViewModelFactory;
import com.example.vision.state.state.SignInState;
import com.example.vision.welcome_screen.WelcomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class SignInScreen extends Fragment {
    //use language logic from welcome screen.


    Fragment getThisFragment(){
        return this;
    }

    TextInputEditText userName;
    TextInputEditText password;

    Button signInButton;

    SignInViewModel signInViewModel;
    Observer<SignInState> sessionStateObserver = signInState -> {
        if(signInState != signInViewModel.signInState){
            switch (signInState){
                case Signed_in:
                    NavHostFragment.findNavController(getThisFragment()).navigate(R.id.action_signInScreen_to_homeFragment);
                    break;
                case no_internet:
                    Toast.makeText(getActivity(), "Internal Problem, Check Your Connection", Toast.LENGTH_SHORT).show();
                    break;
                case Wrong_creds:
                    Toast.makeText(getActivity(), "Wrong User Name or Password", Toast.LENGTH_SHORT).show();
                    break;


            }
        }

    };

    private WelcomeViewModel mViewModel;
    Spinner spinner;
    ImageButton backButton;
    Observer<String> languageObserver = s -> {
        if(Objects.equals(s, "en")){
            spinner.setSelection(0,false);

        }else{
            spinner.setSelection(1,false);

        }
    };
    public SignInScreen() {
        // Required empty public constructor
    }


    public static SignInScreen newInstance(String param1, String param2) {
        SignInScreen fragment = new SignInScreen();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
        signInViewModel = new ViewModelProvider(
                this,
                new SignInViewModelFactory(new LocalRepository())
        ).get(SignInViewModel.class);
        signInViewModel.getSignInStateLiveData().observe(getActivity(), sessionStateObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.userName);
        password = view.findViewById(R.id.password);

        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    signInViewModel.signIn(userName.getText().toString(), password.getText().toString());
                }
            }
        });
        spinner = view.findViewById(R.id.language_spinner);
        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> NavHostFragment.findNavController(SignInScreen.this).popBackStack());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                mViewModel.languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Optional: Customize dropdown layout
        spinner.setAdapter(adapter);
        ((MainActivity) requireActivity()).languageController.code.observe(getActivity(),languageObserver);

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signInViewModel.getSignInStateLiveData().removeObserver(sessionStateObserver);
    }
}