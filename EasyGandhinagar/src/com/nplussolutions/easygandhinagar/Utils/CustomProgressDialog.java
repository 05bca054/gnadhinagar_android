package com.nplussolutions.easygandhinagar.Utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nplussolutions.easygandhinagar.R;

public class CustomProgressDialog extends Dialog {
	// Variable and Object Declaration
	Context context;
	String name;

	public CustomProgressDialog(Context context, String name) {
		super(context);
		this.context = context;
		this.name = name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_progressdialog);
		((TextView) findViewById(R.id.txt_dialog)).setText(name);
		((ImageView) findViewById(R.id.img)).startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate));
	}
}
