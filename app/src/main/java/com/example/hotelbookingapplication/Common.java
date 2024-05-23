package com.example.hotelbookingapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
}
