package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iHotelDetails;
import com.example.hotelbookingapplication.model.HotelDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallHotelDetails {
    public static void get(Long id, iHotelDetails callback){
        ApiService.apiService.getHotelDetails(id).enqueue(new Callback<HotelDetails>() {
            @Override
            public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                if(response.isSuccessful()){
                    HotelDetails hotelDetails = response.body();
                    callback.onSuccess(hotelDetails);
                }
            }

            @Override
            public void onFailure(Call<HotelDetails> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
