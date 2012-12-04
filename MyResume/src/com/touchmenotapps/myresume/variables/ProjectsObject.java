package com.touchmenotapps.myresume.variables;

import java.util.ArrayList;

public class ProjectsObject {
	
	private String mCompanyName = "";
	private ArrayList<ProjectsDetailsObject> mProjectDescription = new ArrayList<ProjectsDetailsObject>(0);
	
	/**
	 * @param mCompanyName
	 * @param mProjectDescription
	 */
	public ProjectsObject(String mCompanyName,
			ArrayList<ProjectsDetailsObject> mProjectDescription) {
		super();
		this.mCompanyName = mCompanyName;
		this.mProjectDescription = mProjectDescription;
	}

	/**
	 * @return the mCompanyName
	 */
	public String getCompanyName() {
		return mCompanyName;
	}

	/**
	 * @return the mProjectDescription
	 */
	public ArrayList<ProjectsDetailsObject> getProjectDescription() {
		return mProjectDescription;
	}
}
