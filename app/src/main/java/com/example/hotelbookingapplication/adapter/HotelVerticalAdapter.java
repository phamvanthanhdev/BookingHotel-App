package com.example.hotelbookingapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapplication.HotelDetailActivity;
import com.example.hotelbookingapplication.R;
import com.example.hotelbookingapplication.model.Hotel;
import com.example.hotelbookingapplication.untils.Common;

import java.util.List;

public class HotelVerticalAdapter extends RecyclerView.Adapter<HotelVerticalAdapter.RecentsViewHolder>{
    Context context;
    List<Hotel> items;

    public HotelVerticalAdapter(Context context, List<Hotel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.hotel_vertical, null);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        layoutParams.setMargins(10, 0, 10, 30);
        view.setLayoutParams(layoutParams);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {
        holder.hotelName.setText(items.get(position).getName());
        holder.hotelPrice.setText(Common.convertCurrencyVietnamese(items.get(position).getPrice())+"đ/đêm");
        holder.hotelCity.setText(items.get(position).getCity());
        holder.hotelRating.setText(items.get(position).getRating()+"");
        holder.imgHotel.setImageBitmap(Common.decodeBase64ToBitmap(items.get(position).getPhoto()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("hotelId", items.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHotel;
        TextView hotelName, hotelCity, hotelRating, hotelPrice;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHotel = itemView.findViewById(R.id.imageHotel);
            hotelName = itemView.findViewById(R.id.textHotelName);
            hotelCity = itemView.findViewById(R.id.textHotelCity);
            hotelRating = itemView.findViewById(R.id.textHotelRating);
            hotelPrice = itemView.findViewById(R.id.textHotelPrice);
        }
    }
}