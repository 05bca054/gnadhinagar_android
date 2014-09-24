package com.nplussolutions.easygandhinagar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nplussolutions.easygandhinagar.Utils.Utility;

public class ShowMessage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_message);
		
		final Dialog popup_warning_error = new Dialog(this);
		popup_warning_error.requestWindowFeature(Window.FEATURE_NO_TITLE);
		popup_warning_error.setContentView(R.layout.popup_warning_error);
		popup_warning_error.setCancelable(false);
		
		LinearLayout popup_layout_main = (LinearLayout) popup_warning_error.findViewById(R.id.popup_layout_main); 
		popup_layout_main.setLayoutParams(new LinearLayout.LayoutParams((int) (Utility.getDeviceWidth(this) / 1.2), LayoutParams.WRAP_CONTENT));
		
		((TextView) popup_warning_error.findViewById(R.id.popup_middle_text)).setText("This Application works best with high speed internet option like 3G or WI-FI");
		((LinearLayout) popup_warning_error.findViewById(R.id.popup_btn_ok_layout)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup_warning_error.dismiss();
				startActivity(new Intent(getApplicationContext(),Home.class));
				finish();
			}
		});
		
		popup_warning_error.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popup_warning_error.show();
		
	}

	

}
