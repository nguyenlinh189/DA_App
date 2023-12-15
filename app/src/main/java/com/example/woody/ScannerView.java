package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.example.woody.api.APIUtils;
import com.example.woody.model.Wood;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerView extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result result) {
        SearchWoodByQRCode.predictText.setVisibility(View.VISIBLE);
        APIUtils.getApiServiceInterface().getWoodById(Integer.parseInt(result.getText())).enqueue(new Callback<Wood>() {
            @Override
            public void onResponse(Call<Wood> call, Response<Wood> response) {
                if(response.isSuccessful() && response.body()!=null){
                    SearchWoodByQRCode.predictText.setText(response.body().getScientificName());
                }
                else{
                    SearchWoodByQRCode.predictText.setText("Không tìm được gỗ từ mã QR");
                }
            }

            @Override
            public void onFailure(Call<Wood> call, Throwable t) {
                SearchWoodByQRCode.predictText.setText("Không tìm được gỗ từ mã QR");
            }
        });
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}