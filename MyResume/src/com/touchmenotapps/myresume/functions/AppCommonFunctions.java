package com.touchmenotapps.myresume.functions;

import android.content.Context;
import android.util.TypedValue;

public class AppCommonFunctions {
	
	private Context context;
		
	public AppCommonFunctions(Context _context) {
		this.context = _context;
	}
	
	public int getScreenDPI() {
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 1, context.getResources().getDisplayMetrics());
		return dip;
	}
}
