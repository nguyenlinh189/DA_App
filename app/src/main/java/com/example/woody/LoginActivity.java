package com.example.woody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woody.api.APIUtils;
import com.example.woody.api.TokenManager;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;
import com.example.woody.model.UserDTO;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email, pass;
    private TextView txtRegiter;
    private ImageView btnBack;
    private Button btnLogin, btnGoogle;
    private TokenManager tokenManager;
    private int RC_SIGN_IN=65;
    private GoogleSignInClient mGoogleSignInClient;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        if(tokenManager.getToken()!=null){
            APIUtils.getApiServiceInterface().getUserInfo("Bearer "+tokenManager.getToken()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        intent=new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
        intent=getIntent();
        if(intent.getStringExtra("email")!=null)
            email.setText(intent.getStringExtra("email"));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semail=email.getText().toString();
                String spass=pass.getText().toString();
                Log.e("tag",semail+" "+spass);
                if(semail.isEmpty())
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                else if(spass.isEmpty())
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                else {
                    UserDTO userDTO=new UserDTO();
                    userDTO.setEmail(semail);
                    userDTO.setPassword(spass);

                    APIUtils.getApiServiceInterface().login(userDTO).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                String token=response.body();
                                tokenManager.saveToken(token);
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else
                            {
                                Toast.makeText(getApplicationContext(), "Email hoặc Password không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Goi API khong thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("117424537794-affb5362l2mtcooo289g4enjhtgghd49.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(getApplicationContext(),gso);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });
        txtRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        email=findViewById(R.id.eemail);
        pass=findViewById(R.id.epass);
        btnBack=findViewById(R.id.backBtn);
        btnLogin=findViewById(R.id.btn_login);
        btnGoogle=findViewById(R.id.btn_google);
        txtRegiter=findViewById(R.id.txt_register);
        tokenManager=new TokenManager(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account=completedTask.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(),account.getEmail(),Toast.LENGTH_SHORT).show();
            System.out.println(account.getIdToken());
            APIUtils.getApiServiceInterface().checkIdTokenGoogle(account.getIdToken()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        if(response.body().equalsIgnoreCase("invalid id token")==true){
                            Toast.makeText(getApplicationContext(),"login google fail id token invalid",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            tokenManager.saveToken(response.body());
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "goi api khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(ApiException e){
            Toast.makeText(getApplicationContext(), "login google fail: "+e.toString()+"", Toast.LENGTH_LONG).show();
        }
    }
}