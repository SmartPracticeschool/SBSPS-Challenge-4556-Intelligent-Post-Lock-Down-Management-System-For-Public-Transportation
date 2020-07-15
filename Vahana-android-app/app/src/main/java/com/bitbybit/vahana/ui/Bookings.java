package com.bitbybit.vahana.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitbybit.vahana.R;
import com.bitbybit.vahana.adaptors.BookingsAdaptor;
import com.bitbybit.vahana.gettersetter.BookGS;
import com.bitbybit.vahana.gettersetter.BookingsGS;
import com.bitbybit.vahana.utils.Data;
import com.bitbybit.vahana.utils.Mysingleton;
import com.bitbybit.vahana.utils.Shared_prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Bookings extends AppCompatActivity implements BookingsAdaptor.BookingInterface{

    private RecyclerView bookings;
    private ProgressDialog progressDialog;
    public  ArrayList<BookingsGS> bookingsGS;
    private BookingsAdaptor bookingsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        getviews();
    }

    private void getviews() {
        bookings = findViewById(R.id.bookings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Booking Details");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        prepare_progressdialog();
        setrecycleview();
        closesoftkeyboard();
        bookings(Shared_prefs.getInstance(this).get_username());
    }

    public void prepare_progressdialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("please wait...");
    }

    private void closesoftkeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    private void setrecycleview() {
        bookingsGS = new ArrayList<>();
//        if(Data.getInstance().getBookGS() != null){
//            BookingsGS book = new BookingsGS();
//            book.setFrom(Data.getInstance().getFrom());
//            book.setTo(Data.getInstance().getTo());
//            book.setDate(Data.getInstance().getDate());
//            book.setSeats(Data.getInstance().getBookGS().size() + "");
//            bookingsGS.add(book);
//        }
//
//        BookingsGS book1 = new BookingsGS();
//        book1.setFrom(Data.getInstance().getFrom());
//        book1.setTo(Data.getInstance().getTo());
//        book1.setDate("01/08/2020");
//        book1.setSeats("03");
//        bookingsGS.add(book1);
//
//        BookingsGS book2 = new BookingsGS();
//        book2.setFrom(Data.getInstance().getFrom());
//        book2.setTo(Data.getInstance().getTo());
//        book2.setDate("04/09/2020");
//        book2.setSeats("01");
//        bookingsGS.add(book2);

        bookings.setLayoutManager(new LinearLayoutManager(this));
        bookings.setHasFixedSize(true);
        bookingsAdaptor = new BookingsAdaptor(bookingsGS, this);
        bookings.setAdapter(bookingsAdaptor);
    }

    @Override
    public void triggerQrClick() {
        BookingExpandedDialog bookingExpandedDialog = new BookingExpandedDialog();
        bookingExpandedDialog.show(getSupportFragmentManager(), "Booking");
    }

    private void bookings(final String email) {
        progressDialog.show();

        JSONObject data = new JSONObject();
        try {
            data.put("email", email);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Data.bookings_url,
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = (response);
                    if (jsonObject.getBoolean("success")) {
                        bookingsGS.clear();
                        JSONArray parent = new JSONArray(jsonObject.getString("status"));
                        for (int count = 0; count < parent.length(); count++) {
                            JSONObject child = parent.getJSONObject(count);
                            JSONObject vdetail = child.getJSONObject("v_detail");
                            BookingsGS book = new BookingsGS();
                            book.setFrom(vdetail.getString("arrival_time"));
                            book.setTo(vdetail.getString("departure_time"));
                            book.setDate(child.getString("date"));
                            book.setId(child.getInt("id"));
                            book.setUrl(child.getString("qr"));
                            JSONArray jsonArray = child.getJSONArray("p_detail");
                            book.setSeats(jsonArray.length() + "");
                            ArrayList<BookGS> temp = new ArrayList<>();
                            for (int c = 0; c < jsonArray.length(); c++) {
                                JSONObject child1 = jsonArray.getJSONObject(c);
                                BookGS bookGS = new BookGS();
                                bookGS.setName(child1.getString("name"));
                                bookGS.setSex(child1.getString("sex"));
                                bookGS.setAge(child1.getInt("age") + "");
                                temp.add(bookGS);
                            }
                            book.setDetailList(temp);
                            bookingsGS.add(book);
                        }
                        bookingsAdaptor.notifyDataSetChanged();

                    } else {
                        Toast.makeText(Bookings.this, "something is wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(Bookings.this, "Please check your network connection...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Mysingleton.getInstance(this.getApplicationContext()).addtorequestque(jsonObjectRequest, "bookings");
    }
}
