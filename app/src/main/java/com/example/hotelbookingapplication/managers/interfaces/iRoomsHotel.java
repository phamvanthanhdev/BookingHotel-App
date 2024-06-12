package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.Room;

import java.util.List;

public interface iRoomsHotel {
    void onSuccess(List<Room> rooms);
    void onError(Throwable t);
}
