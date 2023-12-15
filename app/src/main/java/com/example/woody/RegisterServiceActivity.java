package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.woody.adapter.ServiceAdapter;
import com.example.woody.api.APIUtils;
import com.example.woody.model.Service;
import com.example.woody.model.User;
import com.example.woody.ui.library_wood.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterServiceActivity extends AppCompatActivity {
    private RecyclerView rview;
    private User user;
    private List<Service>listService=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);
        rview=findViewById(R.id.rview);
        Intent intent=getIntent();
        user= (User) intent.getSerializableExtra("user");
        ServiceAdapter adapter=new ServiceAdapter(user);
        APIUtils.getApiServiceInterface().getService().enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    listService=response.body();
                    adapter.setListService(listService);
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });
        rview.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        rview.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        rview.setLayoutManager(gridLayoutManager);

    }
}