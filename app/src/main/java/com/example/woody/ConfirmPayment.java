package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woody.adapter.VoucherAdapter;
import com.example.woody.api.APIUtils;
import com.example.woody.model.UsedService;
import com.example.woody.model.Voucher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPayment extends AppCompatActivity {
    private TextView name,duration, dateEnd, price, voucher, totalMoney;
    private RecyclerView rview;
    private List<Voucher>listVoucher=new ArrayList<>();
    private Button btnthanhtoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);
        initView();
        Intent intent=getIntent();
        UsedService usedService= (UsedService) intent.getSerializableExtra("usedService");
        name.setText("Sử dụng nhận diện gỗ dựa trên hình ảnh trong "+usedService.getService().getName());
        duration.setText(usedService.getService().getDuration()+" tháng");
        dateEnd.setText(usedService.getDateEnd());
        price.setText(usedService.getService().getPrice()+" đồng");
        totalMoney.setText(usedService.getTotalMoney()+" đồng");
        Voucher voucherItem=usedService.getVoucher();
        if(voucherItem!=null)
            voucher.setText(voucherItem.getAmount()+" đồng");
        else
            voucher.setText("--");
        VoucherAdapter voucherAdapter=new VoucherAdapter(listVoucher, usedService);
        rview.setAdapter(voucherAdapter);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rview.setLayoutManager(manager);
        APIUtils.getApiServiceInterface().getListVoucherByUsedService(usedService).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    listVoucher=response.body();
                    voucherAdapter.setList(listVoucher);
                    rview.setAdapter(voucherAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {

            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    APIUtils.getApiServiceInterface().getPay_url(usedService,"http://com.da.wood/vnpay_return").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(response.body()));
                            view.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(view.getContext(), "khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initView() {
        name=findViewById(R.id.name);
        duration=findViewById(R.id.duration);
        dateEnd=findViewById(R.id.dateEnd);
        price=findViewById(R.id.price);
        voucher=findViewById(R.id.voucher);
        totalMoney=findViewById(R.id.totalMoney);
        rview=findViewById(R.id.rview);
        btnthanhtoan=findViewById(R.id.btnthanhtoan);
    }
}