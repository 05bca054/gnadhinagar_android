package com.nplussolutions.easygandhinagar;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.Utility;

public class CircleMap extends FragmentActivity {
	Context ctx;
	MarkerOptions marker;
	Handler handler_mapupdate = new Handler();
	Handler handler_map = new Handler();
	Handler handler_maplasttime = new Handler();
	
	Button btn_exit_no_dialog,btn_location_no_dialog;
	Button btn_exit_yes_dialog,btn_location_yes_dialog ;
	
	String[] lat,longi,name,plotno;
	LocationManager locationManager;
	LatLng fromPosition;
	LatLng toPosition;
	
	Spanned directiontext;
	Button btnfindroot;
	ListView lv_rootmap;
	
	Button btn_popup_ok;
	Spinner spn_popup_updatelocation;
	Button btn_yes_popup_deleteaccount, btn_no_popup_deleteaccount;
	
	ArrayList<RootMapPOJO> root_arr;
	
	ArrayList<String> des;
	ArrayList<String> duration;
	ArrayList<String> distance;
	
	public static final String PREFS_NAME = "GUJ_GOV";
	SharedPreferences settings;
	Editor editor;
	
	float zoomlevel = 15;
	CustomProgressDialog pdialog;
	
	ArrayList<Integer> icons = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigationmap);

		ctx = this;
		
		Intent i = getIntent();
		
		lat = i.getStringArrayExtra("lat");
		longi = i.getStringArrayExtra("longi");
		plotno = i.getStringArrayExtra("plotno");
		name = i.getStringArrayExtra("name");

		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		icons.add(R.drawable.ic_launcher);
		icons.add(R.drawable.ic_launcher);
		icons.add(R.drawable.play_ground);
		icons.add(R.drawable.shopping_complex);
		icons.add(R.drawable.secondary_schools);
		icons.add(R.drawable.temple);
		icons.add(R.drawable.hotel);
		icons.add(R.drawable.petrol_pump_gas_station);
		icons.add(R.drawable.collage);
		icons.add(R.drawable.govt_offices);
		icons.add(R.drawable.hospital);
		icons.add(R.drawable.geast_house);
		icons.add(R.drawable.bus_stations);
		icons.add(R.drawable.commercial);
		icons.add(R.drawable.police_station);
		icons.add(R.drawable.balmandir);
		icons.add(R.drawable.community_center);
		icons.add(R.drawable.post_offoce);
		icons.add(R.drawable.bank);
		icons.add(R.drawable.balmandir);
		icons.add(R.drawable.parking);
		icons.add(R.drawable.tennis_court);
		icons.add(R.drawable.vegitable_market);
		icons.add(R.drawable.haveli);
		icons.add(R.drawable.balmandir);
		icons.add(R.drawable.library);
		icons.add(R.drawable.fire_station);
		icons.add(R.drawable.university);
		icons.add(R.drawable.campus);
		icons.add(R.drawable.vegitable_market);
		icons.add(R.drawable.dairy);
		icons.add(R.drawable.private_company);
		icons.add(R.drawable.resort);
		icons.add(R.drawable.park);
		icons.add(R.drawable.lake);// Lake
		icons.add(R.drawable.lake);//stepwall
		icons.add(R.drawable.play_ground);//circle
		
		Utility.showPopupDialog(CircleMap.this,"Please tap on appropriate icon to get name of place and then tap on name to get route from current location.");
		
		initMap();

	}

	
	private void initMap() {
		try {
			if (Config.googleMap == null) {
				Config.googleMap = ((SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}

			
			if(settings.contains("zoomLevel")){
				zoomlevel = Config.googleMap.getCameraPosition().zoom;
				if(zoomlevel == 2.0)
					zoomlevel = Float.parseFloat(settings.getString("zoomLevel",null).toString());
				
				editor.putString("zoomLevel", ""+zoomlevel);
				editor.commit();
				
				zoomlevel = Float.parseFloat(settings.getString("zoomLevel",null).toString());
			}else{
				zoomlevel = Config.googleMap.getCameraPosition().zoom;
				if(zoomlevel == 2.0)
					zoomlevel = 12;
				
				editor.putString("zoomLevel", ""+zoomlevel);
				editor.commit();
			}
			Config.googleMap.setMyLocationEnabled(true);

				
				Config.googleMap.getUiSettings().setZoomControlsEnabled(true);
				Config.googleMap.clear();

				for(int i=0;i<plotno.length;i++){
					
					marker = new MarkerOptions().position(new LatLng(Double.parseDouble(lat[i]), Double.parseDouble(longi[i]))).title(name[i]).snippet(lat[i]+","+longi[i]);
					marker.icon(BitmapDescriptorFactory.fromResource(icons.get(Integer.parseInt(plotno[i]))));
					
					Config.CURRENT_LATITUDE_TO = lat[i];
					Config.CURRENT_LONGITUDE_TO = longi[i];
					
					
					Config.googleMap.addMarker(marker);
				}
			
				CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(Double.parseDouble(Config.CURRENT_LATITUDE_TO), Double.parseDouble(Config.CURRENT_LONGITUDE_TO))).zoom(zoomlevel).build();
		
				Config.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				
				Config.googleMap.setInfoWindowAdapter(new InfoWindowAdapter()
				{
		            @Override
		            public View getInfoWindow(Marker args) {
		                return null;
		            }
		            // Defines the contents of the InfoWindow
		            @Override
		            public View getInfoContents(Marker args) {
		            	View v = getLayoutInflater().inflate(R.layout.custom_marker_layout, null);
		            	final TextView name1 = (TextView) v.findViewById(R.id.num_txt);
		            	final TextView lat_longi = (TextView) v.findViewById(R.id.marker_lat_long);

		            	name1.setText(args.getTitle().toString());
		                try{
		                	String _lat_longi[] = args.getSnippet().toString().split(",");
		                	String _lat = _lat_longi[0].toString();
		                	String _longi = _lat_longi[1].toString();
		                	
		                	lat_longi.setText(_lat);
		                	lat_longi.setTag(_longi);
		                	name1.setTag(args.getSnippet().toString());
		                }catch (Exception e) {}
		                
		            	Config.googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {          
		                    public void onInfoWindowClick(Marker marker) 
		                    {
		                    	Config.CURRENT_LATITUDE_TO = name1.getTag().toString().split(",")[0].toString();
		                    	Config.CURRENT_LONGITUDE_TO = name1.getTag().toString().split(",")[1].toString();
		                    	
		                    	pdialog = new CustomProgressDialog(CircleMap.this,"Find Location...");
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
		                });
		            	
		            	
		                return v;
		            }
		        });  

		} catch (Exception e) {
			e.printStackTrace();
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
				
				final Dialog popup = new Dialog(CircleMap.this);
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
				
				final Dialog popup = new Dialog(CircleMap.this);
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
						stopService(new Intent(CircleMap.this, LocationService.class));
						CircleMap.this.finish();
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
				
				finish();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<RootMapPOJO> getRootMap()
	{
		for(int i=0;i<des.size();i++){
			root_arr.add(new RootMapPOJO(des.get(i), distance.get(i), duration.get(i)));
		}
		return root_arr;
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
			final Dialog popup = new Dialog(CircleMap.this);
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
			f.feedback(ctx, CircleMap.this);
			
			return true;

		case R.id.menu_suggestion: // Suggestion
			Suggestion s = new Suggestion();
			s.suggestion(ctx, CircleMap.this);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
