package com.example.hotelbookingapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapplication.thread.GetLocationsThread;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchHotelActivity extends AppCompatActivity {
    private AutoCompleteTextView autoTextLocation, autoTextNight;
    private Handler getLocationsHandler;
    private TextInputEditText edtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotel);


        AnhXa();
        SetEvent();

        //Lắng nghe data trả về từ server
        listenerHandlerGetLocations();

        //Start thread get locations
        new GetLocationsThread(getLocationsHandler).start();
    }

    private void AnhXa() {
        autoTextLocation = findViewById(R.id.autoTextDiaDiem);
        edtDate = findViewById(R.id.edtDate);
        autoTextNight = findViewById(R.id.autoTextNight);
        DisplayDataNight(); // Hiển thị dữ liệu đêm
    }

    private void SetEvent(){
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(edtDate);
            }
        });
    }

    private void DisplayDataNight(){
        List<String> nights = new ArrayList<>();
        nights.add("1 đêm");
        nights.add("2 đêm");
        nights.add("3 đêm");
        nights.add("4 đêm");
        nights.add("5 đêm");
        ArrayAdapter<String> nightAdapter = new ArrayAdapter<>(SearchHotelActivity.this, R.layout.list_item_dropdown, nights);
        autoTextNight.setAdapter(nightAdapter);
    }

    private void listenerHandlerGetLocations(){
        getLocationsHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                List<String> locations = (List<String>) msg.obj;
                ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(SearchHotelActivity.this, R.layout.list_item_dropdown, locations);
                autoTextLocation.setAdapter(locationAdapter);
            }
        };
    }

    private void pickDate(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchHotelActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}