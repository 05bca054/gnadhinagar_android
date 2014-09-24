package com.nplussolutions.easygandhinagar;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.Utility;

@SuppressLint("NewApi")
public class Navigation extends Activity {
	Context ctx;
	
	ArrayAdapter<String> adp_subsecter; 
	
	LinearLayout ll_navigation;
	Spinner spn_navi_sector,spn_navi_subsector;
	String sectorid,subsectorid;
	Button btn_home,btn_quit,btn_footersearch;
	List<String> sector_arr;
	List<String> subsector_arr;
	String[] searchploteno_arr;
	AutoCompleteTextView txt_searchploteno;
	LocationManager locationManager;
	
	Handler handler_mapupdate = new Handler();
	Handler handler_map = new Handler();
	Handler handler_maplasttime = new Handler();
	
	Button btn_exit_no_dialog,btn_location_no_dialog;
	Button btn_exit_yes_dialog,btn_location_yes_dialog ;
	
	String[] s = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
	
	String[] sub_all = {"All"};
	
	String[] s_s = {"A", "B", "C", "D"};
	
	String[] sub_3 = {"A","A New","B","C","D"};
	
	String[] sub_10 = {"A","B","Vidhanshbha and New Sachivalya"};
	
	CustomProgressDialog pdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		
		ctx = this;
		
		spn_navi_sector = (Spinner) findViewById(R.id.spn_navi_sector);
		spn_navi_subsector = (Spinner) findViewById(R.id.spn_navi_subsector);
		txt_searchploteno = (AutoCompleteTextView) findViewById(R.id.txt_searchploteno);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		//btn_navigationmap = (Button) findViewById(R.id.btn_footersearch);
		btn_footersearch = (Button) findViewById(R.id.btn_footersearch);
		//btn_footersearch.setVisibility(View.GONE);
		
		txt_searchploteno.setOnEditorActionListener(new DoneOnEditorActionListener());
		
		ll_navigation = (LinearLayout) findViewById(R.id.ll_navigation);
		ll_navigation.setLayoutParams(new LinearLayout.LayoutParams((int)(Utility.getDeviceHeight(Navigation.this) / 2), LayoutParams.WRAP_CONTENT));
		
		//Select Sector
		ArrayAdapter<String> adp_secter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, s);
		adp_secter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
		spn_navi_sector.setAdapter(adp_secter);
		
		spn_navi_sector.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			public void onItemSelected(AdapterView<?> parent, View v, int pos,long arg3) 
			{
				sectorid = spn_navi_sector.getSelectedItem().toString();
				
				
				//Select SubSector
				
				if(sectorid == "1" || sectorid == "8" || sectorid == "9" || sectorid == "11" || sectorid == "12" || sectorid == "30"
						 || sectorid == "14" || sectorid == "15" || sectorid == "16" || sectorid == "17" || sectorid == "18" || sectorid == "19"
						 || sectorid == "20" || sectorid == "21" || sectorid == "22" || sectorid == "23" || sectorid == "24" || sectorid == "25"
						 || sectorid == "26" || sectorid == "27" || sectorid == "28" || sectorid == "29"){
					spn_navi_subsector.setEnabled(false);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, sub_all);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_navi_subsector.setAdapter(adp_subsecter);
					
					subsectorid  = spn_navi_subsector.getSelectedItem().toString();
					subsectorid = "All";
					spn_navi_subsector.setVisibility(View.INVISIBLE);
					
				}else if(sectorid == "2" || sectorid == "4" || sectorid == "5" || sectorid == "6"
						|| sectorid == "7" || sectorid == "13"){
					subsectorid  = spn_navi_subsector.getSelectedItem().toString();
					subsectorid = sectorid + subsectorid;
					
					spn_navi_subsector.setVisibility(View.VISIBLE);
					spn_navi_subsector.setEnabled(true);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, s_s);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_navi_subsector.setAdapter(adp_subsecter);
				}else if(sectorid == "3"){
					subsectorid  = spn_navi_subsector.getSelectedItem().toString();
					subsectorid = sectorid + subsectorid;
					
					spn_navi_subsector.setVisibility(View.VISIBLE);
					spn_navi_subsector.setEnabled(true);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, sub_3);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_navi_subsector.setAdapter(adp_subsecter);
				}else if(sectorid == "10"){
					subsectorid  = spn_navi_subsector.getSelectedItem().toString();
					subsectorid = sectorid + subsectorid;
					
					spn_navi_subsector.setVisibility(View.VISIBLE);
					spn_navi_subsector.setEnabled(true);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, sub_10);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_navi_subsector.setAdapter(adp_subsecter);
				}
			}

			@SuppressWarnings("unused")
			private String getText(String string) {
				return null;
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spn_navi_subsector.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			public void onItemSelected(AdapterView<?> parent, View v, int pos,long arg3) 
			{
				if(sectorid == "1" || sectorid == "8" || sectorid == "9" || sectorid == "11" || sectorid == "12" || sectorid == "30"
						 || sectorid == "14" || sectorid == "15" || sectorid == "16" || sectorid == "17" || sectorid == "18" || sectorid == "19"
						 || sectorid == "20" || sectorid == "21" || sectorid == "22" || sectorid == "23" || sectorid == "24" || sectorid == "25"
						 || sectorid == "26" || sectorid == "27" || sectorid == "28" || sectorid == "29"){
				
					subsectorid = "All";
				}else{
					subsectorid = sectorid + spn_navi_subsector.getSelectedItem().toString();
				}
				
				if(Utility.isInternetAvailable(ctx)){
					txt_searchploteno.setText("");
					new AsyncSearchPloatNo(sectorid, subsectorid).execute();
				}else{
					Utility.showDialog(Navigation.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
				}
			}

			@SuppressWarnings("unused")
			private String getText(String string) {
				return null;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		btn_footersearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx)){
					new AsyncSearchPloat(sectorid, subsectorid,txt_searchploteno.getText().toString()).execute();
				}else{
					Utility.showDialog(Navigation.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
				}
				
			}
		});
		
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
	}

	class DoneOnEditorActionListener implements OnEditorActionListener 
	{ 
		@Override 
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
		{
			if (actionId == EditorInfo.IME_ACTION_DONE) 
			{
				if(Utility.isInternetAvailable(ctx)){
					new AsyncSearchPloat(sectorid, subsectorid,txt_searchploteno.getText().toString()).execute();
				}else{
					Utility.showDialog(Navigation.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
				}
			}
			return false; 
		}
	}
	
	public static List<String> formatData(String data)
	{ 
		return Arrays.asList(data.substring(1, data.length() - 1).replaceAll("\"", "").split(",")); 
	}
	
	//Exit App
	public void exitApp(){
		final Dialog exitapp = new Dialog(Navigation.this);
		
		exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		exitapp.setContentView(R.layout.popup_logout);
		exitapp.setCancelable(false);
		btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
		btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
		btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				stopService(new Intent(Navigation.this, LocationService.class));
				Navigation.this.finish();
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
	
	// Ploat Number Thread
	public class AsyncSearchPloatNo extends AsyncTask<Object, Object, Object> {
						
		CustomProgressDialog dialog;
		Intent intent;
		String sectorname,subsectorname;
		HttpClient httpclient;
		HttpGet httpget;
		List<NameValuePair> nameValuePairs;
		HttpResponse response;
						
		public AsyncSearchPloatNo(String sectorname,String subsectorname) {
			this.sectorname = sectorname;
			this.subsectorname = subsectorname;
				
			dialog = new CustomProgressDialog(ctx, "Loading...");
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		public Object doInBackground(Object... params) {
			try {	
					
				httpclient = new DefaultHttpClient();
				
				String query="";
				query += (sectorname !=null) ? ("sect_id="+URLEncoder.encode(sectorname,"UTF-8")) : "";
				query += (subsectorname !=null) ? ("&sub_sect_id="+URLEncoder.encode(subsectorname,"UTF-8")) : "";
			     
				httpget = new HttpGet(Config.URL + "plot_new.php?"+query);
				
				//httpget = new HttpGet(Config.URL + "plot_new.php?sect_id="+ sectorname + "&sub_sect_id=" + subsectorname);
				response = httpclient.execute(httpget);
				
				return EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public void onPreExecute() {
			super.onPreExecute();
				dialog.show();
		}

		@Override
		public void onPostExecute(Object result) {
			super.onPostExecute(result);
							
			try {
				if(result != null) {
				
			        try {
			        	JSONArray mArray = new JSONArray(result.toString());
			        	searchploteno_arr = new String[mArray.length()];
			        	
			        	for (int i = 0; i < mArray.length(); i++) {
			        		JSONObject mJsonObject = mArray.getJSONObject(i);
			        		searchploteno_arr[i] = mJsonObject.getString("plot_no");
			        	}
			        } catch (JSONException e) {
			        	txt_searchploteno.setAdapter(null);
			            e.printStackTrace();
			        }
		 			
					ArrayAdapter<String> adp=new ArrayAdapter<String>(Navigation.this, android.R.layout.simple_selectable_list_item,searchploteno_arr);
					txt_searchploteno.setThreshold(1);
					txt_searchploteno.setAdapter(adp);
					txt_searchploteno.setTextColor(Color.BLACK);		
												
				} 
			}catch(Exception e) {
				txt_searchploteno.setAdapter(null);
				Log.w("Exception", e.toString());
			}
			dialog.dismiss();
		}

		@Override
		public void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
		}
	}
		
	// SearchPloat Async Thread
	public class AsyncSearchPloat extends AsyncTask<Object, Object, Object> {
					
		JSONObject jSon;
		CustomProgressDialog dialog;
		Intent intent;
		String sectorname;
		HttpClient httpclient;
		HttpGet httpget;
		String selcetsector,selcetsubsector,searchplotno;
		List<NameValuePair> nameValuePairs;
		HttpResponse response;
					
		public AsyncSearchPloat(String selcetsector, String selcetsubsector, String searchplotno) {
			this.selcetsector = selcetsector;
			this.selcetsubsector = selcetsubsector;
			this.searchplotno = searchplotno;
						
			dialog = new CustomProgressDialog(ctx, "Loading...");
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		public Object doInBackground(Object... params) {
			try {	
								
				httpclient = new DefaultHttpClient();
				String query="";
				query += (selcetsector !=null) ? ("sect_id="+URLEncoder.encode(selcetsector,"UTF-8")) : "";
				query += (selcetsubsector !=null) ? ("&sub_sect_id="+URLEncoder.encode(selcetsubsector,"UTF-8")) : "";
				query += (searchplotno !=null) ? ("&plot_no="+URLEncoder.encode(searchplotno,"UTF-8")) : "";
				
				httpget = new HttpGet(Config.URL + "residential.php?"+query);
				
				httpget = new HttpGet(Config.URL + "residential.php?sect_id=" + selcetsector + "&sub_sect_id=" + selcetsubsector + "&plot_no=" + searchplotno);
				response = httpclient.execute(httpget);
									
				return EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		public void onPostExecute(Object result) {
			super.onPostExecute(result);
			try {
				if(result != null) {
					jSon = new JSONObject(result.toString());
					Config.CURRENT_LATITUDE_TO = jSon.getString("lat").toString();
					Config.CURRENT_LONGITUDE_TO = jSon.getString("long").toString();
								
					pdialog = new CustomProgressDialog(Navigation.this,"Find Location...");
					pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					pdialog.setCancelable(false);
					
					if (Config.CURRENT_LATITUDE == 0.0 && Config.CURRENT_LONGITUDE == 0.0){
						
						pdialog.show();
						handler_mapupdate.postDelayed(m_Runnable_mapUpdate, 20000);
					}else{
						goMap();
					}
				} 
			}catch(Exception e) {}
			dialog.dismiss();
		}

		@Override
		public void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
		}
	}
	
	public Runnable m_Runnable_map = new Runnable(){
	    public void run() {
	    	if (Config.CURRENT_LATITUDE != 0.0 && Config.CURRENT_LONGITUDE != 0.0) {
	    		pdialog.dismiss();
	    		goMap();
				handler_map.removeCallbacks(this);
				handler_mapupdate.removeCallbacks(this);
				handler_maplasttime.removeCallbacks(this);
			} else {
				handler_map.postDelayed(this, 1000);
			}
	    }
	};
	
	public Runnable m_Runnable_maplasttime = new Runnable(){
	    public void run() {
	    	if (Config.CURRENT_LATITUDE != 0.0 && Config.CURRENT_LONGITUDE != 0.0) {
	    		pdialog.dismiss();
	    		goMap();
				handler_map.removeCallbacks(this);
				handler_mapupdate.removeCallbacks(this);
				handler_mapupdate.removeCallbacks(this);
			} else {
				pdialog.dismiss();
			
				handler_map.removeCallbacks(m_Runnable_map);
				handler_maplasttime.removeCallbacks(m_Runnable_maplasttime);
				handler_mapupdate.removeCallbacks(m_Runnable_mapUpdate);
				
				final Dialog popup = new Dialog(Navigation.this);
				popup.requestWindowFeature(Window.FEATURE_NO_TITLE);    
				popup.setContentView(R.layout.popup_location);
				popup.setCancelable(false);
				
				TextView txt_hearder_poplocation = (TextView) popup.findViewById(R.id.txt_hearder_poplocation);
				TextView txt_message_dialog = (TextView) popup.findViewById(R.id.txt_message_dialog);
				
				txt_hearder_poplocation.setText("GPS is not Supproted");
				
				txt_message_dialog.setText("Either GPS or A-GPS is not supported by your device.please check your device features."+"\n\n"
						+"Note :--> this application only run on device which supports GPS and A-GPS both the features together.");
				
				btn_location_no_dialog = (Button) popup.findViewById(R.id.btn_location_no_dialog);
				btn_location_yes_dialog = (Button) popup.findViewById(R.id.btn_location_yes_dialog);
				
				btn_location_yes_dialog.setText("OK");
				btn_location_no_dialog.setVisibility(View.GONE);
				
				btn_location_yes_dialog.setOnClickListener(new OnClickListener() 
				{
					public void onClick(View v) 
					{
						finish();
						popup.dismiss();
					}
				});
				
					   
				popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
				popup.show();
			}
	    }
	};
	
	public Runnable m_Runnable_mapUpdate = new Runnable(){
	    public void run() {
	    	if (Config.CURRENT_LATITUDE != 0.0 && Config.CURRENT_LONGITUDE != 0.0) {
	    		
	    		pdialog.dismiss();
	    		goMap();
				
			} else {
				
				final Dialog popup = new Dialog(Navigation.this);
				popup.requestWindowFeature(Window.FEATURE_NO_TITLE);    
				popup.setContentView(R.layout.popup_location);
				popup.setCancelable(false);
				
				TextView txt_hearder_poplocation = (TextView) popup.findViewById(R.id.txt_hearder_poplocation);
				TextView txt_message_dialog = (TextView) popup.findViewById(R.id.txt_message_dialog);
				
				txt_hearder_poplocation.setText("GPS Might not Supproted");
				
				txt_message_dialog.setText("Either GPS or A-GPS is not supported by your device.please check your device features."+"\n\n"
						+"Note :--> this application only run on device which supports GPS and A-GPS both the features together.");
				
				btn_location_no_dialog = (Button) popup.findViewById(R.id.btn_location_no_dialog);
				btn_location_yes_dialog = (Button) popup.findViewById(R.id.btn_location_yes_dialog);
				
				btn_location_yes_dialog.setOnClickListener(new OnClickListener() 
				{
					public void onClick(View v) 
					{
						handler_map.postDelayed(m_Runnable_map, 0);
						handler_map.postDelayed(m_Runnable_maplasttime, 60000);
						popup.dismiss();
					}
				});
				btn_location_no_dialog.setOnClickListener(new OnClickListener() 
				{
					public void onClick(View v) 
					{
						pdialog.dismiss();
						handler_mapupdate.removeCallbacks(m_Runnable_mapUpdate);
						stopService(new Intent(Navigation.this, LocationService.class));
						Navigation.this.finish();
						popup.dismiss();
					}
				});
					   
				popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
				popup.show();
				
				
			}
	    	
	    }
	};
	
	public void goMap(){
		try{
			if (Config.CURRENT_LATITUDE != 0.0 && Config.CURRENT_LONGITUDE != 0.0){
				
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
					    Uri.parse("http://maps.google.com/maps?saddr="+Config.CURRENT_LATITUDE +","+Config.CURRENT_LONGITUDE+"&daddr="+Config.CURRENT_LATITUDE_TO +","+Config.CURRENT_LONGITUDE_TO));
				startActivity(intent);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
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
			final Dialog popup = new Dialog(Navigation.this);
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
			f.feedback(ctx, Navigation.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, Navigation.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
