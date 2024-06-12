package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.dto.UserResponse;
import com.example.hotelbookingapplication.managers.interfaces.iProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallUpdatePassword {
    public static void update(String jwt,String newPass, iProfile callback){
        ApiService.apiService.updatePassword(jwt, newPass).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onError(new Exception("error code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
