package com.touchmenotapps.myresume.variables;

public class ContactDetailsObject {

	private String mAddress = "";
	private String[] mPhoneNumbers;
	private String[] mEmailAddress;
	
	/**
	 * @param mAddress
	 * @param mPhoneNumbers
	 * @param mEmailAddress
	 */
	public ContactDetailsObject(String mAddress, String[] mPhoneNumbers,
			String[] mEmailAddress) {
		super();
		this.mAddress = mAddress;
		this.mPhoneNumbers = mPhoneNumbers;
		this.mEmailAddress = mEmailAddress;
	}
	
	/**
	 * @return the mAddress
	 */
	public String getAddress() {
		return mAddress;
	}
	
	/**
	 * @return the mPhoneNumbers
	 */
	public String[] getPhoneNumbers() {
		return mPhoneNumbers;
	}
	
	/**
	 * @return the mEmailAddress
	 */
	public String[] getEmailAddress() {
		return mEmailAddress;
	}
}
