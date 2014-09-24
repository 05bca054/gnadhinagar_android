package com.nplussolutions.easygandhinagar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nplussolutions.easygandhinagar.Utils.Config;
import com.nplussolutions.easygandhinagar.Utils.CustomProgressDialog;
import com.nplussolutions.easygandhinagar.Utils.Utility;

public class Suggestion {
	
	Dialog popup_suggest;
	
	EditText txt_suggest_name,txt_suggest_emailid,
	txt_suggest_locationname,txt_suggest_message;
	
	Button btn__suggestion_submit;
	
	public void suggestion(final Context ctx, final Activity activity){
		
		popup_suggest = new Dialog(ctx);
		popup_suggest.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		popup_suggest.setContentView(R.layout.popup_suggestion);
		popup_suggest.setCancelable(true);
		
		txt_suggest_name = (EditText) popup_suggest.findViewById(R.id.txt_suggest_name);
		txt_suggest_emailid = (EditText) popup_suggest.findViewById(R.id.txt_suggest_emailid);
		txt_suggest_locationname = (EditText) popup_suggest.findViewById(R.id.txt_suggest_locationname);
		txt_suggest_message = (EditText) popup_suggest.findViewById(R.id.txt_suggest_message);
		
		btn__suggestion_submit = (Button) popup_suggest.findViewById(R.id.btn__suggestion_submit);
		
		btn__suggestion_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt_suggest_name.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
				} else if(txt_suggest_emailid.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Your Email-Id", Toast.LENGTH_SHORT).show();
				} else if (!Utility.isEmail(txt_suggest_emailid.getText().toString().trim())) {
					Toast.makeText(ctx, "Please Enter Valid Email-Id", Toast.LENGTH_SHORT).show();
				} else if(txt_suggest_locationname.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Your Location Name", Toast.LENGTH_SHORT).show();
				} else if(txt_suggest_message.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Message", Toast.LENGTH_SHORT).show();
				} else{
					if(Utility.isInternetAvailable(ctx))
						new AsyncSuggestion(ctx).execute();
					else
						Utility.showDialog(activity, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
				}
			}
		});
		
		ImageView img_about_close = (ImageView) popup_suggest.findViewById(R.id.img_suggestion_close);
		img_about_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup_suggest.dismiss();
			}
		});
		
		popup_suggest.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
		popup_suggest.show();
	}
		// Async Login
		public class AsyncSuggestion extends AsyncTask<Object, Object, Object> {
			CustomProgressDialog dialog;
			Context ctx;
			public AsyncSuggestion(Context ctx) {
				this.ctx = ctx;
				dialog = new CustomProgressDialog(ctx, "Loading...");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.setCancelable(false);
				dialog.show();
			}
	
			@Override
			public Object doInBackground(Object... params) {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(Config.URL + "PHPMailer/suggest.php");
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(5);
					
					nameValuePairList.add(new BasicNameValuePair("name", txt_suggest_name.getText().toString()));
					nameValuePairList.add(new BasicNameValuePair("email", txt_suggest_emailid.getText().toString()));
					nameValuePairList.add(new BasicNameValuePair("location_name", txt_suggest_locationname.getText().toString()));
					nameValuePairList.add(new BasicNameValuePair("location_detail", txt_suggest_message.getText().toString()));
					
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
					HttpResponse response = httpClient.execute(httpPost);
	
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
					//if (result != null) {
						popup_suggest.dismiss();
					//}
					//else{
							
					//}
					
				} catch (Exception e) {
				}
				dialog.dismiss();
			}
	
			@Override
			public void onProgressUpdate(Object... values) {
				super.onProgressUpdate(values);
			}
		}
}
