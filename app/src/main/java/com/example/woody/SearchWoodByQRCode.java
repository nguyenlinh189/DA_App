package com.example.woody;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woody.api.APIUtils;
import com.example.woody.dto.RequestImage;
import com.example.woody.model.Wood;
import com.example.woody.ui.LoadingDialog;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWoodByQRCode extends AppCompatActivity {

    private Button btn_quet_ma, libraryBtn;
    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    public static TextView predictText;
    private ImageView imageView, btnBack;
    private Bitmap picSelected;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_wood_by_qrcode);
        initView();

        libraryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI.normalizeScheme());
                startActivityForResult(i, 3);
            }
        });

        btn_quet_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ScannerView.class);
                startActivity(intent);
            }
        });
    }

    private void getWoodByQRCode(String encoded) {
        predictText.setVisibility(View.VISIBLE);
        APIUtils.getApiServiceInterface().readQRCode(new RequestImage(encoded)).enqueue(new Callback<Wood>() {
            @Override
            public void onResponse(Call<Wood> call, Response<Wood> response) {

                if(response.isSuccessful() && response.body()!=null){
                    Wood wood=response.body();
                    predictText.setText(wood.getScientificName());
                }else{
                    predictText.setText("Không tìm được gỗ");
                }
            }

            @Override
            public void onFailure(Call<Wood> call, Throwable t) {
                predictText.setText("Không tìm được gỗ");
            }
        });
    }

    private void initView() {
        btn_quet_ma = findViewById(R.id.btn_quet_ma);
        imageView = findViewById(R.id.captured_image);
        libraryBtn = findViewById(R.id.library_button);
        predictText=findViewById(R.id.predict);
        btnBack=findViewById(R.id.backBtn);
        loadingDialog= new LoadingDialog(getApplicationContext());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && data != null) {
            imageView.setVisibility(View.VISIBLE);
            Uri selectImage = data.getData();
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(selectImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //    picSelected = RotateBitmap(bitmap, 90);
                picSelected=bitmap;
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                picSelected.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encoded = Base64.getEncoder().encodeToString(byteArray);
                getWoodByQRCode(encoded);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}