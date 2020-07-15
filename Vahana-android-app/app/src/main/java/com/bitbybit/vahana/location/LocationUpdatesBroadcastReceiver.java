package com.bitbybit.vahana.location;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import java.util.List;

public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION_PROCESS_UPDATES =
            "com.google.android.gms.location.sample.locationupdatespendingintent.action" +
                    ".PROCESS_UPDATES";
    private static final String TAG = "LUBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    if (locations.size() <= 0) return;
                    Intent intent1 = new Intent().setAction(context.getPackageName() + "com.matrixfrats.location");
                    intent1.putExtra("lat", locations.get(0).getLatitude());
                    intent1.putExtra("long", locations.get(0).getLongitude());
                    context.sendBroadcast(intent1);
                    Log.d("ABHI", locations.size() + " location size " + locations.get(0).getLatitude());
                }
            }
        }
    }
}
