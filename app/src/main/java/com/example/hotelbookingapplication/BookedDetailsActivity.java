package com.example.hotelbookingapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapplication.managers.CallBookedById;
import com.example.hotelbookingapplication.managers.CallCancelBooking;
import com.example.hotelbookingapplication.managers.CallHotelDetails;
import com.example.hotelbookingapplication.managers.CallRoomById;
import com.example.hotelbookingapplication.managers.interfaces.iBookedById;
import com.example.hotelbookingapplication.managers.interfaces.iCancelBooking;
import com.example.hotelbookingapplication.managers.interfaces.iHotelDetails;
import com.example.hotelbookingapplication.managers.interfaces.iRoomById;
import com.example.hotelbookingapplication.model.BookedResponse;
import com.example.hotelbookingapplication.model.HotelDetails;
import com.example.hotelbookingapplication.model.MessageBooking;
import com.example.hotelbookingapplication.model.Room;
import com.example.hotelbookingapplication.untils.Common;
import com.example.hotelbookingapplication.zalopay.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class BookedDetailsActivity extends AppCompatActivity {
    Long bookedId;
    private TextView txtHotelAddress, txtHotelName, txtRoomType, txtChildren, txtAdults, txtTotalPrice, txtCheckIn, txtCheckOut ,txtNote;
    private TextView txtGuestFullname, txtGuestPhone, txtGuestEmail, txtInfomationCode, txtBookingStatus;
    private ImageView imgRoom;
    private Button btnCancelBooking, btnPayment;
    private BookedResponse bookedResponse;
    private TextView txtContent, txtMessage;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_details);

        Intent intent = getIntent();
        bookedId = intent.getLongExtra("bookedId", 0);

        initView();
        setEvent();
        initZaloPay();
    }

    private void initZaloPay() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);
    }

    private void setEvent() {
        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(BookedDetailsActivity.this, "Xác nhận hủy phòng", "Bạn có chắc chắn hủy đơn đặt phòng này không?");

            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateOrder orderApi = new CreateOrder();
                    JSONObject data = orderApi.createOrder(String.valueOf(100));
                    String code = data.getString("return_code");
                    Toast.makeText(BookedDetailsActivity.this, "return_code: " + code, Toast.LENGTH_LONG).show();
                    Log.d("AAA", "code: " + code);
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder( BookedDetailsActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                BookedDetailsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Hoan tat thanh toan
                                        Toast.makeText(BookedDetailsActivity.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Toast.makeText(BookedDetailsActivity.this, "payment cancel!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Toast.makeText(BookedDetailsActivity.this, "payment error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.d("AAA", "err: " + e);
                    Toast.makeText(BookedDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getBookedById(bookedId);
    }

    private void initView() {
        txtHotelAddress = findViewById(R.id.textHotelAddress);
        txtRoomType = findViewById(R.id.textRoomType);
        imgRoom = findViewById(R.id.imageRoom);
        txtAdults = findViewById(R.id.textAdults);
        txtChildren = findViewById(R.id.textChildren);
        txtTotalPrice = findViewById(R.id.textTotalPrice);
        txtCheckIn = findViewById(R.id.textCheckIn);
        txtCheckOut = findViewById(R.id.textCheckOut);
        btnCancelBooking =findViewById(R.id.btnCancelBooking);
        txtGuestFullname = findViewById(R.id.textGuestFullname);
        txtGuestPhone = findViewById(R.id.textGuestPhoneNumber);
        txtGuestEmail = findViewById(R.id.textGuestEmail);
        txtNote = findViewById(R.id.textNote);
        txtHotelName = findViewById(R.id.textHotelName);
        txtInfomationCode = findViewById(R.id.textInfomationCode);
        txtBookingStatus = findViewById(R.id.textBookingStatus);
        btnPayment = findViewById(R.id.btnPayment);
    }

    public void getBookedById(Long bookedId){
        CallBookedById.get(bookedId, new iBookedById() {
            @Override
            public void onSuccess(BookedResponse response) {
                getHotelDetails(response.getHotelId());
                getRoomById(response.getRoomId());

                bookedResponse = response;
                displayDataResponse(response);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "Error: " + t);
            }
        });
    }

    private void displayDataResponse(BookedResponse bookedResponses) {
        if(bookedResponses.getBookingStatus().equals("Chưa thanh toán"))
            btnPayment.setVisibility(View.VISIBLE);
        else
            btnPayment.setVisibility(View.GONE);
        txtGuestFullname.setText(bookedResponses.getGuestFullName());
        txtGuestEmail.setText(bookedResponses.getGuestEmail());
        txtAdults.setText(bookedResponses.getNumOfAdults() + "");
        txtChildren.setText(bookedResponses.getNumOfChildren() + "");
        txtCheckIn.setText(bookedResponses.getCheckInDate());
        txtCheckOut.setText(bookedResponses.getCheckOutDate());
        txtTotalPrice.setText(Common.convertCurrencyVietnamese(bookedResponses.getTotalPrice()) + " VNĐ");
        txtNote.setText("Lưu ý tới khách sạn: " + bookedResponses.getNote());
        txtInfomationCode.setText("Mã đặt phòng: " + bookedResponses.getBookingConfirmationCode());
        txtBookingStatus.setText("Trạng thái: " + bookedResponses.getBookingStatus());
    }

    private void getHotelDetails(Long id){
        CallHotelDetails.get(id, new iHotelDetails() {
            @Override
            public void onSuccess(HotelDetails hotelDetails) {
                txtHotelName.setText(hotelDetails.getName());
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
                Bitmap bitmap = Common.decodeBase64ToBitmap(room.getPhoto());
                imgRoom.setImageBitmap(bitmap);
                txtRoomType.setText(room.getRoomType());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "error: " + t);
            }
        });
    }

    private void cancelBooking(){
        CallCancelBooking.cancel(bookedResponse.getBookingId(), new iCancelBooking() {
            @Override
            public void onSuccess(MessageBooking messageBooking) {
                if(messageBooking!= null) {
                    txtContent.setText("Thông báo hủy đơn");
                    txtMessage.setText(messageBooking.getMessage());
                    progressBar.setVisibility(View.GONE);
                    txtMessage.setVisibility(View.VISIBLE);
                    //onStart();
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.d("AAA", "Error " + t);
            }
        });
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        openDialogCancelBooking();
                    }
                })
                .setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openDialogCancelBooking(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_booking);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        txtContent = dialog.findViewById(R.id.textContent);
        txtMessage = dialog.findViewById(R.id.textMessage);
        progressBar = dialog.findViewById(R.id.progressBar);
        txtMessage.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        cancelBooking();


        dialog.show();
    }
}