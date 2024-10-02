package com.example.vision.sign_in.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vision.state.state.PasswordFieldState;
import com.example.vision.state.state.SignInState;

public class SignInViewModel extends ViewModel {

    private final MutableLiveData<SignInState> signInStateLiveData = new MutableLiveData<>(SignInState.no_internet);

    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LiveData<SignInState> getSignInStateLiveData() {
        return signInStateLiveData;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
