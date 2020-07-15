package com.bitbybit.vahana.ui;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.bitbybit.vahana.adaptors.StationAdaptor;
import com.bitbybit.vahana.gettersetter.StationGS;
import com.bitbybit.vahana.utils.CustomEditText;
import com.bitbybit.vahana.utils.Data;
import com.bitbybit.vahana.utils.Mysingleton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Stations extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private CustomEditText search;
    private BottomSheetBehavior behavior;
    private RecyclerView stations;
    private StationAdaptor stationAdaptor;
    public ArrayList<StationGS> stationGS;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        getviews();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        closesoftkeyboard();
        prepare_progressdialog();
    }

    private void closesoftkeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    private void setrecycleview() {
        stationGS = new ArrayList<>();
        stations.setLayoutManager(new LinearLayoutManager(this));
        stations.setHasFixedSize(true);
        stationAdaptor = new StationAdaptor(stationGS, this);
        stations.setAdapter(stationAdaptor);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
        LatLng address = new LatLng(82, 78);
        mMap.addMarker(new MarkerOptions().position(address).title("Delivery address")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 15));
    }

    private void getviews() {
        stations = findViewById(R.id.station_search);
        search = findViewById(R.id.search);
        search.setDrawableClickListener(new CustomEditText.DrawableClickListener() {
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        closesoftkeyboard();
                        progressDialog.show();
                        fetch();
                        break;
                    default:
                        break;
                }
            }

        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Vahana Stations");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        View bottomSheet = findViewById(R.id.bottomsheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        setrecycleview();
    }

    public void prepare_progressdialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("please wait...");
    }

    private void fetch(){
        fetch_stations(search.getText().toString().trim());
    }

    private void fetch_stations(final String query) {
        JSONObject data = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Data.station_url + query,
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response;
                    if (jsonObject.getBoolean("success")) {
                        progressDialog.cancel();
                        String data = jsonObject.getString("data");
                        if (data.equals("Not Found")) {
                            stationGS.clear();
                            Toast.makeText(Stations.this, "No results", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONArray jsonArray = new JSONArray(data);
                            int count = 0;
                            stationGS.clear();
                            while (count < jsonArray.length()) {
                                JSONObject temp = jsonArray.getJSONObject(count);
                                stationGS.add(new StationGS(temp.getString("type"),
                                        temp.getString("name"), "12 KM"));
                                count++;
                            }
                        }
                        stationAdaptor.notifyDataSetChanged();
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        Toast.makeText(Stations.this, "something is not right", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Stations.this, "Please check your network connection...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        Mysingleton.getInstance(this.getApplicationContext()).addtorequestque(jsonObjectRequest, "stations");
    }
}
