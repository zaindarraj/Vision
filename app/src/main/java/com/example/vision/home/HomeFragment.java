package com.example.vision.home;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vision.R;
import com.example.vision.databinding.UserProfileHeaderBinding;
import com.example.vision.profile.ProfileLayout;
import com.example.vision.profile.ProfileLayoutViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {


    DrawerLayout drawerLayout;

    TextView label;

    ProfileLayoutViewModel profileLayoutViewModel;

    ImageButton openDrawer;

    NavigationView navigationView;

    UserProfileHeaderBinding userProfileHeaderBinding;

    HomeLayout homeLayout = new HomeLayout();


    Fragment getThis(){
        return  this;
    }

    UserProfileHeaderBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileLayoutViewModel = new ViewModelProvider(getActivity()).get(ProfileLayoutViewModel.class);
        binding = UserProfileHeaderBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());

        binding.setViewmodel(profileLayoutViewModel);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileLayoutViewModel.getProfile();
        label= view.findViewById(R.id.home_label);
        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView= view.findViewById(R.id.navigation);
        View headerView = binding.getRoot();
        navigationView.addHeaderView(headerView);
        navigationView.getMenu().findItem(R.id.nav_account).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                extracted(new ProfileLayout());
                label.setText("Profile");
                drawerLayout.close();

                return true;
            }
        });


        navigationView.getMenu().findItem(R.id.nav_home).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                getChildFragmentManager().popBackStack();


                extracted(new HomeLayout());

                label.setText("Home");
                drawerLayout.close();

                return true;
            }
        });

        openDrawer = view.findViewById(R.id.settings_button);
        openDrawer.setOnClickListener(v -> drawerLayout.open());
    }

    public void extracted(Fragment fragment) {

        getChildFragmentManager().beginTransaction()
                .replace(R.id.home_container, fragment)
                .commit();
    }
}