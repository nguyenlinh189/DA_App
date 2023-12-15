package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;
import com.example.woody.ui.library_wood.GridSpacingItemDecoration;
import com.example.woody.ui.library_wood.WoodAdapter;

public class ListFavWoodActivity extends AppCompatActivity {
    private RecyclerView rview;
    private WoodAdapter woodAdapter;
    private TextView numWood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fav_wood);
//        Toast.makeText(this, SaveAccount.listFavouriteWood.size()+"", Toast.LENGTH_SHORT).show();
        initView();

        User user=new User();
        user.setId(SaveAccount.id);
        user.setEmail(SaveAccount.email);
        user.setAddress(SaveAccount.address);
        user.setName(SaveAccount.name);
        user.setProvider(SaveAccount.provider);
        user.setAddress(SaveAccount.address);
        user.setCreateAt(SaveAccount.createAt);
        user.setCreateUpdate(SaveAccount.createUpdate);
        user.setListFavouriteWood(SaveAccount.listFavouriteWood);
        user.setListIdentify(SaveAccount.listIdentify);
        user.setRole(SaveAccount.role);
        user.setEnabled(SaveAccount.enabled);
        user.setPassword(SaveAccount.password);
        woodAdapter=new WoodAdapter(getApplicationContext(), SaveAccount.listFavouriteWood, user);
        rview.setAdapter(woodAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        rview.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        rview.setLayoutManager(gridLayoutManager);
    }

    private void initView() {
        rview=findViewById(R.id.recyclerView);
        numWood=findViewById(R.id.numberWood);

    }
}