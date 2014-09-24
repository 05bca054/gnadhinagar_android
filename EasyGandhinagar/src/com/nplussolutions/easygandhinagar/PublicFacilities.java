package com.nplussolutions.easygandhinagar;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

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
import android.widget.Button;
import android.widget.ImageView;

import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.Utility;

public class PublicFacilities extends Activity {

	Context ctx;
	
	String[] id,_name,sectorname;
	Button btn_exit_no_dialog;
	Button btn_exit_yes_dialog ;
	
	Button btn_home,btn_quit,btn_footersearch;
	
	Button btn_pub_postoffice,btn_pub_hospital,btn_pub_petrolpump,btn_pub_school,btn_pub_policestation,btn_pub_firestation,
	btn_pub_religious,btn_pub_garden,btn_pub_bank,btn_pub_theartre,btn_pub_communityhall,btn_pub_hotels,btn_pub_shoppingcenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.public_facilities);
		
		ctx = this;
		
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		btn_footersearch = (Button) findViewById(R.id.btn_footersearch);
		btn_footersearch.setVisibility(View.INVISIBLE);
		
		btn_pub_postoffice = (Button) findViewById(R.id.btn_pub_postoffice);
		btn_pub_hospital = (Button) findViewById(R.id.btn_pub_hospital);
		btn_pub_petrolpump = (Button) findViewById(R.id.btn_pub_petrolpump);
		btn_pub_school = (Button) findViewById(R.id.btn_pub_school);
		btn_pub_policestation = (Button) findViewById(R.id.btn_pub_policestation);
		btn_pub_firestation = (Button) findViewById(R.id.btn_pub_firestation);
		btn_pub_religious = (Button) findViewById(R.id.btn_pub_religious);
		btn_pub_garden = (Button) findViewById(R.id.btn_pub_garden);
		btn_pub_bank = (Button) findViewById(R.id.btn_pub_bank);
		btn_pub_theartre = (Button) findViewById(R.id.btn_pub_theartre);
		btn_pub_communityhall = (Button) findViewById(R.id.btn_pub_communityhall);
		btn_pub_hotels = (Button) findViewById(R.id.btn_pub_hotels);
		btn_pub_shoppingcenter = (Button) findViewById(R.id.btn_pub_shoppingcenter);
		
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
		
		btn_pub_postoffice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_postoffice.getText().toString(),btn_pub_postoffice.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_hospital.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_hospital.getText().toString(),btn_pub_hospital.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_petrolpump.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_petrolpump.getText().toString(),btn_pub_petrolpump.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_school.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_school.getText().toString(),btn_pub_school.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_policestation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_policestation.getText().toString(),btn_pub_policestation.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_firestation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_firestation.getText().toString(),btn_pub_firestation.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_religious.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_religious.getText().toString(),btn_pub_religious.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_garden.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_garden.getText().toString(),btn_pub_garden.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_bank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_bank.getText().toString(),btn_pub_bank.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_theartre.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_theartre.getText().toString(),btn_pub_theartre.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_communityhall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_communityhall.getText().toString(),btn_pub_communityhall.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_hotels.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_hotels.getText().toString(),btn_pub_hotels.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
		
		btn_pub_shoppingcenter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Utility.isInternetAvailable(ctx))
					new AsyncPublicServices(btn_pub_shoppingcenter.getText().toString(),btn_pub_shoppingcenter.getTag().toString()).execute();
				else
					Utility.showDialog(PublicFacilities.this, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
			}
		});
	}
	//Exit App
	public void exitApp(){
		final Dialog exitapp = new Dialog(PublicFacilities.this);
		
		exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		exitapp.setContentView(R.layout.popup_logout);
		exitapp.setCancelable(false);
		btn_exit_no_dialog = (Button) exitapp.findViewById(R.id.btn_exit_no_dialog);
		btn_exit_yes_dialog = (Button) exitapp.findViewById(R.id.btn_exit_yes_dialog);
		btn_exit_yes_dialog.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				stopService(new Intent(PublicFacilities.this, LocationService.class));
				PublicFacilities.this.finish();
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
	public class AsyncPublicServices extends AsyncTask<Object, Object, Object> {
		String publicname,bui_id;
		CustomProgressDialog dialog;
		HttpClient httpclient;
		HttpGet httpget;
		HttpResponse response;
							
		public AsyncPublicServices(String publicname,String bui_id) {
			this.bui_id=bui_id;
			this.publicname = publicname;
			
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
				query += (bui_id !=null) ? ("bui_id="+URLEncoder.encode(bui_id,"UTF-8")) : "";
				 
				httpget = new HttpGet(Config.URL + "button_pubserv.php?"+query);
				//http://nplussolutions.com/Webservices_Gujgov_finaldata/button_pubserv.php?bui_id=5
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
					_name = new String[service.length()];
					sectorname = new String[service.length()];
					
					for (int i=0; i<service.length(); i++) {
					    JSONObject items = service.getJSONObject(i);
					    
					    try{
			 				if(items.getString("gid").toString() != null || !items.getString("gid").toString().equals(""))
				 				id[i] = items.getString("gid");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
			 			
			 			try{
			 				if(items.getString("plot_no").toString() != null || !items.getString("plot_no").toString().equals(""))
			 					_name[i] = items.getString("plot_no");
			 			} catch (Exception e) {Log.e("Exception", e.toString());}
			 			
			 			
					}					
							
					Intent intent = new Intent(PublicFacilities.this,PublicFacilitiesList.class);
					intent.putExtra("listname", publicname);
					intent.putExtra("gid", id);
					intent.putExtra("name", _name);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				} 
			}catch(Exception e) {
				Utility.showPopupDialog(PublicFacilities.this,"Public Services not availble.");
				
			}
			dialog.dismiss();
		}

		@Override
		public void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
		}
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
			final Dialog popup = new Dialog(PublicFacilities.this);
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
			f.feedback(ctx, PublicFacilities.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, PublicFacilities.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
