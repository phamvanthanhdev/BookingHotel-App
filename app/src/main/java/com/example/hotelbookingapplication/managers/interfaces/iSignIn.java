package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.dto.AuthResponse;
import com.example.hotelbookingapplication.model.Room;

import java.util.List;

public interface iSignIn {
    void onSuccess(AuthResponse authResponse);
    void onError(Throwable t);
}
