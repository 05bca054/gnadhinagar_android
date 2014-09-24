package com.nplussolutions.easygandhinagar;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EmergencyNumber extends Activity {
	Context ctx;
	
	Button btn_home,btn_quit,btn_footersearch;
	Button btn_exit_no_dialog;
	Button btn_exit_yes_dialog ;
	
	TextView txt_call_st_stand,txt_call_bus_depot,txt_call_railway,txt_call_railway_inquiry,
	txt_call_sabarmati,txt_call_airport,txt_call_torrent,txt_call_police1,txt_call_police2,txt_call_ambulance,txt_call_rto,
	txt_call_womenhelpline;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_number);
		
		ctx = this;
		
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		btn_footersearch = (Button) findViewById(R.id.btn_footersearch);
		btn_footersearch.setVisibility(View.INVISIBLE);
		
		txt_call_st_stand = (TextView) findViewById(R.id.txt_call_st_stand);
		txt_call_bus_depot = (TextView) findViewById(R.id.txt_call_bus_depot);
		txt_call_railway = (TextView) findViewById(R.id.txt_call_railway);
		txt_call_railway_inquiry = (TextView) findViewById(R.id.txt_call_railway_inquiry);
		txt_call_sabarmati = (TextView) findViewById(R.id.txt_call_sabarmati);
		txt_call_airport = (TextView) findViewById(R.id.txt_call_airport);
		txt_call_torrent = (TextView) findViewById(R.id.txt_call_torrent);
		txt_call_police1 = (TextView) findViewById(R.id.txt_call_police1);
		txt_call_police2 = (TextView) findViewById(R.id.txt_call_police2);
		txt_call_ambulance = (TextView) findViewById(R.id.txt_call_ambulance);
		txt_call_rto = (TextView) findViewById(R.id.txt_call_rto);
		txt_call_womenhelpline = (TextView) findViewById(R.id.txt_call_womenhelpline);
		
		btn_home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, Home.class));
				overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
				finish();
			}
		});
		
		btn_quit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				exitApp();
			}
		});
		
		txt_call_st_stand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_st_stand.getText().toString());				
			}
		});
		
		txt_call_bus_depot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_bus_depot.getText().toString());				
			}
		});
		
		txt_call_railway.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_railway.getText().toString());				
			}
		});
		
		txt_call_railway_inquiry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_railway_inquiry.getText().toString());				
			}
		});
		
		txt_call_sabarmati.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_sabarmati.getText().toString());				
			}
		});
		
		txt_call_airport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_airport.getText().toString());				
			}
		});
		
		txt_call_torrent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_torrent.getText().toString());				
			}
		});
		
		txt_call_police1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_police1.getText().toString());				
			}
		});
		
		txt_call_police2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_police2.getText().toString());				
			}
		});
		
		txt_call_ambulance.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_ambulance.getText().toString());				
			}
		});
		
		txt_call_rto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_rto.getText().toString());				
			}
		});
		
		txt_call_womenhelpline.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getCallUser(txt_call_womenhelpline.getText().toString());				
			}
		});
	}
	//get Phone Call
	public void getCallUser(String call_number){
		String number = call_number.replace(" ", "");
		
		try {
	        Intent callIntent = new Intent(Intent.ACTION_CALL);
	        callIntent.setData(Uri.parse("tel:"+number));
	        startActivity(callIntent);
	    } catch (ActivityNotFoundException e) {
	        Log.e("helloandroid dialing example", "Call failed", e);
	    }
	}
	
	//Exit App
	public void exitApp(){
		final Dialog exitapp = new Dialog(EmergencyNumber.this);
		
		exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		exitapp.setContentView(R.layout.popup_logout);
		exitapp.setCancelable(false);
		btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
		btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
		btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				stopService(new Intent(EmergencyNumber.this, LocationService.class));
				EmergencyNumber.this.finish();
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
		startActivity(new Intent(ctx, Home.class));
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		finish();
		
	}
	
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
			final Dialog popup = new Dialog(EmergencyNumber.this);
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
			f.feedback(ctx, EmergencyNumber.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, EmergencyNumber.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
