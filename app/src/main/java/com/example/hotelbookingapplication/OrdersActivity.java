package com.example.hotelbookingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapplication.adapter.BookedVerticalAdapter;
import com.example.hotelbookingapplication.managers.CallBookedByEmail;
import com.example.hotelbookingapplication.managers.interfaces.iBookedByEmail;
import com.example.hotelbookingapplication.model.BookedResponse;
import com.example.hotelbookingapplication.untils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView rvBookedRooms;
    List<BookedResponse> bookedRooms = new ArrayList<>();
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initView();
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

    private void initView() {
        rvBookedRooms = findViewById(R.id.recycleBookedRooms);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getBookedRoomByEmail();
    }

    private void setRoomsRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rvBookedRooms.setLayoutManager(layoutManager);
        BookedVerticalAdapter roomAdapter = new BookedVerticalAdapter(this, bookedRooms);
        rvBookedRooms.setAdapter(roomAdapter);
    }

    private void getBookedRoomByEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "").toString();
        CallBookedByEmail.get(email, new iBookedByEmail() {
            @Override
            public void onSuccess(List<BookedResponse> bookedResponses) {
                progressBar.setVisibility(View.GONE);
                bookedRooms = bookedResponses;
                setRoomsRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }


}