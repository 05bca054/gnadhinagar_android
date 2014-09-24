package com.nplussolutions.easygandhinagar;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.Utility;

public class GandhinagarAttraction extends Activity {
	Context ctx;
	LinearLayout ll_navigation;
	Button btn_home,btn_quit,btn_footersearch;
	ListView lv_attraction;
	String[] id,name;
	ArrayList<PublicFacilitiesPOJO> list_service_arr;
	String sectorid,subsectorid;
	List<String> sector_arr;
	List<String> subsector_arr;
	Button btn_exit_no_dialog;
	Button btn_exit_yes_dialog ;
	String selected_service = "";
	String[] lat,longi,mapname,_ploatno;
	ArrayAdapter<String> adp_subsecter;
	TextView no_result_found;
	
	
	
	ArrayList<String> list = new ArrayList<String>();
	
	LinkedHashMap<String,String> services_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attraction);
		
		ctx = this;
		
		lv_attraction = (ListView) findViewById(R.id.lv_attraction);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		btn_footersearch = (Button) findViewById(R.id.btn_footersearch);
		
		no_result_found = (TextView) findViewById(R.id.txt_no_pub_found);
		
		ll_navigation = (LinearLayout) findViewById(R.id.ll_public_facilities);
		ll_navigation.setLayoutParams(new LinearLayout.LayoutParams((int)(Utility.getDeviceHeight(GandhinagarAttraction.this) / 2), LayoutParams.WRAP_CONTENT));

		list.add("");
		list.add("");
		list.add("Play ground(Garden)");
		list.add("Shopping / complex");
		list.add("Primary Secondary school");
		list.add("Temple/church/Mashed/Gurudware");
		list.add("Hotel/Restaurants");
		list.add("Petrol pump");
		list.add("Collages");
		list.add("");
		list.add("Hospital/Dispensary/HSC");
		list.add("Quaters/Guest House");
		list.add("Bus Station");
		list.add("Commercial");
		list.add("Police station/police chowky");
		list.add("Hostel");
		list.add("Community Hall");
		list.add("Post office");
		list.add("Bank");
		list.add("Cinema (Theartre)");
		list.add("Parking");
		list.add("Tennis court");
		list.add("Vegitable Market");
		list.add("Haveli");
		list.add("Balmandir");
		list.add("Library");
		list.add("Fire Station");
		list.add("University");
		list.add("Institutational (campus)");
		list.add("Vegitable Market");
		list.add("Dairy");
		list.add("Private company");
		list.add("Resort");
		list.add("Park");
		
		
		if(Utility.isInternetAvailable(ctx)){
			new AsyncAttraction().execute();
		}else{
			Utility.showDialog(GandhinagarAttraction.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
		}
		
		
		btn_footersearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selected_service = "";
				try{
					for(int i=0;i<list_service_arr.size();i++){
						
						PublicFacilitiesPOJO obj = list_service_arr.get(i);
						
						if(obj.isCheck()){
							selected_service += obj.getId()+",";
						}
						
							
					}
					if (selected_service.length() > 0 && selected_service.charAt(selected_service.length()-1)==',') {
						selected_service = selected_service.substring(0, selected_service.length()-1);
					}
					if(Utility.isInternetAvailable(ctx))
						new AsyncServicesSearrch(sectorid, subsectorid, selected_service).execute();
					else
						Utility.showDialog(GandhinagarAttraction.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
					
				}catch (Exception e) {
					
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

	//Exit App
	public void exitApp(){
		final Dialog exitapp = new Dialog(GandhinagarAttraction.this);
		
		exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		exitapp.setContentView(R.layout.popup_logout);
		exitapp.setCancelable(false);
		btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
		btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
		btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				stopService(new Intent(GandhinagarAttraction.this, LocationService.class));
				GandhinagarAttraction.this.finish();
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
			
	//ADD value List Of Service POJO
	public ArrayList<PublicFacilitiesPOJO> getAllName()
	{
		for(int i=0;i<id.length;i++){
			list_service_arr.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr;
	}
	
	public static List<String> formatData(String data)
	{ 
		return Arrays.asList(data.substring(1, data.length() - 1).replaceAll("\"", "").split(",")); 
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(ctx, Home.class));
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		finish();
		
	}

		
	
	// Service Async Thread
	public class AsyncAttraction extends AsyncTask<Object, Object, Object> {
		CustomProgressDialog dialog;
		HttpClient httpclient;
		HttpGet httpget;
		HttpResponse response;
							
		public AsyncAttraction() {
			
			dialog = new CustomProgressDialog(ctx, "Loading...");
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		public Object doInBackground(Object... params) {
			try {	
				//http://nplussolutions.com/Webservices_Gujgov/service_new.php?sect_id=5&sub_sect_id=All
				httpclient = new DefaultHttpClient();
				
				httpget = new HttpGet(Config.URL + "gandhinagar_attraction_list.php");
				
				//httpget = new HttpGet(Config.URL + "service_new.php?sect_id=" + sectorname +"&sub_sect_id="+ subsectorname);
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
					lv_attraction.setVisibility(View.VISIBLE);
					JSONArray service = new JSONArray(result.toString());
					
					id = new String[service.length()];
					name = new String[service.length()];
					
					for (int i=0; i<service.length(); i++) {
					    JSONObject items = service.getJSONObject(i);
					    
					    try{
			 				if(items.getString("gid").toString() != null || !items.getString("gid").toString().equals(""))
				 				id[i] = items.getString("gid");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
			 			
			 			try{
			 				if(items.getString("plot_no").toString() != null || !items.getString("plot_no").toString().equals(""))
			 					name[i] = items.getString("plot_no");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
					}					
					
					list_service_arr = new ArrayList<PublicFacilitiesPOJO>();
					
					PublicFacilitiesAdapter adp=new PublicFacilitiesAdapter(GandhinagarAttraction.this, getAllName());
					lv_attraction.setAdapter(adp);						
				} 
			}catch(Exception e) {
				Utility.showPopupDialog(GandhinagarAttraction.this,"Gandhinagar Attraction not availble.");
				
				lv_attraction.setAdapter(null);
			}
			dialog.dismiss();
		}

		@Override
		public void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
		}
	}
	// Search Data Async Thread
	public class AsyncServicesSearrch extends AsyncTask<Object, Object, Object> {
		String sectorname,subsectorname,selectedservice;
		CustomProgressDialog dialog;
		HttpClient httpclient;
		HttpGet httpget;
		HttpResponse response;
							
		public AsyncServicesSearrch(String sectorname,String subsectorname,String selectedservice) {
			this.sectorname = sectorname;
			this.subsectorname = subsectorname;
			this.selectedservice=selectedservice;
			
			dialog = new CustomProgressDialog(ctx, "Loading...");
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		public Object doInBackground(Object... params) {
			try {	
				//http://nplussolutions.com/Webservices_Gujgov/public_service.php?sect_id=5&sub_sect_id=5A&services=2,3,4,5,6,7
				httpclient = new DefaultHttpClient();
				
				String query="";
				query += (selectedservice !=null) ? ("offices="+URLEncoder.encode(selectedservice,"UTF-8")) : "";
				
				httpget = new HttpGet(Config.URL + "gandhinagar_attraction_select.php?"+query);
				
				//httpget = new HttpGet(Config.URL + "public_service.php?sect_id=" + sectorname +"&sub_sect_id="+subsectorname+"&services="+selectedservice);
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
					Log.w("respomse", result.toString());
					
					JSONArray service = new JSONArray(result.toString());
					
					lat = new String[service.length()];
					longi = new String[service.length()];
					_ploatno = new String[service.length()];
					mapname = new String[service.length()];
					
					for (int i=0; i<service.length(); i++) {
					    JSONObject items = service.getJSONObject(i);
					    
					    try{
			 				if(items.getString("lat").toString() != null || !items.getString("lat").toString().equals(""))
				 				lat[i] = items.getString("lat");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
			 			
			 			try{
			 				if(items.getString("long").toString() != null || !items.getString("long").toString().equals(""))
				 				longi[i] = items.getString("long");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
			 			
			 			try{
			 				if(items.getString("bui_id").toString() != null || !items.getString("bui_id").toString().equals(""))
				 				_ploatno[i] = items.getString("bui_id");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
			 			
			 			try{
			 				if(items.getString("plot_no").toString() != null || !items.getString("plot_no").toString().equals(""))
				 				mapname[i] = items.getString("plot_no");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
					}				
					
					if(mapname == null){
						
					}
					
					Config.googleMap = null;
					Intent i =new Intent(GandhinagarAttraction.this,AttractionMap.class);
					i.putExtra("lat", lat);
					i.putExtra("longi", longi);
					i.putExtra("plotno", _ploatno);
					i.putExtra("name", mapname);
					startActivity(i);
					finish();
				} 
			}catch(Exception e) {
			}
			dialog.dismiss();
		}

		@Override
		public void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
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
			final Dialog popup = new Dialog(GandhinagarAttraction.this);
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
			f.feedback(ctx, GandhinagarAttraction.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, GandhinagarAttraction.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
