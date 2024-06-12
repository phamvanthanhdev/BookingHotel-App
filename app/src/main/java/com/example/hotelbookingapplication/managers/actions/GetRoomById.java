package com.example.hotelbookingapplication.managers.actions;

import com.example.hotelbookingapplication.managers.interfaces.iRoomById;
import com.example.hotelbookingapplication.model.Room;

public class GetRoomById {
    public static void getRoomById(Long id, iRoomById callback){
        GetRoomById.getRoomById(id, new iRoomById() {
            @Override
            public void onSuccess(Room room) {
                callback.onSuccess(room);
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }
}
