package com.touchmenotapps.myresume.variables;

public class ProjectsDetailsObject {
	
	private String mProjectName = "";
	private String mProjectDescription = "";
	
	/**
	 * @param mProjectName
	 * @param mProjectDescription
	 */
	public ProjectsDetailsObject(String mProjectName,
			String mProjectDescription) {
		super();
		this.mProjectName = mProjectName;
		this.mProjectDescription = mProjectDescription;
	}

	/**
	 * @return the mProjectName
	 */
	public String getProjectName() {
		return mProjectName;
	}

	/**
	 * @return the mProjectDescription
	 */
	public String getProjectDescription() {
		return mProjectDescription;
	}
}
