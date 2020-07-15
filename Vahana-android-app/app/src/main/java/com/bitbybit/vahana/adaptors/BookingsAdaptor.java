package com.bitbybit.vahana.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbybit.vahana.R;
import com.bitbybit.vahana.gettersetter.BookingsGS;
import com.bitbybit.vahana.utils.Data;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class BookingsAdaptor extends RecyclerView.Adapter<BookingsAdaptor.Viewholder> {

    private ArrayList<BookingsGS> arrayList;
    private Context context;
    private Viewholder viewholder;
    private BookingInterface bookingInterface;

    public interface BookingInterface{
        void triggerQrClick();
    }

    public BookingsAdaptor(ArrayList<BookingsGS> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
        this.bookingInterface = (BookingInterface) context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_view, parent, false);
        Viewholder viewholder = new Viewholder(v);
        this.viewholder = viewholder;
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewHolder, final int position) {
        viewHolder.from.setText(arrayList.get(position).getFrom());
        viewHolder.to.setText(arrayList.get(position).getTo());
        viewHolder.date.setText(arrayList.get(position).getDate());
        viewHolder.seats.setText(arrayList.get(position).getSeats());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        viewHolder.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.getInstance().setTempbookingsGS(arrayList.get(position));
                bookingInterface.triggerQrClick();
            }
        });
        Glide.with(context).load(arrayList.get(position).getUrl()).into(viewHolder.qrcode);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        ImageView qrcode, delete;
        TextView from, to, date, seats;

        public Viewholder(View itemView){
            super(itemView);
            from = itemView.findViewById(R.id.from_bookings);
            to = itemView.findViewById(R.id.to_bookings);
            date = itemView.findViewById(R.id.date_bookings);
            seats = itemView.findViewById(R.id.seat_bookings);
            qrcode = itemView.findViewById(R.id.qrcode);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
