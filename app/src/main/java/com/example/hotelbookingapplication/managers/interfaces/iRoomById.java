package com.example.hotelbookingapplication.managers.interfaces;

import com.example.hotelbookingapplication.model.Room;

public interface iRoomById {
    void onSuccess(Room room);
    void onError(Throwable t);
}
