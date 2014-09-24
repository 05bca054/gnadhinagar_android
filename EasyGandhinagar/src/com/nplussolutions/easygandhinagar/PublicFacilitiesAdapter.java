package com.nplussolutions.easygandhinagar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PublicFacilitiesAdapter extends BaseAdapter
{
	// Variable Declaration
	private static LayoutInflater inflater = null;
	Activity activity;
	ArrayList<PublicFacilitiesPOJO> test;
	
	public PublicFacilitiesAdapter(Activity activity, ArrayList<PublicFacilitiesPOJO> test) {
		this.activity = activity;
		this.test = test;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return test.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		CheckBox chk;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		if (convertView == null) {
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_public_facilities, null);
			
			holder = new ViewHolder();
			holder.chk = (CheckBox) convertView.findViewById(R.id.checkBox1);

			convertView.setTag(holder);
		} 
		else
			holder = (ViewHolder) convertView.getTag();
		
		holder.chk.setText(test.get(position).getName());
		holder.chk.setTag(position);
		holder.chk.setChecked(test.get(position).isCheck());
		
		holder.chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				test.get(Integer.parseInt(buttonView.getTag().toString())).setCheck(isChecked);
				
			}
		});
		return convertView;
	}
	
	
}
