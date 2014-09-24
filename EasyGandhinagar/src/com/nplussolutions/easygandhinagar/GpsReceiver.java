package com.nplussolutions.easygandhinagar;

import com.nplussolutions.easygandhinagar.Utils.Config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

public class GpsReceiver extends BroadcastReceiver {
	
	@Override
    public void onReceive(Context context, Intent intent) {
		
		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	Config.isGps = true;
        } else {
        	Config.isGps = false;
        }
    }   
}