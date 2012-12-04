package com.touchmenotapps.myresume.variables;

public class InterestsDetailsObject {

	private String mInterestName = "";
	private String mInterestLink = "";
	
	/**
	 * @param mInterestName
	 * @param mInterestLink
	 */
	public InterestsDetailsObject(String mInterestName, String mInterestLink) {
		super();
		this.mInterestName = mInterestName;
		this.mInterestLink = mInterestLink;
	}
	
	/**
	 * @return the mInterestName
	 */
	public String getInterestName() {
		return mInterestName;
	}
	
	/**
	 * @return the mInterestLink
	 */
	public String getInterestLink() {
		return mInterestLink;
	}
}
