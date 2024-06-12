package com.example.hotelbookingapplication.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.model.Hotel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllHotelsAsyncTask extends AsyncTask<Void, Integer, List<Hotel>> {
    Context context;
    ProgressBar progressBar;
    TextView textView;
    List<Hotel> hotelList = new ArrayList<>();
    public AllHotelsAsyncTask(Context context, ProgressBar progressBar, TextView textView) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
    }

    @Override
    protected List<Hotel> doInBackground(Void... voids) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService
                .apiService
                .getAllHotels()
                .enqueue(new Callback<List<Hotel>>() {
                    @Override
                    public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                        if(response.isSuccessful()){
                            hotelList = response.body();
                            for (Hotel hotel:hotelList) {
                                Log.d("AAA", "Hotel : " + hotel.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Hotel>> call, Throwable throwable) {
                        Log.d("AAA", "Get all hotels failed! " + throwable.getMessage());
                    }
                });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hotelList;
    }

    @Override
    protected void onPostExecute(List<Hotel> result) {
        progressBar.setVisibility(View.GONE);
        textView.setText("Hotel " + result.get(0).getName());
    }
}
