package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.MessageBooking;

public interface iCancelBooking {
    void onSuccess(MessageBooking messageBooking);
    void onError(Throwable t);
}
