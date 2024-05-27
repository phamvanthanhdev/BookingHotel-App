package com.example.hotelbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hotelbookingapplication.api.ApiService;
import com.example.hotelbookingapplication.model.Hotel;
import com.example.hotelbookingapplication.thread.AllHotelsThread;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;
    private Handler mHandler1, mHandler2;
    private static final int MSG_UPDATE_NUMBER = 100;
    private static final int MSG_UPDATE_NUMBER_DONE = 101;
    private static final int MSG_UPDATE_NUMBER_1 = 102;
    private static final int MSG_UPDATE_NUMBER_DONE_1 = 103;

    private TextView mTextNumber;
    private TextView mTextNumber1;
    private boolean mIsCounting;
    private boolean mIsCounting1;
    private ProgressBar progressBar;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //abcd

        initViews();
        /*listenerHandler();
        listenerHandler1();*/
    }

    private void GetAllHotelsFromServer() {
        ApiService
                .apiService
                .getAllHotels()
                .enqueue(new Callback<List<Hotel>>() {
                    @Override
                    public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                        if(response.isSuccessful()){
                            List<Hotel> hotelList = response.body();
                            for (Hotel hotel:hotelList) {
                                Log.d("AAA", "Hotel : " + hotel.toString());
                            }
                            Bitmap bitmap = decodeBase64ToBitmap(hotelList.get(0).getPhoto());
                            imgView.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Hotel>> call, Throwable throwable) {
                        Log.d("AAA", "Get all hotels failed! " + throwable.getMessage());
                    }
                });
    }

    public Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void initViews() {
        imgView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        mTextNumber = findViewById(R.id.text_number);
        mTextNumber1 = findViewById(R.id.text_number1);
        findViewById(R.id.button_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsCounting) {
                    //GetAllHotelsFromServer();
                    //countNumbers();
                    //countNumbers1();
                    //countNumbers2();
                    mHandler = new Handler(Looper.getMainLooper());
                    new AllHotelsThread(mHandler, mTextNumber).start();
                }
            }
        });
    }

    private void countNumbers2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    /*Message message = new Message();
                    message.what = MSG_UPDATE_NUMBER;
                    message.arg1 = i;*/
                    //mHandler.sendMessage(message);
                    //progressBar.setIndeterminate(true);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler2 = new Handler(Looper.getMainLooper());
                mHandler2.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
                //mHandler.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE);
            }
        }).start();
    }

    private void countNumbers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    Message message = new Message();
                    message.what = MSG_UPDATE_NUMBER;
                    message.arg1 = i;
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE);
            }
        }).start();
    }

    private void countNumbers1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 20; i++) {
                    Message message = new Message();
                    message.what = MSG_UPDATE_NUMBER_1;
                    message.arg1 = i;
                    mHandler1.sendMessage(message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler1.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE_1);
                mHandler1.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextNumber1.setText("Done!Done");
                    }
                });
            }
        }).start();
    }

    /*private void countNumbersWithThread() {
        CountNumberThread countNumberThread = new CountNumberThread(mHandler);
        countNumberThread.start();
    }*/

    //UI Thread
    //Lắng nghe message trả về từ handler
    private void listenerHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_UPDATE_NUMBER:
                        mIsCounting = true;
                        mTextNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case MSG_UPDATE_NUMBER_DONE:
                        mTextNumber.setText("Done!");
                        mIsCounting = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void listenerHandler1() {
        mHandler1 = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_UPDATE_NUMBER_1:
                        //mIsCounting = true;
                        mTextNumber1.setText(String.valueOf(msg.arg1));
                        break;
                    case MSG_UPDATE_NUMBER_DONE_1:
                        mTextNumber1.setText("Done!");
                        //mIsCounting = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }

}