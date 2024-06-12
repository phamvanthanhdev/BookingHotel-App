package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.BookedResponse;

public interface iBookedById {
    void onSuccess(BookedResponse bookedResponses);
    void onError(Throwable t);
}
