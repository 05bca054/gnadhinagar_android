package com.nplussolutions.easygandhinagar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RootMapAdapter extends BaseAdapter
{
	Activity activity;
	
	private static LayoutInflater inflater = null;
	LinearLayout ll_chatLayout_chatLayout;
	ArrayList<RootMapPOJO> rootmap_data = new ArrayList<RootMapPOJO>();

	public RootMapAdapter(Activity activity, ArrayList<RootMapPOJO> rootmap_data) {
		this.activity = activity;
		this.rootmap_data = rootmap_data;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rootmap_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder {
		public TextView txtdes;
		public TextView txtdistance;
		public TextView txtduration;
	}
	
	@Override
	public View getView(int index, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;
		View vi = convertView;

		RootMapPOJO alldistnce = (RootMapPOJO) rootmap_data.get(index);
		
		if (convertView == null) {
			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.item_rootmap,null);
			holder.txtdes = (TextView) vi.findViewById(R.id.txt_des);
			holder.txtdistance = (TextView) vi.findViewById(R.id.txt_distance);
			holder.txtduration = (TextView) vi.findViewById(R.id.txt_duration);
			
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();
		
		holder.txtdes.setText(Html.fromHtml(alldistnce.getDes()));
		holder.txtdistance.setText(alldistnce.getDistance());
		holder.txtduration.setText(alldistnce.getDuration());
		
		return vi;
	}

	
}
