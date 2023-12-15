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
import androidx.camera.view.LifecycleCameraController;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woody.api.APIUtils;
import com.example.woody.api.RealPathUtil;
import com.example.woody.model.IdentifyWoodHistory;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.User;
import com.example.woody.model.Wood;
import com.example.woody.ui.LoadingDialog;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentifyActivity extends AppCompatActivity {
    private Button captureBtn, retakeBtn, identifyBtn, libraryBtn;
    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private TextView predictText;
    private ImageView imageView, btnBack;
    private int idDetected;
    private float percent;
    private Bitmap picSelected;
    private boolean connectNetwork=false;
    private ArrayList<Wood> dataOffline= new ArrayList<>();
    private LoadingDialog loadingDialog;
    private Uri mUri;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        initView();

        Intent intent=getIntent();
        user= (User) intent.getSerializableExtra("user");
        ActivityResultLauncher<String> cameraPermission=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result)
                    Toast.makeText(IdentifyActivity.this, "da chap nhan cho camera", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(IdentifyActivity.this, "chua chap nhan cho camera", Toast.LENGTH_SHORT).show();
            }
        });
        cameraPermission.launch(Manifest.permission.CAMERA);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).popBackStack();
//            }
//        });



        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });
        libraryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI.normalizeScheme());
                startActivityForResult(i, 3);
            }
        });
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strRealPath= RealPathUtil.getRealPath(getApplicationContext(), mUri);
                File file=new File(strRealPath);
                Log.e("duong dan thuc",strRealPath);
                Toast.makeText(getApplicationContext(), "Email"+user.getEmail(), Toast.LENGTH_SHORT).show();
                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part mPart=MultipartBody.Part.createFormData("file",file.getName(), requestBody);
                RequestBody requestBody1=RequestBody.create(MediaType.parse("application/json"),new Gson().toJson(user));
                APIUtils.getApiServiceInterface().addIdentify(mPart,requestBody1).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            SaveAccount.listIdentify=response.body().getListIdentify();
                            IdentifyWoodHistory i =response.body().getListIdentify().get(SaveAccount.listIdentify.size()-1);
                            if(i.getWood()!=null)
                                predictText.setText(i.getWood().getScientificName());
                            else
                                predictText.setText(i.getResult());
                            predictText.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "khong thanh cong", Toast.LENGTH_LONG).show();
                    }
                });
        }
        });

        retakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                retakeBtn.setVisibility(View.INVISIBLE);
                identifyBtn.setVisibility(View.INVISIBLE);
                captureBtn.setVisibility(View.VISIBLE);
                predictText.setVisibility(View.INVISIBLE);
            }
        });

        imageView.setVisibility(View.GONE);
        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(getApplicationContext());
        cameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                startCameraX(cameraProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, getExecutor());

    }

    private void initView() {
        captureBtn = findViewById(R.id.image_capture_button);
        imageView = findViewById(R.id.captured_image);
        retakeBtn = findViewById(R.id.retake_button);
        identifyBtn = findViewById(R.id.cbtn);
        libraryBtn = findViewById(R.id.library_button);
        predictText=findViewById(R.id.predict);
        btnBack=findViewById(R.id.backBtn);
        previewView = findViewById(R.id.viewFinder);
        loadingDialog= new LoadingDialog(getApplicationContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && data != null) {
            previewView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Uri selectImage = data.getData();
            mUri=selectImage;
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(selectImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //    picSelected = RotateBitmap(bitmap, 90);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            predictText.setVisibility(View.INVISIBLE);
            retakeBtn.setVisibility(View.VISIBLE);
            identifyBtn.setVisibility(View.VISIBLE);
            captureBtn.setVisibility(View.INVISIBLE);
        }
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this.getApplicationContext());
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).setTargetResolution(new Size(480, 360)).build();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

    }


    private void capturePhoto() {
        if(imageCapture==null) return;
        String name=System.currentTimeMillis()+"";
        ContentValues contentValues=new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");
        if(Build.VERSION.SDK_INT >Build.VERSION_CODES.P){
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/CameraXStable");
        }
        ImageCapture.OutputFileOptions outputFileOptions=new ImageCapture.OutputFileOptions.Builder(
                getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        ).build();
        imageCapture.takePicture(outputFileOptions, getExecutor(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Toast.makeText(getApplicationContext(), "Image captured "+outputFileResults.getSavedUri(), Toast.LENGTH_LONG).show();
                mUri=outputFileResults.getSavedUri();
                imageView.setVisibility(View.VISIBLE);
                try {
                    InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(outputFileResults.getSavedUri());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    //    picSelected = RotateBitmap(bitmap, 90);
                    imageView.setImageBitmap(RotateBitmap(bitmap, 90));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                predictText.setVisibility(View.INVISIBLE);
                retakeBtn.setVisibility(View.VISIBLE);
                identifyBtn.setVisibility(View.VISIBLE);
                captureBtn.setVisibility(View.INVISIBLE);
                previewView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                exception.printStackTrace();
            }
        });
    }


    private Bitmap toBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}

//    private void checkImageQuality(Bitmap image) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
////        Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
//        checkImageApi(encoded);
//    }
//    private void checkImageApi(String base64){
//        final String[] result = {""};
//        RequestPredict requestPredict=new RequestPredict(base64);
//        ApiService.apiService.checkWoodImage(requestPredict).enqueue(new Callback<ResponsePredict>() {
//            @Override
//            public void onResponse(Call<ResponsePredict> call, Response<ResponsePredict> response) {
//                ResponsePredict response1=response.body();
//                Toast.makeText(getApplicationContext(),response1.getWoodID(), Toast.LENGTH_SHORT).show();
//              //  loadingDialog.hideLoading();
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePredict> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"goi api khong thanh cong",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        ApiService.apiService.checkWoodImage((base64)).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                System.out.println(response.message());
//                loadingDialog.hideLoading();
//                if(response.body().equals("0")){
//                    showAlert();
//                }
//                else{
//                    classifyImage(picSelected,getApplicationContext());
//                    getWoodFromServer(idDetected);
//                }
//                call.cancel();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                loadingDialog.hideLoading();
//                System.out.println("asdf");
//            }
//        });
//
//    }

//    private void showAlert() {
//        AlertDialog.Builder alert= new AlertDialog.Builder(this.getApplicationContext());
//        alert.setTitle("Cảnh báo!");
//        alert.setMessage("Hình ảnh có thể không phải ảnh gỗ, bạn có muốn tiếp tục không?");
//        alert.setNeutralButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                previewView.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.INVISIBLE);
//                retakeBtn.setVisibility(View.INVISIBLE);
//                identifyBtn.setVisibility(View.INVISIBLE);
//                captureBtn.setVisibility(View.VISIBLE);
//                predictText.setVisibility(View.INVISIBLE);
//            }
//        });
//        alert.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                classifyImage(picSelected,getApplicationContext());
//                getWoodFromServer(idDetected);
//            }
//        });
//        alert.show();
//    }

//    private void classifyImage(Bitmap image, Context context) {
//        try {
//
//
//            ConvertModelMobilenetv3244 model = ConvertModelMobilenetv3244.newInstance(context);
//
//            // Creates inputs for reference.
//            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
//            ByteBuffer b= convertBitmapToByteBuffer(image);
//            inputFeature0.loadBuffer(b);
//
//
//            // Runs model inference and gets result.
//            ConvertModelMobilenetv3244.Outputs outputs = model.process(inputFeature0);
//            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//            // Runs model inference and gets result.
//
//            float[] confidences = outputFeature0.getFloatArray();
//
//            int maxPos = 0;
//            float maxConfidence = 0;
//            for (int i = 0; i < confidences.length; i++) {
//                if (confidences[i] > maxConfidence) {
//                    maxConfidence = confidences[i];
//                    maxPos = i;
//                }
//                System.out.println((i+1) + "tỉ lệ" + confidences[i]);
//            }
//            idDetected = maxPos + 1;
//            percent=confidences[maxPos];
//            // Releases model resources if no longer used.
//            model.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private ByteBuffer convertBitmapToByteBuffer(Bitmap bp) {
//        ByteBuffer imgData = ByteBuffer.allocateDirect(Float.BYTES*224*224*3);
//        imgData.order(ByteOrder.nativeOrder());
//        Bitmap bitmap = Bitmap.createScaledBitmap(bp,224,224,false);
//        int [] intValues = new int[224*62240];
//        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        // Convert the image to floating point.
//        int pixel = 0;
//
//        for (int i = 0; i < 224; ++i) {
//            for (int j = 0; j < 224; ++j) {
//                final int val = intValues[pixel++];
//
//                imgData.putFloat(((val>> 16) & 0xFF) / 255.f);
//                imgData.putFloat(((val>> 8) & 0xFF) / 255.f);
//                imgData.putFloat((val & 0xFF) / 255.f);
//            }
//        }
//        return imgData;
//    }

//    private void getWoodFromServer(int idDetected) {
//
//        APIUtils.getApiServiceInterface().getWoodById(idDetected).enqueue(new Callback<Wood>() {
//            @Override
//            public void onResponse(Call<Wood> call, Response<Wood> response) {
//                if(response.isSuccessful() && response.body()!=null){
//                    Wood wood=(Wood) response.body();
//                    Intent intent=new Intent(IdentifyActivity.this, DetailWoodActivity.class);
//                    intent.putExtra("wood", wood);
//                    intent.putExtra("fragment","Identify");
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Wood> call, Throwable t) {
//
//            }
//        });
//    }

//        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            connectNetwork = true;
//        }
//        else{
//            connectNetwork = false;
//            getWoodOffline();
//        }

//                Bitmap bImage = RotateBitmap(picSelected, 90);
//                if(!connectNetwork){
//                    predictText.setVisibility(View.VISIBLE);
//                    for (Wood item: dataOffline){
//                        if(item.getId()==idDetected){
//                            classifyImage(bImage,view.getContext());
//                            predictText.setText(item.getScientificName());
//                        }
//                    }
//                }
//                else{
//                   // loadingDialog.showLoading();
//                    checkImageQuality(bImage);
//                }
//                RequestBody requestBodyUser=RequestBody.create(MediaType.parse())
//                APIUtils.getApiServiceInterface().addIdentify(user, )