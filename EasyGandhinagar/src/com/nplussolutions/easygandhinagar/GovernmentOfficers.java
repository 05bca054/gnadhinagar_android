package com.nplussolutions.easygandhinagar;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.Utility;

public class GovernmentOfficers extends Activity {
	Context ctx;
	LinearLayout ll_offices;
	Spinner spn_office_sector,spn_office_subsector;
	Button btn_home,btn_submitmap,btn_quit,btn_footersearch;
	ListView lv_office_facilities;
	ArrayList<PublicFacilitiesPOJO> list_office_arr;
	String sectorid,subsectorid;
	List<String> sector_arr;
	List<String> subsector_arr;
	Button btn_exit_no_dialog;
	Button btn_exit_yes_dialog ;
	String[] id,name;
	String[] lat,longi,mapname;
	String selected_service = "";
	ArrayAdapter<String> adp_subsecter; 
	
	String[] s = {"All","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
	
	String[] sub_all = {"All"};
	
	String[] s_s = {"A", "B", "C", "D","All"};
	
	String[] sub_3 = {"A","A New","B","C","D","All"};
	
	String[] sub_10 = {"A","B","Vidhanshbha and New Sachivalya","All"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.government_officers);
		
		ctx = this;
		
		spn_office_sector = (Spinner) findViewById(R.id.spn_officers_sector);
		spn_office_subsector = (Spinner) findViewById(R.id.spn_officers_subsector);
		lv_office_facilities = (ListView) findViewById(R.id.lv_officers);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		btn_footersearch = (Button) findViewById(R.id.btn_footersearch);
		//btn_footersearch.setVisibility(View.INVISIBLE);
		
		ll_offices = (LinearLayout) findViewById(R.id.ll_offices);
		ll_offices.setLayoutParams(new LinearLayout.LayoutParams((int)(Utility.getDeviceHeight(GovernmentOfficers.this) / 2), LayoutParams.WRAP_CONTENT));
			
		//Select Sector
		ArrayAdapter<String> adp_sector = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, s);
		adp_sector.setDropDownViewResource(android.R.layout.simple_list_item_checked);
		spn_office_sector.setAdapter(adp_sector);	
		
		spn_office_sector.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			public void onItemSelected(AdapterView<?> parent, View v, int pos,long arg3) 
			{
				sectorid = spn_office_sector.getSelectedItem().toString();
				
				//Select SubSector
				
				if(sectorid == "1" || sectorid == "8" || sectorid == "9" || sectorid == "11" || sectorid == "12" || sectorid == "30"
						 || sectorid == "14" || sectorid == "15" || sectorid == "16" || sectorid == "17" || sectorid == "18" || sectorid == "19"
						 || sectorid == "20" || sectorid == "21" || sectorid == "22" || sectorid == "23" || sectorid == "24" || sectorid == "25"
						 || sectorid == "26" || sectorid == "27" || sectorid == "28" || sectorid == "29" || sectorid == "All"){
					spn_office_subsector.setEnabled(false);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, sub_all);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_office_subsector.setAdapter(adp_subsecter);
					
					subsectorid  = spn_office_subsector.getSelectedItem().toString();
					subsectorid = "All";
					spn_office_subsector.setVisibility(View.INVISIBLE);
					
				}else if(sectorid == "2" || sectorid == "4" || sectorid == "5" || sectorid == "6"
						|| sectorid == "7" || sectorid == "13"){
					spn_office_subsector.setEnabled(true);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, s_s);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_office_subsector.setAdapter(adp_subsecter);
					
					spn_office_subsector.setVisibility(View.VISIBLE);
					subsectorid  = spn_office_subsector.getSelectedItem().toString();
					subsectorid = sectorid + subsectorid;
				}else if(sectorid == "3"){
					spn_office_subsector.setEnabled(true);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, sub_3);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_office_subsector.setAdapter(adp_subsecter);
					
					spn_office_subsector.setVisibility(View.VISIBLE);
					subsectorid  = spn_office_subsector.getSelectedItem().toString();
					subsectorid = sectorid + subsectorid;
				}else if(sectorid == "10"){
					spn_office_subsector.setVisibility(View.VISIBLE);
					spn_office_subsector.setEnabled(true);
					adp_subsecter = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_item, sub_10);
					adp_subsecter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
					spn_office_subsector.setAdapter(adp_subsecter);
				
					spn_office_subsector.setVisibility(View.VISIBLE);
					subsectorid  = spn_office_subsector.getSelectedItem().toString();
					subsectorid = sectorid + subsectorid;
				}
			}

			@SuppressWarnings("unused")
			private String getText(String string) {
				return null;
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spn_office_subsector.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			public void onItemSelected(AdapterView<?> parent, View v, int pos,long arg3) 
			{
				if(sectorid == "1" || sectorid == "8" || sectorid == "9" || sectorid == "11" || sectorid == "12" || sectorid == "30"
						 || sectorid == "14" || sectorid == "15" || sectorid == "16" || sectorid == "17" || sectorid == "18" || sectorid == "19"
						 || sectorid == "20" || sectorid == "21" || sectorid == "22" || sectorid == "23" || sectorid == "24" || sectorid == "25"
						 || sectorid == "26" || sectorid == "27" || sectorid == "28" || sectorid == "29" || sectorid == "All"){
				
					subsectorid = "All";
				}else{
					subsectorid = sectorid + spn_office_subsector.getSelectedItem().toString();
				}
				
				try{
					String substr = subsectorid.substring(subsectorid.length() - 3);
					if(substr.equalsIgnoreCase("All")){
						subsectorid = "All";
					}
				}catch (Exception e) {}
				
				try{
					String substr = subsectorid.substring(subsectorid.length() - 30);
					if(substr.equals("Vidhanshbha and New Sachivalya")){
						//subsectorid = "123";
						subsectorid = String.valueOf("Vidhanshbha and New Sachivalya");
					}
				}catch (Exception e) {}
				
				if(Utility.isInternetAvailable(ctx)){
					new Asyncoffice(sectorid, subsectorid).execute();
				}else{
					Utility.showDialog(GovernmentOfficers.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
				}
				
			}

			@SuppressWarnings("unused")
			private String getText(String string) {
				return null;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
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
		
		btn_footersearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selected_service = "";
				try{
					for(int i=0;i<list_office_arr.size();i++){
						
						PublicFacilitiesPOJO obj = list_office_arr.get(i);
						
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
						Utility.showDialog(GovernmentOfficers.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
					
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	//Exit App
		public void exitApp(){
			final Dialog exitapp = new Dialog(GovernmentOfficers.this);
			
			exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
			exitapp.setContentView(R.layout.popup_logout);
			exitapp.setCancelable(false);
			btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
			btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
			btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
			{
				public void onClick(View v) 
				{
					stopService(new Intent(GovernmentOfficers.this, LocationService.class));
					GovernmentOfficers.this.finish();
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
			for(int i=0;i<name.length;i++){
				list_office_arr.add(new PublicFacilitiesPOJO(name[i],id[i],false));
			}
			return list_office_arr;
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
		// office Async Thread
		public class Asyncoffice extends AsyncTask<Object, Object, Object> {
			String sectorname,subsectorname;
			CustomProgressDialog dialog;
			HttpClient httpclient;
			HttpGet httpget;
			HttpResponse response;
								
			public Asyncoffice(String sectorname,String subsectorname) {
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
					//http://nplussolutions.com/Webservices_Gujgov/office.php?sect_id=5&sub_sect_id=5A
					httpclient = new DefaultHttpClient();
					
					String query="";
					query += (sectorname !=null) ? ("sect_id="+URLEncoder.encode(sectorname,"UTF-8")) : "";
					query += (subsectorname !=null) ? ("&sub_sect_id="+URLEncoder.encode(subsectorname,"UTF-8")) : "";
				     
					httpget = new HttpGet(Config.URL + "office.php?"+query);
					
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
						
						list_office_arr = new ArrayList<PublicFacilitiesPOJO>();
						
						PublicFacilitiesAdapter adp=new PublicFacilitiesAdapter(GovernmentOfficers.this, getAllName());
						lv_office_facilities.setAdapter(adp);						
					} 
				}catch(Exception e) {
					
					Utility.showPopupDialog(GovernmentOfficers.this,"Offices Services not availble.");
					lv_office_facilities.setAdapter(null);
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
			String _service = "9";
								
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
					query += (sectorname !=null) ? ("sect_id="+URLEncoder.encode(sectorname,"UTF-8")) : "";
					query += (subsectorname !=null) ? ("&sub_sect_id="+URLEncoder.encode(subsectorname,"UTF-8")) : "";
					query += (_service !=null) ? ("&services="+URLEncoder.encode(_service,"UTF-8")) : "";
					query += (selectedservice !=null) ? ("&offices="+URLEncoder.encode(selectedservice,"UTF-8")) : "";
					
					httpget = new HttpGet(Config.URL + "public_service.php?"+query);
					
					
					//httpget = new HttpGet(Config.URL + "public_service.php?sect_id=" + sectorname +"&sub_sect_id="+subsectorname+"&services=9&offices="+ selectedservice);
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
				 				if(items.getString("plot_no").toString() != null || !items.getString("plot_no").toString().equals(""))
					 				mapname[i] = items.getString("plot_no");
				 			} catch (Exception e) {Log.e("Exception", e.toString());}
						}	
						
						
						Config.googleMap = null;
						Intent i =new Intent(GovernmentOfficers.this,GovernmentOfficersMap.class);
						i.putExtra("lat", lat);
						i.putExtra("longi", longi);
						i.putExtra("name", mapname);
						startActivity(i);
					} 
				}catch(Exception e) {}
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
				final Dialog popup = new Dialog(GovernmentOfficers.this);
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
				f.feedback(ctx, GovernmentOfficers.this);
				
				return true;

			case R.id.menu_suggestion: // Suggestion
				Suggestion s = new Suggestion();
				s.suggestion(ctx, GovernmentOfficers.this);
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
}
