package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    private ImageView img_status, nhandienanh;
    private TextView txt_status, sotien,magiaodich, noidung,thoigian, phuongthuc, nganhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        Intent intent=getIntent();
        if(intent!=null && intent.getExtras()!=null){
            Uri url=intent.getData();
            String vnp_ResponseCode=url.getQueryParameter("vnp_ResponseCode");
            if(vnp_ResponseCode.equalsIgnoreCase("00")){
                img_status.setImageResource(R.drawable.success);
                txt_status.setText("Thanh toán thành công");
            }
            else{
                img_status.setImageResource(R.drawable.fail);
                txt_status.setText("Thanh toán không thành công");
            }
            sotien.setText(Integer.parseInt(url.getQueryParameter("vnp_Amount"))/100+"");
            magiaodich.setText(url.getQueryParameter("vnp_TransactionNo"));
            noidung.setText(url.getQueryParameter("vnp_OrderInfo"));
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                String payDate = inputFormat1.format(inputFormat.parse(url.getQueryParameter("vnp_PayDate")));
                thoigian.setText(payDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            phuongthuc.setText(url.getQueryParameter("vnp_CardType"));
            nganhang.setText(url.getQueryParameter("vnp_BankCode"));
        }else{
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
        }
        nhandienanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(ResultActivity.this, IdentifyActivity.class);
               startActivity(intent);
            }
        });
    }

    private void initView() {
        img_status=findViewById(R.id.img_status);
        txt_status=findViewById(R.id.tv_status);
        sotien=findViewById(R.id.amount);
        magiaodich=findViewById(R.id.magiaodich);
        noidung=findViewById(R.id.noidung);
        thoigian=findViewById(R.id.thoigian);
        phuongthuc=findViewById(R.id.phuongthuc);
        nganhang=findViewById(R.id.nganhang);
        nhandienanh=findViewById(R.id.nhandienanh);
    }
}