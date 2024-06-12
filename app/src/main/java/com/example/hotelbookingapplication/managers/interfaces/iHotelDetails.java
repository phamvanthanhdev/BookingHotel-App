package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.HotelDetails;

public interface iHotelDetails {
    void onSuccess(HotelDetails hotelDetails);
    void onError(Throwable t);
}
