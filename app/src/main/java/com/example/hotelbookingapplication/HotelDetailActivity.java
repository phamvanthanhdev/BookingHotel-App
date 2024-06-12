package com.example.hotelbookingapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapplication.managers.CallHotelDetails;
import com.example.hotelbookingapplication.managers.interfaces.iHotelDetails;
import com.example.hotelbookingapplication.model.HotelDetails;
import com.example.hotelbookingapplication.untils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HotelDetailActivity extends AppCompatActivity {
    Long hotelId;
    private Button btnRoomsHotel;
    private ImageView imgHotel;
    private TextView txtHotelName, txtHotelCity, txtHotelPrice, txtHotelRating, txtHotelAddress, txtHotelDescription;
    private RatingBar ratingBar;
//    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        Intent intent = getIntent();
        hotelId = intent.getLongExtra("hotelId", 0);

        initView();
        setEvent();
//        setBottomNavView();
    }

    /*private void setBottomNavView() {
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
    }*/

    private void initView() {
        btnRoomsHotel = findViewById(R.id.btnRoomsHotel);
        txtHotelName= findViewById(R.id.textHotelName);
        txtHotelCity = findViewById(R.id.textHotelCity);
        txtHotelPrice = findViewById(R.id.textHotelPrice);
        txtHotelRating = findViewById(R.id.textHotelRating);
        txtHotelAddress = findViewById(R.id.textHotelAddress);
        txtHotelDescription = findViewById(R.id.textHotelDescription);
        ratingBar = findViewById(R.id.ratingBarHotel);
        imgHotel = findViewById(R.id.imageHotel);
    }

    private void displayViewData(HotelDetails hotelDetails){
        imgHotel.setImageBitmap(Common.decodeBase64ToBitmap(hotelDetails.getPhoto()));
        txtHotelName.setText(hotelDetails.getName());
        txtHotelCity.setText(hotelDetails.getCity());
        txtHotelPrice.setText(Common.convertCurrencyVietnamese(hotelDetails.getPrice()) + " VNĐ");
        txtHotelRating.setText(hotelDetails.getRating()+"");
        txtHotelAddress.setText(hotelDetails.getAddress());
        txtHotelDescription.setText(hotelDetails.getDescription());
        ratingBar.setRating((float)hotelDetails.getRating());
    }

    private void setEvent(){
        btnRoomsHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelDetailActivity.this, RoomsActivity.class);
                intent.putExtra("hotelId", hotelId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getHotelDetails(hotelId);
    }

    private void getHotelDetails(Long id){
        CallHotelDetails.get(id, new iHotelDetails() {
            @Override
            public void onSuccess(HotelDetails hotelDetails) {
                Log.d("AAA", hotelDetails.toString());
                displayViewData(hotelDetails);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }
}