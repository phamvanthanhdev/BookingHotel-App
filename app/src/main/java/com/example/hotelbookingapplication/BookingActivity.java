package com.example.hotelbookingapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapplication.managers.CallBookingRoom;
import com.example.hotelbookingapplication.managers.CallHotelDetails;
import com.example.hotelbookingapplication.managers.CallRoomById;
import com.example.hotelbookingapplication.managers.interfaces.iBookingRoom;
import com.example.hotelbookingapplication.managers.interfaces.iHotelDetails;
import com.example.hotelbookingapplication.managers.interfaces.iRoomById;
import com.example.hotelbookingapplication.model.BookingRequest;
import com.example.hotelbookingapplication.model.HotelDetails;
import com.example.hotelbookingapplication.model.MessageBooking;
import com.example.hotelbookingapplication.model.Room;
import com.example.hotelbookingapplication.untils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {
    private Long roomId, hotelId;
    private TextView txtHotelAddress, txtRoomType, txtChildren, txtAdults, txtRoomPrice, txtCheckIn, txtCheckOut, txtNightNumber, txtGuestEmail;
    private EditText edtNote;
    private EditText edtGuestFullname, edtGuestPhone;
    private ImageView imgRoom;
    private ImageButton btnRemoveChild, btnAddChild, btnRemoveAdult, btnAddAdult, btnSelectCheckIn, btnSelectCheckOut;
    Button btnConfirmBooking;
    private Room roomGlobal;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Intent intent = getIntent();
        roomId = intent.getLongExtra("roomId", 0);
        hotelId = intent.getLongExtra("hotelId", 0);

        initView();
        setEvent();
        setBottomNavView();
    }

    private void setBottomNavView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.btnMenuHome){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                if(item.getItemId() == R.id.btnMenuOrders){
                    startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                }
                if(item.getItemId() == R.id.btnMenuProfile){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
                if(item.getItemId() == R.id.btnMenuLogout){
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }

                return false;
            }
        });
    }

    private void setEvent() {
        btnRemoveChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(txtChildren.getText().toString()) > 0){
                    int childNumber = Integer.parseInt(txtChildren.getText().toString()) - 1;
                    txtChildren.setText(childNumber+"");
                }
            }
        });

        btnRemoveAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(txtAdults.getText().toString()) > 1){
                    btnRemoveAdult.setVisibility(View.VISIBLE);
                    int adultNumber = Integer.parseInt(txtAdults.getText().toString()) - 1;
                    txtAdults.setText(adultNumber+"");
                }
            }
        });

        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childNumber = Integer.parseInt(txtChildren.getText().toString()) + 1;
                txtChildren.setText(childNumber+"");
            }
        });

        btnAddAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adultNumber = Integer.parseInt(txtAdults.getText().toString()) + 1;
                txtAdults.setText(adultNumber+"");
            }
        });

        btnSelectCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.selectDate(txtCheckIn, BookingActivity.this);
            }
        });

        btnSelectCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.selectDate(txtCheckOut, BookingActivity.this);
            }
        });

        txtCheckOut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strCheckIn = txtCheckIn.getText().toString();
                long distanceDay = Common.caculateDistanceDay(strCheckIn,s.toString());
                if(distanceDay < 0){
                    txtNightNumber.setText("cho " + 1 + " đêm");
                    Toast.makeText(BookingActivity.this, "Ngày check out phải lớn hơn ngày check in!", Toast.LENGTH_SHORT).show();
                    Common.selectDate(txtCheckOut, BookingActivity.this);
                }else {
                    txtNightNumber.setText("cho " + distanceDay + " đêm");
                    BigDecimal priceDecimal = roomGlobal.getRoomPrice().multiply(BigDecimal.valueOf(distanceDay));
                    String totalPrice = Common.convertCurrencyVietnamese(priceDecimal);
                    txtRoomPrice.setText(totalPrice + " VNĐ");
                }
            }
        });

        txtCheckIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strCheckOut = txtCheckOut.getText().toString();
                long distanceDay = Common.caculateDistanceDay(s.toString(),strCheckOut);
                if(distanceDay < 0){
                    txtNightNumber.setText("cho " + 1 + " đêm");
                    Toast.makeText(BookingActivity.this, "Ngày check out phải lớn hơn ngày check in!", Toast.LENGTH_SHORT).show();
                    Common.selectDate(txtCheckIn, BookingActivity.this);
                    txtRoomPrice.setText(Common.convertCurrencyVietnamese(roomGlobal.getRoomPrice()) + " VNĐ");
                }else {
                    txtNightNumber.setText("cho " + distanceDay + " đêm");
                    BigDecimal priceDecimal = roomGlobal.getRoomPrice().multiply(BigDecimal.valueOf(distanceDay));
                    String totalPrice = Common.convertCurrencyVietnamese(priceDecimal);
                    txtRoomPrice.setText(totalPrice + " VNĐ");
                }
            }
        });

        btnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogProcessing();
                String checkInDate = txtCheckIn.getText().toString().trim(), checkOutDate = txtCheckOut.getText().toString().trim();
                String guestFullName = edtGuestFullname.getText().toString();
                String guestEmail = txtGuestEmail.getText().toString();
                int numAdults = Integer.parseInt(txtAdults.getText().toString());
                int numChildrent = Integer.parseInt(txtChildren.getText().toString());
                String note = edtNote.getText().toString().trim();
                long distanceDay = Common.caculateDistanceDay(txtCheckIn.getText().toString(),txtCheckOut.getText().toString());
                BigDecimal price = roomGlobal.getRoomPrice().multiply(BigDecimal.valueOf(distanceDay));
                BookingRequest request = new BookingRequest(
                        checkInDate, checkOutDate
                        , guestFullName, guestEmail, numAdults,
                        numChildrent, note, price, roomId, hotelId);

                saveBooking(request);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getHotelById(hotelId);
        getRoomById(roomId);
    }

    private void initView() {
        txtHotelAddress = findViewById(R.id.textHotelAddress);
        txtRoomType = findViewById(R.id.textRoomType);
        imgRoom = findViewById(R.id.imageRoom);
        txtAdults = findViewById(R.id.textAdults);
        txtChildren = findViewById(R.id.textChildren);
        btnRemoveChild = findViewById(R.id.btnRemoveChild);
        btnAddChild = findViewById(R.id.btnAddChild);
        btnRemoveAdult = findViewById(R.id.btnRemoveAdult);
        btnAddAdult = findViewById(R.id.btnAddAdult);
        txtRoomPrice = findViewById(R.id.textRoomPrice);
        btnSelectCheckIn = findViewById(R.id.btnSelectCheckIn);
        btnSelectCheckOut = findViewById(R.id.btnSelectCheckOut);
        txtCheckIn = findViewById(R.id.textCheckIn);
        txtCheckOut = findViewById(R.id.textCheckOut);
        txtNightNumber = findViewById(R.id.textNightNumber);
        btnConfirmBooking =findViewById(R.id.btnConfirmBooking);
        edtGuestFullname = findViewById(R.id.edtGuestFullname);
        edtGuestPhone = findViewById(R.id.edtGuestPhone);
        txtGuestEmail = findViewById(R.id.txtGuestEmail);
        edtNote = findViewById(R.id.edittextNote);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtCheckIn.setText(simpleDateFormat.format(new Date()));
        txtCheckOut.setText(simpleDateFormat.format(new Date()));

        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "").toString();
        txtGuestEmail.setText(email);

    }

    private void getHotelById(Long hotelId){
        CallHotelDetails.get(hotelId, new iHotelDetails() {
            @Override
            public void onSuccess(HotelDetails hotelDetails) {
                txtHotelAddress.setText(hotelDetails.getAddress());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }

    private void getRoomById(Long id){
        CallRoomById.get(id, new iRoomById() {
            @Override
            public void onSuccess(Room room) {
                roomGlobal = room;
                Bitmap bitmap = Common.decodeBase64ToBitmap(room.getPhoto());
                imgRoom.setImageBitmap(bitmap);
                txtRoomType.setText(room.getRoomType());
                txtRoomPrice.setText(Common.convertCurrencyVietnamese(room.getRoomPrice()) + "VNĐ");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }

    private void saveBooking(BookingRequest request){
        CallBookingRoom.saveBookingRoom(request, new iBookingRoom() {
            @Override
            public void onSuccess(MessageBooking message) {
                if(message.getCode() == 200) {
                    Intent intent = new Intent(BookingActivity.this, BookSuccessActivity.class);
                    intent.putExtra("message_booking", message);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(BookingActivity.this, BookingFailedActivity.class);
                    intent.putExtra("message_booking", message);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(BookingActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDialogProcessing(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_process);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        dialog.show();
    }
}