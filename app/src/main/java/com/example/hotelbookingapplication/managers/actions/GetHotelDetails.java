package com.example.hotelbookingapplication.managers.actions;

import com.example.hotelbookingapplication.managers.interfaces.iHotelDetails;
import com.example.hotelbookingapplication.model.HotelDetails;

public class GetHotelDetails {
    public static void getHotelDetails(Long id, iHotelDetails callback){
        GetHotelDetails.getHotelDetails(id, new iHotelDetails() {
            @Override
            public void onSuccess(HotelDetails hotelDetails) {
                callback.onSuccess(hotelDetails);
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }
}
