package com.nplussolutions.easygandhinagar;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;

import com.google.android.gms.location.LocationClient;
import com.nplussolutions.easygandhinagar.Utils.Config;

public class LocationService extends IntentService {

	Handler handlerSendDeviceInfo = null;
	
	public LocationService() {
		super("EG");	
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		if(handlerSendDeviceInfo == null)
			handlerSendDeviceInfo = new Handler();
			
		Location location = intent.getParcelableExtra(LocationClient.KEY_LOCATION_CHANGED);
		
		Config.CURRENT_LATITUDE = location.getLatitude();
		Config.CURRENT_LONGITUDE = location.getLongitude();
		Config.isStarted = true;
	}
	
	public void onDestroy() {
		super.onDestroy();
		
		
	}
	

}