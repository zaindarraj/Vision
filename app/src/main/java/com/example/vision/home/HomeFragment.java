package com.example.vision.home;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vision.MainActivity;
import com.example.vision.R;
import com.example.vision.alerts.AlertsFragmanet;
import com.example.vision.databinding.UserProfileHeaderBinding;
import com.example.vision.language.LanguageController;
import com.example.vision.language.LanguageControllerFactory;
import com.example.vision.language.LanguageLocalDataSource;
import com.example.vision.language.LanguageRepository;
import com.example.vision.profile.ProfileLayout;
import com.example.vision.profile.ProfileLayoutViewModel;
import com.example.vision.session_management.data_store.LocalDataStoreProvider;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {
    public LanguageController languageController;

    Observer<Boolean> loggedOut = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if(aBoolean){
                homeViewModel.loggedOut.postValue(false);
                NavHostFragment.findNavController(getThis()).navigate(R.id.action_homeFragment_to_signInScreen);

            }
        }
    };



    HomeViewModel homeViewModel;
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
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        homeViewModel.loggedOut.observe(getActivity(), loggedOut);

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
                label.setText(R.string.account);
                drawerLayout.close();

                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                    homeViewModel.logout();
                    return true;
            }
        });



        languageController = new ViewModelProvider(
                this,
                new LanguageControllerFactory(new LanguageRepository(new LanguageLocalDataSource(getActivity())))
        ).get(LanguageController.class);


        navigationView.getMenu().findItem(R.id.nav_home).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                getChildFragmentManager().popBackStack();


                extracted(new HomeLayout());

                label.setText(R.string.home);
                drawerLayout.close();

                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_alerts).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                getChildFragmentManager().popBackStack();


                extracted(new AlertsFragmanet());

                label.setText(R.string.alerts);
                drawerLayout.close();

                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_language).setOnMenuItemClickListener(item -> {
            getChildFragmentManager().popBackStack();
            if(Objects.equals(  ((MainActivity) requireActivity()).languageController.code.getValue(), "ar")){
                ((MainActivity) requireActivity()).languageController.setLocalCode("en");

            }else{
                ((MainActivity) requireActivity()).languageController.setLocalCode("ar");

            }

            return true;
        });

        openDrawer = view.findViewById(R.id.settings_button);
        openDrawer.setOnClickListener(v -> drawerLayout.open());
    }

    public void extracted(Fragment fragment) {

        getChildFragmentManager().beginTransaction()
                .replace(R.id.home_container, fragment)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeViewModel.loggedOut.removeObserver(loggedOut);
    }
}