package com.nplussolutions.easygandhinagar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class IAgree extends Activity {

	// Preference
	public static final String PREFS_NAME = "GUJ_GOV";
	SharedPreferences settings;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iagree);
	
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		CheckBox chk_iagree = (CheckBox) findViewById(R.id.checkBox11);
		
		chk_iagree.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					editor.putString("EULA", "Y");
					editor.commit();
					
					startActivity(new Intent(IAgree.this,ShowMessage.class));
					finish();
				}
				
			}
		});
		
	
	}

	

}
