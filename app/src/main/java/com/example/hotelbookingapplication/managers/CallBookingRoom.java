package com.example.hotelbookingapplication.managers;

import android.util.Log;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iBookingRoom;
import com.example.hotelbookingapplication.model.BookingRequest;
import com.example.hotelbookingapplication.model.MessageBooking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBookingRoom {
    public static void saveBookingRoom(BookingRequest request, iBookingRoom callback){
        ApiService.apiService.saveBooking(request).enqueue(new Callback<MessageBooking>() {
            @Override
            public void onResponse(Call<MessageBooking> call, Response<MessageBooking> response) {
                if(response.isSuccessful()){
                    Log.d("AAA", "success code " +response.code() + "body " + response.body().getMessage());
                    callback.onSuccess(response.body());
                }else{
                    Log.d("AAA", "error code " +response.code());
                    callback.onError(new Exception("Error code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<MessageBooking> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
