package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.dto.AuthResponse;
import com.example.hotelbookingapplication.dto.LoginRequest;
import com.example.hotelbookingapplication.dto.UserRequest;
import com.example.hotelbookingapplication.managers.interfaces.iSignIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallAuthSignUp {
    public static void get(UserRequest request, iSignIn callback){
        ApiService.apiService.signUp(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onError(new Exception("error code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
