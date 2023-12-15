package com.example.woody.ui.login_logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.woody.ListFavWoodActivity;
import com.example.woody.ListHistoryActivity;
import com.example.woody.LoginActivity;
import com.example.woody.R;
import com.example.woody.api.APIUtils;
import com.example.woody.api.TokenManager;
import com.example.woody.model.Glossary;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforAccFragment extends Fragment {
    private Button btn_logout;
    private ImageView ic_account;
    private TextView name, email;
    private User user;
    private GoogleSignInClient mGoogleSignInClient;
    private LinearLayout mlistFav, mlistHis;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_infor_acc, container, false);
        initView(view);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("117424537794-affb5362l2mtcooo289g4enjhtgghd49.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(getContext(),gso);

        TokenManager tokenManager=new TokenManager(getContext());
        name.setText(SaveAccount.name);
        email.setText(SaveAccount.email);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenManager.removeToken();
                if (SaveAccount.provider.equalsIgnoreCase("google")){
                    signOut();
                }
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mlistHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), ListHistoryActivity.class);
                startActivity(intent);
            }
        });
        mlistFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), ListFavWoodActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initView(View view) {
        btn_logout=view.findViewById(R.id.btn_logout);
        name=view.findViewById(R.id.nameUser);
        email=view.findViewById(R.id.emailUser);
        mlistFav=view.findViewById(R.id.mlistFav);
        mlistHis=view.findViewById(R.id.mlistHis);
    }
    private final void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "dang xuat tai khoan", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
