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

import com.example.hotelbookingapplication.BookingActivity;
import com.example.hotelbookingapplication.R;
import com.example.hotelbookingapplication.model.Room;
import com.example.hotelbookingapplication.untils.Common;

import java.util.List;

public class RoomVerticalAdapter extends RecyclerView.Adapter<RoomVerticalAdapter.RecentsViewHolder>{
    Context context;
    List<Room> items;
    Long houseId;

    public RoomVerticalAdapter(Context context, List<Room> items, Long houseId) {
        this.context = context;
        this.items = items;
        this.houseId = houseId;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.room_vertical, null);
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
        holder.txtRoomType.setText(items.get(position).getRoomType());
        holder.txtRoomPrice.setText(Common.convertCurrencyVietnamese(items.get(position).getRoomPrice())+"đ/đêm");
        holder.txtRoomStatus.setText(items.get(position).isBooked() ? "Hết phòng" : "Còn phòng");
        holder.imgRoom.setImageBitmap(Common.decodeBase64ToBitmap(items.get(position).getPhoto()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("roomId", items.get(position).getId());
                intent.putExtra("hotelId", houseId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoom;
        TextView txtRoomStatus, txtRoomType, txtRoomPrice;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imageRoom);
            txtRoomStatus = itemView.findViewById(R.id.textRoomStatus);
            txtRoomType = itemView.findViewById(R.id.textRoomType);
            txtRoomPrice = itemView.findViewById(R.id.textRoomPrice);
        }
    }
}