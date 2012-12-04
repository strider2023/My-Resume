package com.touchmenotapps.myresume.variables;

public class WorkExpDetialsObject {

	private String mCompanyName = "";
	private String mWorkSpan = "";
	private String mWorkDetails = "";
	
	/**
	 * @param mCompanyName
	 * @param mWorkSpan
	 * @param mWorkDetails
	 */
	public WorkExpDetialsObject(String mCompanyName, String mWorkSpan,
			String mWorkDetails) {
		super();
		this.mCompanyName = mCompanyName;
		this.mWorkSpan = mWorkSpan;
		this.mWorkDetails = mWorkDetails;
	}

	/**
	 * @return the mCompanyName
	 */
	public String getCompanyName() {
		return mCompanyName;
	}

	/**
	 * @return the mWorkSpan
	 */
	public String getWorkSpan() {
		return mWorkSpan;
	}

	/**
	 * @return the mWorkDetails
	 */
	public String getWorkDetails() {
		return mWorkDetails;
	}
}
