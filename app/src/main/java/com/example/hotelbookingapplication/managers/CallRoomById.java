package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iRoomById;
import com.example.hotelbookingapplication.model.Room;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallRoomById {
    public static void get(Long id, iRoomById callback){
        ApiService.apiService.getRoomById(id).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onError(new Exception("error code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
