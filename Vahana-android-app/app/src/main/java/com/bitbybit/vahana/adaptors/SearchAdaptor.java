package com.bitbybit.vahana.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbybit.vahana.R;
import com.bitbybit.vahana.gettersetter.SearchGS;
import com.bitbybit.vahana.ui.Book;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdaptor extends RecyclerView.Adapter<SearchAdaptor.Viewholder> {

    private ArrayList<SearchGS> arrayList;
    private Context context;
    private String date;

    public SearchAdaptor(ArrayList<SearchGS> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view, parent, false);
        Viewholder viewholder = new Viewholder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewHolder, final int position) {
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.seat.setText("S-" + arrayList.get(position).getSeats());
        viewHolder.time.setText(arrayList.get(position).getArrival_time() + " - " +
                arrayList.get(position).getDeparture_time());
        switch (arrayList.get(position).getFlag()){
            case "bus":
                viewHolder.flag.setImageResource(R.drawable.ic_directions_bus);
                break;
            case "train":
                viewHolder.flag.setImageResource(R.drawable.ic_train);
                break;
            case "flight":
                viewHolder.flag.setImageResource(R.drawable.ic_flight);
                break;
        }
        viewHolder.searchcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Book.class);
                intent.putExtra("vid", arrayList.get(position).getVid());
                intent.putExtra("date", date);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextView name, seat, time;
        ImageView flag;
        CardView searchcard;

        public Viewholder(View itemView){
            super(itemView);
            searchcard = itemView.findViewById(R.id.bookingsViewCard);
            flag = itemView.findViewById(R.id.icon_search);
            name = itemView.findViewById(R.id.name_search);
            seat = itemView.findViewById(R.id.seats_search);
            time = itemView.findViewById(R.id.time_search);
        }
    }
}
