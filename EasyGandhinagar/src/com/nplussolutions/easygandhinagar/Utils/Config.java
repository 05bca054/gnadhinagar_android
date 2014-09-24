package com.nplussolutions.easygandhinagar.Utils;

import android.app.Application;

import com.google.android.gms.maps.GoogleMap;

// Application Level Global Variables.
public class Config extends Application 
{
	//public static String URL = "http://nplussolutions.com/Webservices_Gujgov/";
	public static String URL = "http://nplussolutions.com/Webservices_Gujgov_finaldata/";

	public static GoogleMap googleMap;
	
	public static boolean isGps;
	public static boolean isStarted = false;
	
	public static Double CURRENT_LATITUDE = 0.0;
	public static Double CURRENT_LONGITUDE = 0.0;
	//public static Double CURRENT_LATITUDE = 23.0300;
	//public static Double CURRENT_LONGITUDE = 72.5800;
	
	public static String CURRENT_LATITUDE_TO = "0.0";
	public static String CURRENT_LONGITUDE_TO = "0.0";
	
	public static int MAP_INTERVAL_POS = 0;
	public static int MAP_INTERVAL = 10000;
}