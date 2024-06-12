package com.example.hotelbookingapplication.managers;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.managers.interfaces.iBookedById;
import com.example.hotelbookingapplication.model.BookedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBookedById {
    public static void get(Long bookedId, iBookedById callback){
        ApiService.apiService.getBookedById(bookedId).enqueue(new Callback<BookedResponse>() {
            @Override
            public void onResponse(Call<BookedResponse> call, Response<BookedResponse> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onError(new Exception("Code : " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<BookedResponse> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
