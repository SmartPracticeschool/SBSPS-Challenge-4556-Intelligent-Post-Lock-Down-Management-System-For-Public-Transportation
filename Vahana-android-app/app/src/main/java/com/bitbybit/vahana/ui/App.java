package com.bitbybit.vahana.ui;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bitbybit.vahana.gettersetter.HotspotGS;
import com.bitbybit.vahana.utils.Data;
import com.bitbybit.vahana.utils.Mysingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        fetch();
    }

    private void fetch() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Data.hotspot_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject parent = new JSONObject(response);
                    if (parent.getBoolean("success")) {
                        JSONArray jsonArray = new JSONArray(parent.getString("data"));
                        ArrayList<HotspotGS> hotspotGS = new ArrayList<>();
                        for (int c = 0; c < jsonArray.length(); c++) {
                            JSONObject jsonObject = new JSONObject(jsonArray.getString(c));
                            HotspotGS temp = new HotspotGS();
                            temp.setLat(jsonObject.getInt("lat"));
                            Log.d("ABHI", jsonObject.getInt("lat") + "");
                            temp.setLng(jsonObject.getInt("lng"));
                            temp.setRadius(jsonObject.getLong("radius"));
                            hotspotGS.add(temp);
                        }
                        Data.getInstance().setHotspotGS(hotspotGS);
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(App.this, "Please check your network connection...", Toast.LENGTH_SHORT).show();
            }
        });

        Mysingleton.getInstance(this.getApplicationContext()).addtorequestque(stringRequest, "hotspot");
    }
}
