package com.bitbybit.vahana.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitbybit.vahana.R;
import com.bitbybit.vahana.adaptors.BookAdaptor;
import com.bitbybit.vahana.gettersetter.BookGS;
import com.bitbybit.vahana.utils.Data;
import com.bitbybit.vahana.utils.Mysingleton;
import com.bitbybit.vahana.utils.Shared_prefs;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Book extends AppCompatActivity {

    public ArrayList<BookGS> bookGS;
    private RecyclerView passengers;
    private MaterialButton confirm;
    private FloatingActionButton add;
    private ProgressDialog progressDialog;
    private BookAdaptor bookAdaptor;
    private int vid;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getviews();
    }

    private void getviews() {
        passengers = findViewById(R.id.passengers);
        confirm = findViewById(R.id.confirm);
        add = findViewById(R.id.add);

        vid = getIntent().getIntExtra("vid", -1);
        date = getIntent().getStringExtra("date");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Book");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookGS.add(new BookGS());
                bookAdaptor.notifyDataSetChanged();
            }
        });

        prepare_progressdialog();
        setrecycleview();
        closesoftkeyboard();
    }

    public void prepare_progressdialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("please wait...");
    }

    private void setrecycleview() {
        bookGS = new ArrayList<>();
        bookGS.add(new BookGS());
        passengers.setLayoutManager(new LinearLayoutManager(this));
        passengers.setHasFixedSize(true);
        bookAdaptor = new BookAdaptor(bookGS, this);
        passengers.setAdapter(bookAdaptor);
    }

    private void closesoftkeyboard() {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void book() {
        closesoftkeyboard();
        progressDialog.show();
        bookTicket();
    }

    private JSONObject buildData() {
        ArrayList<BookGS> temp = bookAdaptor.getAllData();
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        try {
            for (int count = 0; count < temp.size(); count++) {
                BookGS bookGS = temp.get(count);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", bookGS.getName());
                jsonObject.put("age", bookGS.getAge());
                jsonObject.put("sex", bookGS.getSex());
                jsonArray.put(jsonObject);
            }
            result.put("details", jsonArray);
            result.put("email", Shared_prefs.getInstance(this).get_username());
            result.put("vid", vid);
            result.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void bookTicket() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Data.book_url
                , buildData(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(Book.this, "Ticket Booked", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Book.this, HomePage.class));
                        finish();
                    } else {
                        Toast.makeText(Book.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(Book.this, "Please check your network connection...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        Mysingleton.getInstance(this.getApplicationContext()).addtorequestque(jsonObjectRequest, "book");
    }
}
