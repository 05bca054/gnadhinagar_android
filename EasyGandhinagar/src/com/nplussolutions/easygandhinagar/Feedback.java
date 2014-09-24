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

public class Feedback {
	
	Dialog popup_feedback;
	
	EditText txt_feedback_name,txt_feedback_emailid,txt_feedback_message;
	
	Button btn_feedback_submit;
	
	public void feedback(final Context ctx, final Activity activity){
		
		popup_feedback = new Dialog(ctx);
		popup_feedback.requestWindowFeature(Window.FEATURE_NO_TITLE);    
		popup_feedback.setContentView(R.layout.popup_feedback);
		popup_feedback.setCancelable(true);
		
		txt_feedback_name = (EditText) popup_feedback.findViewById(R.id.txt_feedback_name);
		txt_feedback_emailid = (EditText) popup_feedback.findViewById(R.id.txt_feedback_emailid);
		txt_feedback_message = (EditText) popup_feedback.findViewById(R.id.txt_feedback_message);
		
		btn_feedback_submit = (Button) popup_feedback.findViewById(R.id.btn_feedback_submit);
		
		btn_feedback_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt_feedback_name.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
				} else if(txt_feedback_emailid.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Your Email-Id", Toast.LENGTH_SHORT).show();
				} else if (!Utility.isEmail(txt_feedback_emailid.getText().toString().trim())) {
					Toast.makeText(ctx, "Please Enter Valid Email-Id", Toast.LENGTH_SHORT).show();
				}  else if(txt_feedback_message.getText().toString().trim().length() == 0) {
					Toast.makeText(ctx, "Please Enter Message", Toast.LENGTH_SHORT).show();
				} else{
					if(Utility.isInternetAvailable(ctx))
						new AsyncFeedback(ctx).execute();
					else
						Utility.showDialog(activity, "Easy Gandhinagar", "Something Going Wrong with your Internet Connection!!", R.drawable.ic_launcher);
				}
			}
		});
		
		ImageView img_about_close = (ImageView) popup_feedback.findViewById(R.id.img_feedback_close);
		img_about_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup_feedback.dismiss();
			}
		});
		
		popup_feedback.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
		popup_feedback.show();
	}
		// Async Login
		public class AsyncFeedback extends AsyncTask<Object, Object, Object> {
			CustomProgressDialog dialog;
			Context ctx;
			
			public AsyncFeedback(Context ctx) {
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
					HttpPost httpPost = new HttpPost(Config.URL + "PHPMailer/feedback.php");
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(3);
					
					nameValuePairList.add(new BasicNameValuePair("name", txt_feedback_name.getText().toString()));
					nameValuePairList.add(new BasicNameValuePair("email", txt_feedback_emailid.getText().toString()));
					nameValuePairList.add(new BasicNameValuePair("feedback", txt_feedback_message.getText().toString()));
					
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
						popup_feedback.dismiss();
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
