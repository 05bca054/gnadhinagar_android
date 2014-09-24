package com.nplussolutions.easygandhinagar;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.Utility;

@SuppressLint("NewApi")
public class Home extends Activity  implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener,LocationListener{
	Context ctx;
	LinearLayout ll_home;
	Button btn_exit_no_dialog;
	Button btn_exit_yes_dialog ;
	LocationManager locationManager;
	Button btn_home_gov_offices, btn_home_public_fac_utili, btn_home_navigation, 
	btn_home_attraction, btn_home_circle, btn_home_EmergencyNumber;
	
	Intent mIntentService;
	PendingIntent mPendingIntent;
	LocationClient locationclient;
	LocationRequest locationrequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		ctx = this;
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			Config.isGps = true;
		else
			Config.isGps = false;

		if (!Config.isGps) {
			Utility.startGPS(ctx);
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
		Config.isStarted = false;
		
		
		mIntentService = new Intent(this, LocationService.class);
		mPendingIntent = PendingIntent.getService(this, 1, mIntentService, 0);
		
		int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if(resp == ConnectionResult.SUCCESS) {
			locationclient = new LocationClient(this, this, this);
			locationclient.connect();
		} else {
			Toast.makeText(this, "Google Play Service Error " + resp, Toast.LENGTH_LONG).show();
		}
		
		btn_home_navigation = (Button) findViewById(R.id.btn_home_navigation);
		btn_home_public_fac_utili = (Button) findViewById(R.id.btn_home_public_fac_utili);
		btn_home_gov_offices = (Button) findViewById(R.id.btn_home_gov_offices);
		btn_home_attraction  = (Button) findViewById(R.id.btn_home_attraction);
		btn_home_circle = (Button) findViewById(R.id.btn_home_circle);
		btn_home_EmergencyNumber = (Button) findViewById(R.id.btn_home_EmergencyNumber);
		
		
		ll_home = (LinearLayout) findViewById(R.id.ll_home);
		ll_home.setLayoutParams(new LinearLayout.LayoutParams((int) (Utility.getDeviceHeight(Home.this) / 2), LayoutParams.WRAP_CONTENT));
		
		btn_home_navigation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, Navigation.class));
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		
		btn_home_public_fac_utili.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, PublicFacilities.class));
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		
		btn_home_gov_offices.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, GovernmentOfficers.class));
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		
		btn_home_attraction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, GandhinagarAttraction.class));
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		
		btn_home_circle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, GandhinagarCircle.class));
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
		
		btn_home_EmergencyNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, EmergencyNumber.class));
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				finish();
			}
		});
	}
	//Exit App
	public void exitApp(){
		final Dialog exitapp = new Dialog(Home.this);
		
		exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		exitapp.setContentView(R.layout.popup_logout);
		exitapp.setCancelable(false);
		btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
		btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
		btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				stopService(new Intent(Home.this, LocationService.class));
				Home.this.finish();
				exitapp.dismiss();
			}
		});
		btn_exit_no_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				exitapp.dismiss();
			}
		});
			   
		exitapp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
		exitapp.show();
	}
		
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		exitApp();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {
		try {
			locationrequest = LocationRequest.create();
			locationrequest.setInterval(5000);
			locationclient.requestLocationUpdates(locationrequest, mPendingIntent);
		} catch (Exception e) {}
	}

	@Override
	public void onLocationChanged(Location arg0) {}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {}
	
	@Override
	public void onDisconnected() {}
	// Menu Option
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.id.menu_aboutus: // AboutUs
			final Dialog popup = new Dialog(Home.this);
			popup.requestWindowFeature(Window.FEATURE_NO_TITLE);    
			popup.setContentView(R.layout.popup_aboutus);
			popup.setCancelable(true);
			
			ImageView img_about_close = (ImageView) popup.findViewById(R.id.img_about_close);
			img_about_close.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					popup.dismiss();
				}
			});
			
			popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
			popup.show();
			
			return true;
			
		case R.id.menu_feedback: // Feedback
			Feedback f = new Feedback();
			f.feedback(ctx, Home.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, Home.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
