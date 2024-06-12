package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.BookedResponse;

import java.util.List;

public interface iBookedByEmail {
    void onSuccess(List<BookedResponse> bookedResponses);
    void onError(Throwable t);
}
