package com.bitbybit.vahana.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbybit.vahana.R;
import com.bitbybit.vahana.gettersetter.BookGS;
import com.bitbybit.vahana.gettersetter.BookingsGS;
import com.bitbybit.vahana.utils.Data;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BookingExpandedDialog extends AppCompatDialogFragment {

    private ImageView qr_dialog;
    private TextView bid_dialog, from, to, details1, details2, details3;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bookings_dialog, null);

        qr_dialog = view.findViewById(R.id.qr_dialog);
        bid_dialog = view.findViewById(R.id.bid_dialog);
        from = view.findViewById(R.id.from_dialogs);
        to = view.findViewById(R.id.to_dialogs);
        details1 = view.findViewById(R.id.details1_dialog);
        details2 = view.findViewById(R.id.details2_dialog);
        details3 = view.findViewById(R.id.details3_dialog);

        BookingsGS bookingsGS = Data.getInstance().getTempbookingsGS();
        from.setText(bookingsGS.getFrom());
        to.setText(bookingsGS.getTo());
        bid_dialog.setText("Booking ID - #" + bookingsGS.getId());
        Glide.with(this).load(bookingsGS.getUrl()).into(qr_dialog);

        int count = 0;
        if (bookingsGS.getDetailList() != null) {
            count = (bookingsGS.getDetailList().size() > 3) ? 2 : bookingsGS.getDetailList().size();
        } else {
            count = -1;
        }

        for (int i = 0; i < count; i++) {
            BookGS bookGS = bookingsGS.getDetailList().get(i);
            switch (i) {
                case 0:
                    details1.setText(bookGS.getName() + " " + bookGS.getAge() + " " + bookGS.getSex());
                    break;
                case 1:
                    details2.setText(bookGS.getName() + " " + bookGS.getAge() + " " + bookGS.getSex());
                    break;
                case 2:
                    details3.setText(bookGS.getName() + " " + bookGS.getAge() + " " + bookGS.getSex());
                    break;
                default:
                    details1.setText(bookGS.getName() + " " + bookGS.getAge() + " " + bookGS.getSex());
            }
        }


        builder.setView(view);

        return builder.create();
    }
}

