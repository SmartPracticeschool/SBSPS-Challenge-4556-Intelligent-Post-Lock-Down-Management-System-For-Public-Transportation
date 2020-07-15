package com.bitbybit.vahana.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbybit.vahana.R;
import com.bitbybit.vahana.gettersetter.StationGS;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class StationAdaptor extends RecyclerView.Adapter<StationAdaptor.Viewholder> {

    private ArrayList<StationGS> arrayList;
    private Context context;
    private Viewholder viewholder;

    public StationAdaptor(ArrayList<StationGS> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_view, parent, false);
        Viewholder viewholder = new Viewholder(v);
        this.viewholder = viewholder;
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewHolder, final int position) {
        switch (arrayList.get(position).getFlag()){
            case "bus":
                viewHolder.icon.setImageResource(R.drawable.ic_directions_bus);
                break;
            case "train":
                viewHolder.icon.setImageResource(R.drawable.ic_train);
                break;
            case "flight":
                viewHolder.icon.setImageResource(R.drawable.ic_flight);
                break;
        }
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.distance.setText(arrayList.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name, distance;

        public Viewholder(View itemView){
            super(itemView);
            icon = itemView.findViewById(R.id.icon_station);
            name = itemView.findViewById(R.id.name_station);
            distance = itemView.findViewById(R.id.distance_station);
        }
    }
}

