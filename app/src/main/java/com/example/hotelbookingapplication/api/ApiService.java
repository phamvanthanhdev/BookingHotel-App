package com.example.hotelbookingapplication.api;

import com.example.hotelbookingapplication.model.Hotel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.14:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);



    ///api/ubox/get_creative?id_device=ubx1945_30207954&id_ktv=ubx1945&id_partner=5&type=1
    @GET("api/hotel/all-hotels")
    Call<List<Hotel>> getAllHotels();

    @GET("api/hotel/get/locations")
    Call<List<String>> getLocations();

    /*@Multipart
    @POST("/api/uploadFile")
        //Call<DataMessage> uploadFileAPI(@PartMap Map<String, RequestBody> params);
    Call<DataMessage> uploadFileAPI( @Part MultipartBody.Part image,
                                     @Part("box_id") RequestBody boxId,
                                     @Part("ktv_id") RequestBody ktvId,
                                     @Part("campaign_id") RequestBody campaignId);*/

}