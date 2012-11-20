package com.touchmenotapps.myresume.adapters;

import com.touchmenotapps.myresume.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private String[] header_data, description_data;
	
	public AppListAdapter(Context _context) { 
		mInflater = LayoutInflater.from(_context);
	}
	
	public void SetListData(String[] headerData, String[] contentData)
	{
		this.header_data = headerData;
		this.description_data = contentData;
	}

	public int getCount() {
		return header_data.length;
	}

	public Object getItem(int position) {
		return header_data[position];
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

		holder.mHeaderText.setText(header_data[position]);
		holder.mDescriptionText.setText(description_data[position]);
				
		return convertView;
	}
	
	static class ViewHolder {
		TextView mHeaderText, mDescriptionText;
	}

}
