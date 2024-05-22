package com.example.hotelbookingapplication.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.example.hotelbookingapplication.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetLocationsThread extends Thread{
    Handler handler;

    public GetLocationsThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        getLocationsAPI(new LocationCallBack() {
            @Override
            public void getLocationComplete(List<String> locations) {
                if(locations != null){
                    Message message = new Message();
                    message.obj = locations;
                    handler.sendMessage(message);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (String location:locations) {
                                Log.d("AAA", location.toString());
                            }
                        }
                    });
                }
            }

            @Override
            public void getLocationFailed(Throwable throwable) {
                Log.d("AAA", "Error: " + throwable);
            }
        });
    }

    private interface LocationCallBack{
        void getLocationComplete(List<String> locations);
        void getLocationFailed(Throwable throwable);
    }

    private void getLocationsAPI(LocationCallBack locationCallBack){
        ApiService
                .apiService
                .getLocations()
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if(response.isSuccessful()){
                            List<String> locations = response.body();
                            locationCallBack.getLocationComplete(locations);
                        }else {
                            locationCallBack.getLocationFailed(new Exception("Get locations failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable throwable) {
                        locationCallBack.getLocationFailed(new Exception("Get locations failed"));
                    }
                });
    }
}
