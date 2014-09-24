package com.nplussolutions.easygandhinagar.Utils;

/**
 * @author Nikunj Sorathiya
 * @department Android
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.view.ViewPager.LayoutParams;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nplussolutions.easygandhinagar.R;

@SuppressWarnings("deprecation")
public class Utility{
	
	/**
	  * @param context
	  * Method will start Device GPS automatically.
	 */
	public static void startGPS(Context context) {
		String provider;
		 try {
			provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	        if(!provider.contains("gps")) {
	        	Intent poke = new Intent();
	        	poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
	        	poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        	poke.setData(Uri.parse("3")); 
	        	context.sendBroadcast(poke);
	        }
	        context.sendBroadcast(new Intent("android.location.GPS_ENABLED_CHANGE").putExtra("enabled", true));
		 } catch(Exception e){}
	}
	
	/**
	  * @param SERVER_IP_ADDRESS (String)
	  * @param TCP_SERVER_PORT (int)
	  * @param DATA (String)
	  * 
	  * @return SERVER_RESPONSE (String)
	  * 
	  * Method will send data to given TCP Address and port.
	 */
	public static String sendDataToTCPListener(String SERVER_IP_ADDRESS, int TCP_SERVER_PORT, String DATA) {
		String SERVER_RESPONSE = "";
		
		Socket s = null;
		PrintWriter out = null;
		BufferedReader bReader = null;
		
		try {	
			s = new Socket(SERVER_IP_ADDRESS, TCP_SERVER_PORT);
			
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
			out.write(DATA);
			out.flush();
			bReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			SERVER_RESPONSE = bReader.readLine().toString();
			s.close();
		} catch (Exception e) {
			SERVER_RESPONSE = "E,0";
		}
		return SERVER_RESPONSE;
	}
	
	/**
	  * @param activity (Activity)
	  * @return width (int)
	  * 
	  * Method will return device width.
	 */
	@SuppressLint("NewApi")
	public static int getDeviceWidth(Activity activity) {
		Point size;
		int width = 0;
		WindowManager wm;
		
		wm = activity.getWindowManager();
		size = new Point();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			wm.getDefaultDisplay().getSize(size);
			width = size.x;
		} else {
			Display d = wm.getDefaultDisplay();
			width = d.getWidth();
		}
		return width;
	}
	
	/**
	  * @param activity (Activity)
	  * @return height (int)
	  * 
	  * Method will return device height.
	 */
	@SuppressLint("NewApi")
	public static int getDeviceHeight(Activity activity) {
		Point size;
		int height = 0;
		WindowManager wm;
		
		wm = activity.getWindowManager();
		size = new Point();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			wm.getDefaultDisplay().getSize(size);
			height = size.y;
		} else {
			Display d = wm.getDefaultDisplay();
			height = d.getHeight();
		}
		return height;
	}
	
	/**
	  * @param activity (Activity)
	  * @return Bitmap image
	  * 
	  * Method will take screen shot and return bitmap image. 
	 */
	public static Bitmap takeScreenShot(Activity activity) {
		View view = activity.getCurrentFocus().getRootView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		return Bitmap.createBitmap(view.getDrawingCache());
	}
	
	/**
	  * @param activity (Activity)
	  * 
	  * Method will hide Keyboard. 
	 */
	public static void hideKeyboard(Activity activity) {
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	/**
	  * @param activity (Activity)
	  * @return returnval (boolean)
	  * 
	  * Method will check whether mobile network is available or not. if Available then true otherwis false.  
	 */
	public static boolean isMobileNetvorkAvailable(Activity activity) {
		TelephonyManager tel = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);       
	   	boolean returnval = (tel.getNetworkOperator() != null && tel.getNetworkOperator().equals("")) ? false : true;
	   	return returnval;
	}
	
	/**
	  * @param context (Context)
	  * @return isAvailable (boolean)
	  * 
	  * Method will check whether Internet connection is available or not. if Available then true otherwis false. 
	 */
	public static boolean isInternetAvailable(Context context) {
		NetworkInfo netInfo;
		ConnectivityManager cm;
		
		cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netInfo = cm.getActiveNetworkInfo();
		
		if (netInfo != null && netInfo.isConnected() && netInfo.isAvailable())
			return true;
		
		return false;
	}
	
	/**
	  * @param context (Context)
	  * @return DEVICE_IMEI (String)
	  * 
	  * Method will return device IMEI number if available otherwise it will return Null. 
	 */
	public static String getDeviceIMEI(Context context) {
		TelephonyManager telephonyManager;
		String DEVICE_IMEI = null;
		
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		if(telephonyManager.getDeviceId() != null)
			DEVICE_IMEI = telephonyManager.getDeviceId();
		
		return DEVICE_IMEI;
	}
	
	/**
	  * @param context (Context)
	  * 
	  * @return boolean
	  * 
	  * Method will return true if device is Tablet otherwise False. 
	 */
	public static boolean isTablet(Context context) {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}
	
	/**
	  * @param context (Context)
	  * @param lat (String)
	  * @param lng (String)
	  * 
	  * @return completeaddress (String)
	  * 
	  * Method will return complete address of provided latitude and longitude. 
	 */
	public static String findAddress(Context context, String lat, String lng) {
		String completeaddress="";
		try {
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(context, Locale.getDefault());
			addresses = geocoder.getFromLocation(Double.parseDouble(lat),Double.parseDouble(lng), 1);
			
			if(addresses != null) {
				String address = addresses.get(0).getAddressLine(0);
				String city = addresses.get(0).getAddressLine(1);
				completeaddress = address+","+city;
			}
		} catch(Exception e) {
			Log.w("GetAddress",e.toString());
		}
				
		if(completeaddress.equals(""))
			completeaddress = findAddress(context, lat, lng);

		return completeaddress;
	}
	
	/**
	  * @param context (Context)
	  * 
	  * @return has_key (String)
	  * 
	  * Method will return Hash Key of the given context. 
	 */
	public static String getSignature(Context context) {
		PackageInfo info;
		String has_key = "null";
		try {
		    info = context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);
		    for (Signature signature : info.signatures) {
		        MessageDigest md;
		        md = MessageDigest.getInstance("SHA");
		        md.update(signature.toByteArray());
		        has_key = new String(Base64.encode(md.digest(), 0));
		    }
		} catch (Exception e) {}
		return has_key;
	}
	
	/**
	  * @param context (Context)
	  * 
	  * @return boolean
	  * 
	  * Method will return true if email has valid format otherwise false. 
	 */
	public static boolean isEmail(String email) {
		String MAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern emailPattern = Pattern.compile(MAIL_PATTERN.toString());
		Matcher emailMatcher = emailPattern.matcher(email);
		
		if(emailMatcher.find())
			return true;
		
		return false;
	}
	
	/**
	  * @param context (Context) 
	  * 
	  * Method will generate Log file in sdcard. 
	 */
	public static void generateLog(Context context, String FILE_NAME, String DIR_NAME) {
		File myFile = null;
		try {
	        Process process = Runtime.getRuntime().exec("logcat -d");
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                         
	        StringBuilder log = new StringBuilder();
	        String line;
	        while ((line = bufferedReader.readLine()) != null)
	          log.append("\n" +line);
	        
			if(isSdCardAvailable()) {
				new File("/sdcard/"+DIR_NAME+"/").mkdir();
				myFile = new File("/sdcard/"+DIR_NAME+"/"+FILE_NAME+"_"+getCurrentDateTime("dd_MM_yyyy_HH_mm")+".txt");
			} else {
				Toast.makeText(context, "External Storage not Available", Toast.LENGTH_SHORT).show();
			}
			
	        myFile.createNewFile();
	        FileOutputStream fOut = new FileOutputStream(myFile);
	        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	        myOutWriter.append(log.toString());
	        myOutWriter.close();
	        fOut.close();
		} catch (Exception e) {}
	}
	
	/**
	  * @return boolean
	  * 
	  * Method will check whether External Memory card is available or not.
	 */
	public static boolean isSdCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**	@param pattern (String)
	  * @return dateTime (String)
	  * 
	  * Method will check whether External Memory card is available or not.
	 */
	public static String getCurrentDateTime(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
	/**
	  * @param original (String)
	  * @return MD5 String
	  * 
	  * Method will convert string into MD5.
	 * @throws UnsupportedEncodingException 
	 */
	public static String convertToMd5(String original) {
		
		StringBuffer sb = null;
        try {
        	
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(original.getBytes("UTF-8"));
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {}
        
        return sb.toString();
    }
	
	/**
	  * @param activity (activity)
	  * @param header (String)
	  * @param message (String)
	  * @param icon (Integer)
	  * 
	  * Method will show Alert Dialog with given parameters.
	 */
	
	public static void showDialog(Activity activity, String header, String message, Integer icon) {
		AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setTitle(header);
		alertDialog.setIcon(icon);
		alertDialog.setMessage(message);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}});
		alertDialog.show();
	}
	
	/**
	  * @param activity (activity)
	  * Method will show Custom Dialog as per given parameters.
	 */
	
	public static void showPopupDialog(Activity activity, String txt_message) {
		final Dialog popup_warning_error = new Dialog(activity);
		popup_warning_error.requestWindowFeature(Window.FEATURE_NO_TITLE);
		popup_warning_error.setContentView(R.layout.popup_warning_error);
		popup_warning_error.setCancelable(false);
		
		LinearLayout popup_layout_main = (LinearLayout) popup_warning_error.findViewById(R.id.popup_layout_main); 
		popup_layout_main.setLayoutParams(new LinearLayout.LayoutParams((int) (Utility.getDeviceWidth(activity) / 1.2), LayoutParams.WRAP_CONTENT));
		
		((TextView) popup_warning_error.findViewById(R.id.popup_middle_text)).setText(txt_message);
		((LinearLayout) popup_warning_error.findViewById(R.id.popup_btn_ok_layout)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup_warning_error.dismiss();
			}
		});
		
		popup_warning_error.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popup_warning_error.show();
	}
}