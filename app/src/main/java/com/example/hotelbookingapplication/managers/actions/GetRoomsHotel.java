package com.example.hotelbookingapplication.managers.actions;

import com.example.hotelbookingapplication.managers.interfaces.iRoomsHotel;
import com.example.hotelbookingapplication.model.Room;

import java.util.List;

public class GetRoomsHotel {
    public static void getRooms(Long hotelId, iRoomsHotel callback){
        GetRoomsHotel.getRooms(hotelId, new iRoomsHotel() {
            @Override
            public void onSuccess(List<Room> rooms) {
                callback.onSuccess(rooms);
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }
}
