package com.example.hotelbookingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapplication.model.MessageBooking;

public class BookSuccessActivity extends AppCompatActivity {
    private TextView txtMessage;
    private Button btnHome, btnOrders;
    MessageBooking message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_success);

        Intent intent = getIntent();
        message = (MessageBooking) intent.getSerializableExtra("message_booking");

        initView();
        setEvent();
    }

    private void setEvent() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookSuccessActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookSuccessActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        txtMessage = findViewById(R.id.textMessage);
        btnHome = findViewById(R.id.btnHome);
        btnOrders = findViewById(R.id.btnOrders);
        txtMessage.setText(message.getMessage());
    }
}