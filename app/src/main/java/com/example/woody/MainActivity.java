package com.example.woody;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.woody.api.APIUtils;
import com.example.woody.api.TokenManager;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;
import com.example.woody.model.WoodPagination;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    FragmentManager manager;
    FragmentTransaction transaction;
    private TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tokenManager=new TokenManager(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        APIUtils.getApiServiceInterface().getUserInfo("Bearer "+tokenManager.getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body()!=null){

                    User user=response.body();
                    SaveAccount.id=user.getId();
                    SaveAccount.email=user.getEmail();
                    SaveAccount.name=user.getName();
                    SaveAccount.phone=user.getPhone();
                    SaveAccount.address=user.getAddress();
                    SaveAccount.provider=user.getProvider();
                    SaveAccount.createAt=user.getCreateAt();
                    SaveAccount.createUpdate=user.getCreateUpdate();
                    SaveAccount.password=user.getPassword();
                    SaveAccount.role=user.getRole();
                    SaveAccount.listIdentify=user.getListIdentify();
                    SaveAccount.listFavouriteWood=user.getListFavouriteWood();
                    SaveAccount.enabled=user.isEnabled();

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.detailWord) {
                    navView.setVisibility(View.GONE);
                } else {
                    navView.setVisibility(View.VISIBLE);
                }
            }
        });
        manager=getSupportFragmentManager();

    }

}