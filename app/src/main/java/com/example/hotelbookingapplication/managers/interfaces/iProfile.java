package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.dto.UserResponse;
import com.example.hotelbookingapplication.model.BookedResponse;

import java.util.List;

public interface iProfile {
    void onSuccess(UserResponse userResponse);
    void onError(Throwable t);
}
