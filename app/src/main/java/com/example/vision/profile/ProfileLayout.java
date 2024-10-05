package com.example.vision.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vision.R;
import com.example.vision.databinding.FragmentProfileLayoutBinding;
import com.example.vision.databinding.UserProfileHeaderBinding;

public class ProfileLayout extends Fragment {
    ProfileLayoutViewModel profileLayoutViewModel;

    FragmentProfileLayoutBinding binding;

    EditText userName;
    Button submit;

    public static ProfileLayout newInstance() {
        return new ProfileLayout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileLayoutViewModel = new ViewModelProvider(getActivity()).get(ProfileLayoutViewModel.class);
        binding = FragmentProfileLayoutBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(profileLayoutViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submit = view.findViewById(R.id.submitUserName);
        userName = view.findViewById(R.id.userName);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please provide your name first", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Working..", Toast.LENGTH_SHORT).show();

                    profileLayoutViewModel.setUserName(userName.getText().toString());
                }
            }
        });
    }

    private ViewModelStoreOwner getThis() {
        return  this;
    }


}