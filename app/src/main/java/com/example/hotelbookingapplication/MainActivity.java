package com.example.hotelbookingapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapplication.adapter.HotelVerticalAdapter;
import com.example.hotelbookingapplication.model.Hotel;
import com.example.hotelbookingapplication.thread.AllHotelsThread;
import com.example.hotelbookingapplication.untils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;
    private static final int MSG_UPDATE_NUMBER = 100;
    private static final int MSG_UPDATE_NUMBER_DONE = 101;
    List<Hotel> hotelList = new ArrayList<>();
    private ImageView imgHotelSuggest;
    private TextView txtHotelNameSuggest, txtHotelPriceSuggest, txtHotelCitySuggest;
    private GridLayout gridLayoutHotels;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        //Khai báo một Handler chờ nhận dữ liệu từ Thread và xử lý UI
        listenerHandlerGetHotels();

        setBottomNavView();
    }

    private void setBottomNavView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.btnMenuHome){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
                if(item.getItemId() == R.id.btnMenuOrders){
                    startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                    return true;
                }
                if(item.getItemId() == R.id.btnMenuProfile){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
                }
                if(item.getItemId() == R.id.btnMenuLogout){
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    return true;
                }

                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        //Start Thread lấy dữ liệu all hotels từ Server
        getAllHotels();

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnableConfig);
    }

    private void setViewHotelSuggest(Hotel hotelSuggest) {
        Bitmap bitmap = Common.decodeBase64ToBitmap(hotelSuggest.getPhoto());
        imgHotelSuggest.setImageBitmap(bitmap);
        txtHotelNameSuggest.setText(hotelSuggest.getName());
        txtHotelCitySuggest.setText(hotelSuggest.getCity());
        txtHotelPriceSuggest.setText(Common.convertCurrencyVietnamese(hotelSuggest.getPrice()) + "đ/đêm");
    }

    private void getAllHotels(){
        new AllHotelsThread(mHandler).start();
    }

    // Mỗi 10s hàm run được thực thi 1 lần
    protected Runnable mRunnableConfig = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                Hotel hotelSuggest = getRandomHotelSuggest();
                setViewHotelSuggest(hotelSuggest);
            });
            mHandler.postDelayed(this, 10*1000);
        }
    };


    private void initViews() {
        imgHotelSuggest = findViewById(R.id.imageHotelSuggest);
        txtHotelNameSuggest = findViewById(R.id.textHotelNameSuggest);
        txtHotelPriceSuggest = findViewById(R.id.textHotelPriceSuggest);
        txtHotelCitySuggest = findViewById(R.id.textHotelCitySuggest);
        gridLayoutHotels = findViewById(R.id.gridLayoutHotels);
        progressBar = findViewById(R.id.progressBar);
    }

    private Hotel getRandomHotelSuggest(){
        Random random =new Random();
        return hotelList.get(random.nextInt(hotelList.size()));
    }

    //UI Thread
    //Lắng nghe message trả về từ handler
    private void listenerHandlerGetHotels() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if(msg.obj != null) {
                    progressBar.setVisibility(View.GONE);
                    hotelList = (List<Hotel>) msg.obj;
                    if (hotelList.isEmpty()) {
                        for (Hotel hotel : hotelList) {
                            Log.d("AAA", "Hotel : " + hotel.toString());
                        }
                    }

                    //Runnable start
                    mRunnableConfig.run();
                    setHotelRecycler();
                }
            }
        };
    }

    //Thêm 1 include Hotel vào gridLayout
    private void addViewGroupHotel(){
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = 0;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        layoutParams.setMargins(0, 0, 10, 10);

        ViewGroup viewGroup = new ViewGroup(this) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                //Lấy view từ Layout tĩnh trong folder layout

            }
        };

        viewGroup.setLayoutParams(layoutParams);
        gridLayoutHotels.addView(viewGroup);
    }


    private void setHotelRecycler(){
        RecyclerView rvHotels = findViewById(R.id.rvHotels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvHotels.setLayoutManager(layoutManager);
        HotelVerticalAdapter hotelVerticalAdapter = new HotelVerticalAdapter(this, hotelList);
        rvHotels.setAdapter(hotelVerticalAdapter);
    }



}