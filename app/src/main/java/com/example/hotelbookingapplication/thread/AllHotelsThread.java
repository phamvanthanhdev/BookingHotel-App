package com.example.hotelbookingapplication.thread;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.model.Hotel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllHotelsThread extends Thread{
    Handler handler;
    TextView textView;

    public AllHotelsThread(Handler handler, TextView textView) {
        this.handler = handler;
        this.textView = textView;
    }

    @Override
    public void run() {
        GetAllHotels(new GetAllHotelsCallback() {
            @Override
            public void getHotelsSuccess(List<Hotel> hotels) {
                if(hotels != null){
                    for (Hotel hotel:hotels) {
                        Log.d("AAA", "Hotel : " + hotel.toString());
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Hotel " + hotels.get(0).getName());
                        }
                    });
                }else {
                    Log.d("AAA", "Hotels is null!");
                }
            }

            @Override
            public void getHotelsFailed(Throwable throwable) {
                Log.d("AAA", "Error: " + throwable);
            }
        });

    }

    public interface GetAllHotelsCallback{
        void getHotelsSuccess(List<Hotel> hotels);
        void getHotelsFailed(Throwable throwable);
    }

    public void GetAllHotels(GetAllHotelsCallback hotelsCallback){
        ApiService
                .apiService
                .getAllHotels()
                .enqueue(new Callback<List<Hotel>>() {
                    @Override
                    public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                        if(response.isSuccessful()){
                            List<Hotel> hotelList = response.body();
                            if(hotelList != null) {
                                hotelsCallback.getHotelsSuccess(hotelList);
                            }else {
                                Log.d("AAA", "Hotels is null! ");
                                hotelsCallback.getHotelsFailed(new Exception("Hotels is null"));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Hotel>> call, Throwable throwable) {
                        Log.d("AAA", "Get all hotels failed! " + throwable.getMessage());
                        hotelsCallback.getHotelsFailed(new Exception("Get all hotels failed! " + throwable.getMessage()));
                    }
                });
    }
}
