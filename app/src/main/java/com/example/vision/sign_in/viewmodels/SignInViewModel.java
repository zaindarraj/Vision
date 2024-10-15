package com.example.vision.sign_in.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vision.session_management.SessionController;
import com.example.vision.session_management.data_store.LocalDataStore;
import com.example.vision.session_management.data_store.RemoteDataSource;
import com.example.vision.state.state.PasswordFieldState;
import com.example.vision.state.state.SessionState;
import com.example.vision.state.state.SignInState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignInViewModel extends ViewModel {
    SessionController sessionController = new SessionController();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public SignInState signInState = SignInState.init;

    SignInViewModel() {
        compositeDisposable.add(sessionController.getSessionStateObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
            if (result == SessionState.Session_Ready) {
                signInStateLiveData.postValue(SignInState.Signed_in);
            } else if (result == SessionState.Session_Empty) {
                signInStateLiveData.postValue(SignInState.no_internet);
            }else if(result == SessionState.SESSION_WRONG){
                signInStateLiveData.postValue(SignInState.Wrong_creds);

            }
        }));
    }

    private final MutableLiveData<SignInState> signInStateLiveData = new MutableLiveData<>(SignInState.init);

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


    public void signIn(String email, String password) {
        sessionController.sign(email, password);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
