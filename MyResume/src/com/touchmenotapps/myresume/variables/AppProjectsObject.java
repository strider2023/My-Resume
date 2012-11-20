package com.touchmenotapps.myresume.variables;

public class AppProjectsObject {
	
	private String mCompanyName = "";
	private String mProjectName="";
	private String mProjectDescription="";
	
	public AppProjectsObject(String mCompanyName, String mProjectName,
			String mProjectDescription) {
		super();
		this.mCompanyName = mCompanyName;
		this.mProjectName = mProjectName;
		this.mProjectDescription = mProjectDescription;
	}
	
	public String getCompanyName() {
		return mCompanyName;
	}
	
	public String getProjectName() {
		return mProjectName;
	}
	
	public String getProjectDescription() {
		return mProjectDescription;
	}	
}
