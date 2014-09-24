package com.nplussolutions.easygandhinagar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplaceScreen extends Activity {
	
	protected boolean _active = true;
	protected int _splashTime = 500;
	
	Animation animation;
	ImageView img1,img2,img3;
	Handler handler = new Handler();
	int count = 1;

	
	// Preference
	public static final String PREFS_NAME = "GUJ_GOV";
	SharedPreferences settings;
	Editor editor;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splacescreen);
		
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		img1 = (ImageView) findViewById(R.id.imageView1);
		img2 = (ImageView) findViewById(R.id.imageView3);
		img3 = (ImageView) findViewById(R.id.imageView5);
		
		img1.setVisibility(View.VISIBLE);
		animation = AnimationUtils.loadAnimation(SplaceScreen.this, R.anim.blink);
		img1.startAnimation(animation);
		
		handler.postDelayed(m_Runnable,2500);
		
	}

	public Runnable m_Runnable = new Runnable(){
	    public void run() {
	    	count++;
	    	switch (count) {
			case 1:
				img1.clearAnimation();
				img1.setVisibility(View.GONE);
				handler.postDelayed(m_Runnable,2500);
				break;

			case 2:
				img1.clearAnimation();
				img1.setVisibility(View.GONE);
				img2.setVisibility(View.VISIBLE);
				img2.startAnimation(animation);
				handler.postDelayed(m_Runnable,2500);
				break;

			case 3:
				img2.clearAnimation();
				img2.setVisibility(View.GONE);
				img3.setVisibility(View.VISIBLE);
				img3.startAnimation(animation);
				handler.postDelayed(m_Runnable,2500);
				break;


			default:
				img3.clearAnimation();
				img3.setVisibility(View.GONE);
				handler.removeCallbacks(m_Runnable);
				
				if(!settings.contains("EULA")) {
            		startActivity(new Intent(getApplicationContext(),IAgree.class));
        		}else{
        			startActivity(new Intent(getApplicationContext(),ShowMessage.class));
        		}
                
                finish();
				break;
			}
	    }
	};

}
