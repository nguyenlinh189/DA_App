package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woody.adapter.HistoryIdentifyAdapter;
import com.example.woody.model.SaveAccount;
import com.example.woody.ui.library_wood.GridSpacingItemDecoration;

public class ListHistoryActivity extends AppCompatActivity {
    private RecyclerView rview;
    private TextView count;
    private HistoryIdentifyAdapter adapter;
    private ImageView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);
        initView();
        adapter=new HistoryIdentifyAdapter(SaveAccount.listIdentify);
        rview.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        rview.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        rview.setLayoutManager(gridLayoutManager);
    }

    private void initView() {
        rview=findViewById(R.id.recyclerView);
        count=findViewById(R.id.count);
        btnback=findViewById(R.id.backBtn);
    }
}