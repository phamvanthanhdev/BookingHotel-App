package com.example.hotelbookingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapplication.adapter.RoomVerticalAdapter;
import com.example.hotelbookingapplication.managers.CallRoomsHotel;
import com.example.hotelbookingapplication.managers.interfaces.iRoomsHotel;
import com.example.hotelbookingapplication.model.Room;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {
    private RecyclerView rvRooms;
    private List<Room> roomList = new ArrayList<>();
    private Long hotelId;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Intent intent = getIntent();
        hotelId = intent.getLongExtra("hotelId", 0);

        initViews();
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
        super.onStart();
        getRoomsByHotelId(hotelId);
    }

    private void initViews() {
        rvRooms = findViewById(R.id.recycleViewRooms);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setRoomsRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rvRooms.setLayoutManager(layoutManager);
        RoomVerticalAdapter roomAdapter = new RoomVerticalAdapter(this, roomList, hotelId);
        rvRooms.setAdapter(roomAdapter);
    }

    private void getRoomsByHotelId(Long hotelId){
        CallRoomsHotel.get(hotelId, new iRoomsHotel() {
            @Override
            public void onSuccess(List<Room> rooms) {
                progressBar.setVisibility(View.GONE);
                roomList = rooms;
                for (Room r:roomList) {
                    Log.d("AAA", r.toString());
                }
                setRoomsRecycler();
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }
}