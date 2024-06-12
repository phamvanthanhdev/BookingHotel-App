package com.example.hotelbookingapplication.untils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.DatePicker;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {
    public static final String convertCurrencyVietnamese(BigDecimal currency){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(currency);
    }

    //Chuyển hình ảnh về dạng Bitmap
    public static final Bitmap decodeBase64ToBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static void selectDate(TextView txt, Activity activity){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public static long caculateDistanceDay(String strCheckIn, String strCheckout) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCheckIn = null, dateCheckOut = null;
        try {
            dateCheckIn = simpleDateFormat.parse(strCheckIn);
            dateCheckOut = simpleDateFormat.parse(strCheckout);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateCheckIn);
        calendar2.setTime(dateCheckOut);

        long timeInMillis1 = calendar1.getTimeInMillis();
        long timeInMillis2 = calendar2.getTimeInMillis();

        long distanceDay = -1;
        if(timeInMillis2 - timeInMillis1 > 0) {
            long distanceMiliseconds = Math.abs(timeInMillis2 - timeInMillis1);
            distanceDay = distanceMiliseconds / (1000 * 60 * 60 * 24);
        }
        return distanceDay;
    }

    public static String getJwtToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwt", "").toString();
    }

    public static String getEmailShared(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "raju6@gmail.com").toString();
    }
}
