package com.touchmenotapps.myresume.adapters;

import java.util.ArrayList;

import com.touchmenotapps.myresume.R;
import com.touchmenotapps.myresume.variables.ProjectsDetailsObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProjectsListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private ArrayList<ProjectsDetailsObject> mData = new  ArrayList<ProjectsDetailsObject>(0);
	
	public ProjectsListAdapter(Context _context) { 
		mInflater = LayoutInflater.from(_context);
	}
	
	public void setListData(ArrayList<ProjectsDetailsObject> data) {
		this.mData = data;
	}

	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		return mData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		/** Defining the custom list layout and attaching the display data to it */
		if (convertView == null) {
			convertView	= mInflater.inflate(R.layout.layout_listview_item, null);
			holder = new ViewHolder();
			holder.mHeaderText	= (TextView) convertView.findViewById(R.id.listview_header_text);
			holder.mDescriptionText	= (TextView) convertView.findViewById(R.id.listview_content_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mHeaderText.setText(mData.get(position).getProjectName());
		holder.mDescriptionText.setText(mData.get(position).getProjectDescription());
		return convertView;
	}
	
	static class ViewHolder {
		TextView mHeaderText, mDescriptionText;
	}

}
