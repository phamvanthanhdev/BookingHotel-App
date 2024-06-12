package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iBookedByEmail;
import com.example.hotelbookingapplication.model.BookedResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBookedByEmail {
    public static void get(String email, iBookedByEmail callback){
        ApiService.apiService.getBookedByEmail(email).enqueue(new Callback<List<BookedResponse>>() {
            @Override
            public void onResponse(Call<List<BookedResponse>> call, Response<List<BookedResponse>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess((List<BookedResponse>) response.body());
                }else {
                    callback.onError(new Exception("Error get booked by email!"));
                }
            }

            @Override
            public void onFailure(Call<List<BookedResponse>> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
