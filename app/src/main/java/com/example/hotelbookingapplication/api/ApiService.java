package com.example.hotelbookingapplication.api;

import com.example.hotelbookingapplication.dto.AuthResponse;
import com.example.hotelbookingapplication.dto.LoginRequest;
import com.example.hotelbookingapplication.dto.UserRequest;
import com.example.hotelbookingapplication.dto.UserResponse;
import com.example.hotelbookingapplication.model.BookedResponse;
import com.example.hotelbookingapplication.model.BookingRequest;
import com.example.hotelbookingapplication.model.Hotel;
import com.example.hotelbookingapplication.model.HotelDetails;
import com.example.hotelbookingapplication.model.MessageBooking;
import com.example.hotelbookingapplication.model.Room;
import com.example.hotelbookingapplication.untils.Containts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Containts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);



    ///api/ubox/get_creative?id_device=ubx1945_30207954&id_ktv=ubx1945&id_partner=5&type=1
    @GET("api/hotel/all-hotels")
    Call<List<Hotel>> getAllHotels();

    @GET("api/hotel/get/locations")
    Call<List<String>> getLocations();

    @GET("api/hotel/get/{id}")
    Call<HotelDetails> getHotelDetails(@Path("id") Long id);

    @GET("api/room/get-rooms-hotel/{hotelId}")
    Call<List<Room>> getRoomsByHotelId(@Path("hotelId") Long hotelId);

    @GET("api/room/get/{id}")
    Call<Room> getRoomById(@Path("id") Long id);

    @POST("api/booking/book")
    Call<MessageBooking> saveBooking(@Body BookingRequest request);

    @GET("api/booking/get-by-email")
    Call<List<BookedResponse>> getBookedByEmail(@Query("guestEmail") String email);

    @GET("api/booking/get/{bookedId}")
    Call<BookedResponse> getBookedById(@Path("bookedId") Long bookedId);

    @GET("api/booking/cancel-booking/{bookedId}")
    Call<MessageBooking> cancelBookingRoom(@Path("bookedId") Long bookedId);

    @POST("auth/signin")
    Call<AuthResponse> signIn(@Body LoginRequest loginRequest);
    @POST("auth/signup")
    Call<AuthResponse> signUp(@Body UserRequest userRequest);
    @GET("api/users/profile")
    Call<UserResponse> getProfile(@Header("Authorization") String jwt);

    @PUT("api/users/update")
    Call<UserResponse> updatePassword(@Header("Authorization") String jwt, @Query("password") String newPassword);

    /*@Multipart
    @POST("/api/uploadFile")
        //Call<DataMessage> uploadFileAPI(@PartMap Map<String, RequestBody> params);
    Call<DataMessage> uploadFileAPI( @Part MultipartBody.Part image,
                                     @Part("box_id") RequestBody boxId,
                                     @Part("ktv_id") RequestBody ktvId,
                                     @Part("campaign_id") RequestBody campaignId);*/

}