package com.bitbybit.vahana.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitbybit.vahana.R;
import com.bitbybit.vahana.gettersetter.BookGS;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class BookAdaptor extends RecyclerView.Adapter<BookAdaptor.Viewholder> {

    private ArrayList<BookGS> arrayList;
    private Context context;
    private Viewholder viewholder;

    public BookAdaptor(ArrayList<BookGS> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_layout, parent, false);
        Viewholder viewholder = new Viewholder(v);
        this.viewholder = viewholder;
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewHolder, final int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public ArrayList<BookGS> getAllData() {
        ArrayList<BookGS> marrayList = new ArrayList<>();
        for(int i=0; i < getItemCount(); i++){
            BookGS bookGS = new BookGS();
            bookGS.setName(viewholder.name.getText().toString().trim());
            bookGS.setAge(viewholder.age.getText().toString().trim());
            bookGS.setSex(viewholder.sex.getText().toString().trim());
            marrayList.add(bookGS);
        }
        return marrayList;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextInputEditText name, age, sex;

        public Viewholder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.passengername);
            age = itemView.findViewById(R.id.passengerage);
            sex = itemView.findViewById(R.id.passengersex);
        }
    }
}
