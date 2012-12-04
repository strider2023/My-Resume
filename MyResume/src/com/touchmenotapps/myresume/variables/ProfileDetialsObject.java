package com.touchmenotapps.myresume.variables;

import java.util.ArrayList;

public class ProfileDetialsObject {

	private String mHeader = null;
	private ArrayList<String> mDetailsData = null;
	
	/**
	 * @param mHeader
	 * @param mDetailsData
	 */
	public ProfileDetialsObject(String mHeader, ArrayList<String> mDetailsData) {
		super();
		this.mHeader = mHeader;
		this.mDetailsData = mDetailsData;
	}

	/**
	 * @return the mHeader
	 */
	public String getHeader() {
		return mHeader;
	}

	/**
	 * @return the mDetailsData
	 */
	public ArrayList<String> getDetailsData() {
		return mDetailsData;
	}
}
