package com.bitbybit.vahana.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitbybit.vahana.R;
import com.bitbybit.vahana.adaptors.SearchAdaptor;
import com.bitbybit.vahana.gettersetter.SearchGS;
import com.bitbybit.vahana.utils.Data;
import com.bitbybit.vahana.utils.Mysingleton;
import com.google.android.material.button.MaterialButton;

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

public class Search  extends AppCompatActivity {

    private TextView text_change;
    private EditText from, to, date;
    private MaterialButton search;
    private RecyclerView searchresults;
    private ProgressDialog progressDialog;
    private String flag;
    public  ArrayList<SearchGS> searchGS;
    private SearchAdaptor searchAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getviews();
    }

    private void getviews() {
        text_change = findViewById(R.id.text_search);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        date = findViewById(R.id.date);
        search = findViewById(R.id.findvahana);
        searchresults = findViewById(R.id.searchresults);

        flag = getIntent().getStringExtra("flag");
        text_change.setText("Vahana-" + flag);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAdaptor.setDate(date.getText().toString().trim());
                fetch();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Search");
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
    }

    public void prepare_progressdialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("please wait...");
    }


    private void setrecycleview() {
        searchGS = new ArrayList<>();
        searchresults.setLayoutManager(new LinearLayoutManager(this));
        searchresults.setHasFixedSize(true);
        searchAdaptor = new SearchAdaptor(searchGS, this);
        searchresults.setAdapter(searchAdaptor);
    }

    private void closesoftkeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    private void fetch(){
        closesoftkeyboard();
//        switch (flag){
//            case "bus":
//                searchGS.add(new SearchGS(flag, "Partridge Family Bus", "12:10 - 12:50", "S-26"));
//                searchGS.add(new SearchGS(flag, "Nelson's Tour Bus", "10:10 - 11:30", "S-32"));
//                searchGS.add(new SearchGS(flag, "Ken Kesey's Bus", "1:10 - 2:20", "S-22"));
//                searchGS.add(new SearchGS(flag, "Magic School Bus", "12:00 - 12:10", "S-60"));
//                searchGS.add(new SearchGS(flag, "Freedom Riders' Bus", "12:10 - 12:50", "S-15"));
//                searchGS.add(new SearchGS(flag, "Campaign Bus", "1:30 - 12:50", "S-26"));
//                break;
//            case "train":
//                searchGS.add(new SearchGS(flag, "Rajdhani Express", "12:10 - 12:50", "S-26"));
//                searchGS.add(new SearchGS(flag, "Duronto Express", "10:10 - 11:30", "S-32"));
//                searchGS.add(new SearchGS(flag, "Shatabdi Express", "1:10 - 2:20", "S-22"));
//                searchGS.add(new SearchGS(flag, "Jan Shatabdi Express", "12:00 - 12:10", "S-60"));
//                searchGS.add(new SearchGS(flag, "Garib Rath Express", "12:10 - 12:50", "S-15"));
//                searchGS.add(new SearchGS(flag, "Humsafar Express", "1:30 - 12:50", "S-26"));
//                break;
//            case "flight":
//                searchGS.add(new SearchGS(flag, "IndiGo", "12:10 - 12:50", "S-26"));
//                searchGS.add(new SearchGS(flag, "GoAir", "10:10 - 11:30", "S-32"));
//                searchGS.add(new SearchGS(flag, "Air India", "1:10 - 2:20", "S-22"));
//                searchGS.add(new SearchGS(flag, "SpiceJet", "12:00 - 12:10", "S-60"));
//                searchGS.add(new SearchGS(flag, "Air Asia", "12:10 - 12:50", "S-15"));
//                searchGS.add(new SearchGS(flag, "IndiGo", "1:30 - 12:50", "S-26"));
//                break;
//        }
        if(!TextUtils.isEmpty(from.getText().toString().trim()) &&
                !TextUtils.isEmpty(to.getText().toString().trim()) &&
                !TextUtils.isEmpty(date.getText().toString().trim())){
            progressDialog.show();
            search(from.getText().toString().trim(), to.getText().toString().trim(), date.getText().toString().trim());
        } else {
            Toast.makeText(Search.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void search(final String from, final String to, final String date) {

        JSONObject data = new JSONObject();
        try {
            data.put("source", from);
            data.put("destination", to);
            data.put("date", date);
            data.put("type", flag);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Data.search_url,
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = (response);
                    if (jsonObject.getBoolean("success")) {
                        progressDialog.cancel();
                        String data = jsonObject.getString("data");
                        if (data.equals("Not Found")) {
                            searchGS.clear();
                            Toast.makeText(Search.this, "No results", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONArray jsonArray = new JSONArray(data);
                            int count = 0;
                            searchGS.clear();
                            while (count < jsonArray.length()) {
                                JSONObject temp = jsonArray.getJSONObject(count);
                                SearchGS in = new SearchGS();
                                in.setName(temp.getString("vehicle_name"));
                                in.setVid(temp.getInt("vehicle_id"));
                                in.setArrival_time(temp.getString("arrival_time"));
                                in.setDeparture_time(temp.getString("departure_time"));
                                in.setSource(temp.getString("source"));
                                in.setDestination(temp.getString("destination"));
                                in.setFlag(temp.getString("type"));
                                in.setSeats(temp.getInt("occupancy"));
                                searchGS.add(in);
                                count++;
                            }
                        }
                        searchAdaptor.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Search.this, "something is not right", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.cancel();

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(Search.this, "Please check your network connection...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        Mysingleton.getInstance(this.getApplicationContext()).addtorequestque(jsonObjectRequest, "search");
    }
}

