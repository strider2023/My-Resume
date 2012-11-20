package com.touchmenotapps.myresume.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
	
	private Context context;
	private static SharedPreferences mPrefs;

	public AppPreferences(Context _context){
		this.context = _context;
		mPrefs = context.getSharedPreferences("MyResumeAppPrefs", 0); //0 = mode private. only this app can read these preferences
	}
	
	public void setProjectsRunned() {
	    SharedPreferences.Editor edit = mPrefs.edit();
	    edit.putBoolean("MyResumeProjectsFirstRun", false);
	    edit.commit();
	}
	
	public void setWorkRunned() {
	    SharedPreferences.Editor edit = mPrefs.edit();
	    edit.putBoolean("MyResumeWorkFirstRun", false);
	    edit.commit();
	}
	
	public void setProfileRunned() {
	    SharedPreferences.Editor edit = mPrefs.edit();
	    edit.putBoolean("MyResumeProfileFirstRun", false);
	    edit.commit();
	}
	
	public boolean getProjectsFirstRun() {
	     return mPrefs.getBoolean("MyResumeProjectsFirstRun", true);
	}
	
	public boolean getWorkFirstRun() {
	     return mPrefs.getBoolean("MyResumeWorkFirstRun", true);
	}
	
	public boolean getProfileFirstRun() {
	     return mPrefs.getBoolean("MyResumeProfileFirstRun", true);
	}
}
