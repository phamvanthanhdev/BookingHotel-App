package com.example.hotelbookingapplication.managers;

import android.util.Log;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iRoomsHotel;
import com.example.hotelbookingapplication.model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallRoomsHotel {
    public static void get(Long hotelId, iRoomsHotel callback){
        ApiService.apiService.getRoomsByHotelId(hotelId).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    Log.d("AAA", "Rooms hotels code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
