package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.MessageBooking;

public interface iBookingRoom {
    void onSuccess(MessageBooking message);
    void onError(Throwable t);
}
