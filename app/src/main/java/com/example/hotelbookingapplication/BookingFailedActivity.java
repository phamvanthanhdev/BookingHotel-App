package com.example.hotelbookingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapplication.model.MessageBooking;

public class BookingFailedActivity extends AppCompatActivity {
    private TextView txtMessage;
    private Button btnHome, btnBackToBooking;
    MessageBooking message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_failed);
        Intent intent = getIntent();
        message = (MessageBooking) intent.getSerializableExtra("message_booking");

        initView();
        setEvent();
    }

    private void setEvent() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingFailedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnBackToBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        txtMessage = findViewById(R.id.textMessage);
        btnHome = findViewById(R.id.btnHome);
        btnBackToBooking = findViewById(R.id.btnBackToBooking);
        txtMessage.setText(message.getMessage());
    }
}