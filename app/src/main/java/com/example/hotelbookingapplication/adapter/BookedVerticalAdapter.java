package com.example.hotelbookingapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapplication.BookedDetailsActivity;
import com.example.hotelbookingapplication.R;
import com.example.hotelbookingapplication.model.BookedResponse;
import com.example.hotelbookingapplication.untils.Common;

import java.util.List;

public class BookedVerticalAdapter extends RecyclerView.Adapter<BookedVerticalAdapter.RecentsViewHolder>{
    Context context;
    List<BookedResponse> items;

    public BookedVerticalAdapter(Context context, List<BookedResponse> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.order_booking, null);
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
        holder.txtCode.setText("Mã đặt phòng: " + items.get(position).getBookingConfirmationCode());
        holder.txtTotalGuest.setText("Tổng số người: " + items.get(position).getTotalNumOfGuest());
        holder.txtTotalPrice.setText(Common.convertCurrencyVietnamese(items.get(position).getTotalPrice()) + " VNĐ");
        holder.txtCheckIn.setText(items.get(position).getCheckInDate().toString());
        holder.txtCheckOut.setText(items.get(position).getCheckOutDate().toString());
        holder.txtBookingStatus.setText(items.get(position).getBookingStatus());
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookedDetailsActivity.class);
                intent.putExtra("bookedId", items.get(position).getBookingId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtCode, txtTotalGuest, txtTotalPrice, txtCheckIn, txtCheckOut, txtBookingStatus;
        Button btnDetails;
        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.textInfomationCode);
            txtTotalGuest = itemView.findViewById(R.id.textTotalNumOfGuest);
            txtTotalPrice = itemView.findViewById(R.id.textTotalPrice);
            txtCheckIn = itemView.findViewById(R.id.textCheckIn);
            txtCheckOut = itemView.findViewById(R.id.textCheckOut);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            txtBookingStatus = itemView.findViewById(R.id.textBookingStatus);
        }
    }
}