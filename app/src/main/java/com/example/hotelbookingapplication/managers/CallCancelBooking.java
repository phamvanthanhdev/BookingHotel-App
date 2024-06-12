package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iCancelBooking;
import com.example.hotelbookingapplication.model.MessageBooking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallCancelBooking {
    public static void cancel(Long bookedId, iCancelBooking callback){
        ApiService.apiService.cancelBookingRoom(bookedId).enqueue(new Callback<MessageBooking>() {
            @Override
            public void onResponse(Call<MessageBooking> call, Response<MessageBooking> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onError(new Exception("Code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<MessageBooking> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
