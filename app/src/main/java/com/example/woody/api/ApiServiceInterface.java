package com.example.woody.api;

import com.example.woody.dto.RequestImage;
import com.example.woody.model.AppendixCITES;
import com.example.woody.model.CategoryWood;
import com.example.woody.model.GeographicalArea;
import com.example.woody.model.Glossary;
import com.example.woody.model.IdentifyWoodHistory;
import com.example.woody.model.PlantFamily;
import com.example.woody.model.Preservation;
import com.example.woody.model.Service;
import com.example.woody.model.UsedService;
import com.example.woody.model.User;
import com.example.woody.model.UserDTO;
import com.example.woody.model.Voucher;
import com.example.woody.model.Wood;
import com.example.woody.model.WoodPagination;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiServiceInterface {
    // nguoi dung
    @POST("/user/login")
    Call<String> login(@Body UserDTO userDTO);
    @POST("/user/register")
    Call<String> register(@Body UserDTO userDTO);
    @GET("/user/getUserInfo")
    Call<User> getUserInfo(@Header("Authorization")String authorization);
    @POST("/user/updateListFavourite")
    Call<User> updateListFavourite(@Body User user);
    @POST("/user/deleteIdentifyHistory")
    Call<String>deleteIdentify(@Body IdentifyWoodHistory identifyWoodHistory);

    @Multipart
    @POST("user/addIdentify")
    Call<User>addIdentify(@Part MultipartBody.Part file, @Part("user") RequestBody user);

    // xac nhan thong tin dang nhap tu google va luu thong tin
    @GET("/google/checkIdTokenGoogle")
    Call<String>checkIdTokenGoogle(@Query("idToken")String idTokenGoogle);
    // go
    @GET("/wood/get")
    Call<WoodPagination> getListWood(@Query("category")int category,
                                     @Query("pageNum")int pageNum,
                                     @Query("key")String key,
                                     @Query("family")String familys,
                                     @Query("area")String areas,
                                     @Query("color")String colors,
                                     @Query("cites")String cites,
                                     @Query("preservation")String preservations);
    @GET("/wood/getById")
    Call<Wood>getWoodById(@Query("id")int id);

    @POST("/wood/readQRCode")
    Call<Wood> readQRCode(@Body RequestImage requestImage);

    // thuat ngu
    @GET("/glossary/getAll")
    Call<List<Glossary>>getGlossary(@Query(value = "key")String key);
    // la danh sach category wood
    @GET("/category-wood/getAll")
    Call<List<CategoryWood>>getCategoryWood();

    // lay danh sach cac du lieu de search phan wood tieng viet
    @GET("/plantfamily/getByCategory")
    Call<List<PlantFamily>>getListFamilyByCategory(@Query("category")int category);

    @GET("/area/getByCategoryWood")
    Call<List<GeographicalArea>>getListAreaByCategoryWood(@Query("category")int category);

    @GET("/cites/get")
    Call<List<AppendixCITES>>getListCites();
    @GET("/preservation/get")
    Call<List<Preservation>>getListPreservation();

    // kiam tra dich vu da dang ki hay chua
    @GET("/usedService/get")
    Call<UsedService>getUsedServiceById(@Query("id")int id);
    // lay danh sach cac dich vu
    @GET("/service/get")
    Call<List<Service>>getService();
    // lay duong dan thanh toans
    @POST("/usedService/payment")
    Call<String>getPay_url(@Body UsedService usedService, @Query("vnp_return") String vnp_return);

    @POST("/usedService/getListVouchers")
    Call<List<Voucher>>getListVoucherByUsedService(@Body UsedService usedService);
}
