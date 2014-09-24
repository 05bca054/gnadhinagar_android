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
import android.widget.TextView;

import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.MyListView;
import com.nplussolutions.easygandhinagar.Utils.Utility;

public class GandhinagarCircle extends Activity {
	Context ctx;
	LinearLayout ll_navigation;
	Button btn_home,btn_quit,btn_footersearch;
	String[] id,name;
	String sectorid,subsectorid;
	List<String> sector_arr;
	List<String> subsector_arr;
	Button btn_exit_no_dialog;
	Button btn_exit_yes_dialog ;
	String selected_service = "";
	String[] lat,longi,mapname,_ploatno;
	ArrayAdapter<String> adp_subsecter;
	TextView no_result_found;
	
	LinkedHashMap<String,String> services_list;
	
	MyListView lv_circle_ch,lv_circle_chh,lv_circle_g,lv_circle_gh,lv_circle_j,lv_circle_k,lv_circle_kh;
	
	ArrayList<PublicFacilitiesPOJO> list_service_arr_ch;
	ArrayList<PublicFacilitiesPOJO> list_service_arr_chh;
	ArrayList<PublicFacilitiesPOJO> list_service_arr_g;
	ArrayList<PublicFacilitiesPOJO> list_service_arr_gh;
	ArrayList<PublicFacilitiesPOJO> list_service_arr_j;
	ArrayList<PublicFacilitiesPOJO> list_service_arr_k;
	ArrayList<PublicFacilitiesPOJO> list_service_arr_kh;
	
	String[] ch_name = {"Ch 0 - Surajya","Ch 1","Ch 2 - Nyay","Ch 3 - Kisan","Ch 4","Ch 5 - Prerana","Ch 6 - Yuva chetna","Ch 7"};
	String[] ch_gid = {"34644","34645","34646","34647","34651","34649","34650","34651"};
	
	String[] chh_name = {"Chh 3 - Parinam","Chh 4 A - Panchamrut","Chh 4 B - Punit","Chh 5 - Purusharth","Chh 6 - Bal chetna"};
	String[] chh_gid = {"34652","34653","34654","34655","34656"};
	
	String[] g_name = {"G 0","G 1 - Sangit","G 1.5","G 2 - Sadbhav","G 3 - Sant Rohidas","G 5 - Seva","G 6 - Sanskar","G 7"};
	String[] g_gid = {"34628","34627","34629","34630","34631","34632","34633","34634"};
	
	String[] gh_name = {"Gh 0 - Chanakya","Gh 1","Gh 1.5","Gh 2 - Hemchandracharya","Gh 3 - Zaverchand Meghani","Gh 4","Gh 5 - Ravishankar Maharaj","Gh 6","Gh 7 - Dayananda Saraswati"};
	String[] gh_gid = {"34635","34636","34637","34638","34639","34640","34641","34642","34643"};
	
	String[] j_name = {"J 5 - Panchashakti",
			"J 6 - Nari chetna",
			"J 7 - Pragati"};
	String[] j_gid = {"34657",
			"34658",
			"34659"};
	
	String[] k_name = {"K 5 - Kranti",
			"K 6 - Sanskrut",
			"K 7 - Yoga"};
	String[] k_gid = {"34617",
			"34618",
			"34619"};
	
	String[] kh_name = {"Kh - 0","Kh - 1","Kh - 2","Kh 3 - Satya","Kh 5 - Ahimsa","Kh 6 - Sanskruti","Kh 7 - Dharma"};
	String[] kh_gid = {"34617","34618","34619","34623","34622","34621","34620"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_list);
		
		ctx = this;
		
		lv_circle_ch = (MyListView) findViewById(R.id.lv_circle_ch);
		lv_circle_chh = (MyListView) findViewById(R.id.lv_circle_chh);
		lv_circle_g = (MyListView) findViewById(R.id.lv_circle_g);
		lv_circle_gh = (MyListView) findViewById(R.id.lv_circle_gh);
		lv_circle_j = (MyListView) findViewById(R.id.lv_circle_j);
		lv_circle_k = (MyListView) findViewById(R.id.lv_circle_k);
		lv_circle_kh = (MyListView) findViewById(R.id.lv_circle_kh);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		btn_footersearch = (Button) findViewById(R.id.btn_footersearch);
		
		no_result_found = (TextView) findViewById(R.id.txt_no_pub_found);
		
		ll_navigation = (LinearLayout) findViewById(R.id.ll_public_facilities);
		ll_navigation.setLayoutParams(new LinearLayout.LayoutParams((int)(Utility.getDeviceHeight(GandhinagarCircle.this) / 2), LayoutParams.WRAP_CONTENT));

		
		/*if(Utility.isInternetAvailable(ctx)){
			new AsyncCircle().execute();
		}else{
			Utility.showDialog(GandhinagarCircle.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
		}*/
		
		try {
			id = new String[ch_gid.length];
			name = new String[ch_name.length];
				
			for (int i=0; i<ch_name.length; i++) {
				id[i] = ch_gid[i].toString();
				name[i] = ch_name[i].toString();
			}					
				
			list_service_arr_ch = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_ch=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameCh());
			lv_circle_ch.setAdapter(adp_ch);
			
			id = new String[chh_gid.length];
			name = new String[chh_name.length];
				
			for (int i=0; i<chh_name.length; i++) {
				id[i] = chh_gid[i].toString();
				name[i] = chh_name[i].toString();
			}					
				
			list_service_arr_chh = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_chh=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameChh());
			lv_circle_chh.setAdapter(adp_chh);
			
			id = new String[g_gid.length];
			name = new String[g_name.length];
				
			for (int i=0; i<g_name.length; i++) {
				id[i] = g_gid[i].toString();
				name[i] = g_name[i].toString();
			}					
				
			list_service_arr_g = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_g=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameG());
			lv_circle_g.setAdapter(adp_g);
			
			id = new String[gh_gid.length];
			name = new String[gh_name.length];
				
			for (int i=0; i<gh_name.length; i++) {
				id[i] = gh_gid[i].toString();
				name[i] = gh_name[i].toString();
			}					
				
			list_service_arr_gh = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_gh=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameGh());
			lv_circle_gh.setAdapter(adp_gh);
			
			id = new String[j_gid.length];
			name = new String[j_name.length];
				
			for (int i=0; i<j_name.length; i++) {
				id[i] = j_gid[i].toString();
				name[i] = j_name[i].toString();
			}					
				
			list_service_arr_j = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_j=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameJ());
			lv_circle_j.setAdapter(adp_j);
			
			id = new String[k_gid.length];
			name = new String[k_name.length];
				
			for (int i=0; i<k_name.length; i++) {
				id[i] = k_gid[i].toString();
				name[i] = k_name[i].toString();
			}					
				
			list_service_arr_k = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_k=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameK());
			lv_circle_k.setAdapter(adp_k);
			
			id = new String[kh_gid.length];
			name = new String[kh_name.length];
				
			for (int i=0; i<kh_name.length; i++) {
				id[i] = kh_gid[i].toString();
				name[i] = kh_name[i].toString();
			}					
				
			list_service_arr_kh = new ArrayList<PublicFacilitiesPOJO>();
			
			PublicFacilitiesAdapter adp_kh=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllNameKh());
			lv_circle_kh.setAdapter(adp_kh);
		}catch(Exception e) {}
		
		btn_footersearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selected_service = "";
				try{
					for(int i=0;i<list_service_arr_ch.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_ch.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}
					for(int i=0;i<list_service_arr_chh.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_chh.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}
					for(int i=0;i<list_service_arr_g.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_g.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}
					for(int i=0;i<list_service_arr_gh.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_gh.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}
					for(int i=0;i<list_service_arr_j.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_j.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}
					for(int i=0;i<list_service_arr_k.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_k.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}
					for(int i=0;i<list_service_arr_kh.size();i++){
						PublicFacilitiesPOJO obj = list_service_arr_kh.get(i);
						if(obj.isCheck())
							selected_service += obj.getId()+",";
					}

					
					if (selected_service.length() > 0 && selected_service.charAt(selected_service.length()-1)==',') {
						selected_service = selected_service.substring(0, selected_service.length()-1);
					}
					if(Utility.isInternetAvailable(ctx))
						new AsyncServicesSearrch(sectorid, subsectorid, selected_service).execute();
					else
						Utility.showDialog(GandhinagarCircle.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
					
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
		final Dialog exitapp = new Dialog(GandhinagarCircle.this);
		
		exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		exitapp.setContentView(R.layout.popup_logout);
		exitapp.setCancelable(false);
		btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
		btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
		btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				stopService(new Intent(GandhinagarCircle.this, LocationService.class));
				GandhinagarCircle.this.finish();
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
	public ArrayList<PublicFacilitiesPOJO> getAllNameCh(){
		for(int i=0;i<id.length;i++){
			list_service_arr_ch.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_ch;
	}
	public ArrayList<PublicFacilitiesPOJO> getAllNameChh(){
		for(int i=0;i<id.length;i++){
			list_service_arr_chh.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_chh;
	}
	public ArrayList<PublicFacilitiesPOJO> getAllNameG(){
		for(int i=0;i<id.length;i++){
			list_service_arr_g.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_g;
	}
	public ArrayList<PublicFacilitiesPOJO> getAllNameGh(){
		for(int i=0;i<id.length;i++){
			list_service_arr_gh.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_gh;
	}
	public ArrayList<PublicFacilitiesPOJO> getAllNameJ(){
		for(int i=0;i<id.length;i++){
			list_service_arr_j.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_j;
	}
	public ArrayList<PublicFacilitiesPOJO> getAllNameK(){
		for(int i=0;i<id.length;i++){
			list_service_arr_k.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_k;
	}
	public ArrayList<PublicFacilitiesPOJO> getAllNameKh(){
		for(int i=0;i<id.length;i++){
			list_service_arr_kh.add(new PublicFacilitiesPOJO(name[i],id[i],false));
		}
		return list_service_arr_kh;
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

		
	
	/*// Service Async Thread
	public class AsyncCircle extends AsyncTask<Object, Object, Object> {
		CustomProgressDialog dialog;
		HttpClient httpclient;
		HttpGet httpget;
		HttpResponse response;
							
		public AsyncCircle() {
			
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
				
				httpget = new HttpGet(Config.URL + "circle_navi_list.php");
				
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
					lv_circle.setVisibility(View.VISIBLE);
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
					
					PublicFacilitiesAdapter adp=new PublicFacilitiesAdapter(GandhinagarCircle.this, getAllName());
					lv_circle.setAdapter(adp);						
				} 
			}catch(Exception e) {
				Utility.showPopupDialog(GandhinagarCircle.this,"Gandhinagar Circle not availble.");
				
				lv_circle.setAdapter(null);
			}
			dialog.dismiss();
		}

		@Override
		public void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
		}
	}*/
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
				
				httpget = new HttpGet(Config.URL + "circle_navi_select.php?"+query);
				
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
					Intent i =new Intent(GandhinagarCircle.this,CircleMap.class);
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
			final Dialog popup = new Dialog(GandhinagarCircle.this);
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
			f.feedback(ctx, GandhinagarCircle.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, GandhinagarCircle.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
