package com.touchmenotapps.myresume.variables;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DataParsingFunctions {
	
	private final String RESUME_TAG = "resume";
	private final String NAME_TAG = "name";
	private final String PERIOD_TAG = "period";
	private final String ADDRESS_TAG = "address";
	private final String PHONE_TAG = "phone";
	private final String MAIL_TAG = "mail";
	private final String COMPANY_TAG = "company";
	private final String FIRST_NAME_TAG = "first-name";
	private final String MIDDLE_NAME_TAG = "middle-name";
	private final String LAST_NAME_TAG = "last-name";
	private final String INTERESTS_DETAILS_TAG = "interests-details";
	private final String WORK_DETAILS_TAG = "work-detial";
	private final String PROJECT_DETAILS_TAG = "projects-details";
	private final String PROFILE_DETAILS_TAG = "profile-detail";
	private final String ITEM_DETAILS_TAG = "item-details";
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected String parseFirstName(Document doc) {
		String mFirstName = "";
		NodeList mResumeNodeList = doc.getElementsByTagName(RESUME_TAG);
		for (int counter = 0; counter < mResumeNodeList.getLength(); counter++) {
			Element elementData = (Element) mResumeNodeList.item(counter);
			mFirstName = elementData.getAttribute(FIRST_NAME_TAG);
		}
		return mFirstName;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected String parseMiddleName(Document doc) {
		String mFirstName = "";
		NodeList mResumeNodeList = doc.getElementsByTagName(RESUME_TAG);
		for (int counter = 0; counter < mResumeNodeList.getLength(); counter++) {
			Element elementData = (Element) mResumeNodeList.item(counter);
			mFirstName = elementData.getAttribute(MIDDLE_NAME_TAG);
		}
		return mFirstName;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected String parseLastName(Document doc) {
		String mFirstName = "";
		NodeList mResumeNodeList = doc.getElementsByTagName(RESUME_TAG);
		for (int counter = 0; counter < mResumeNodeList.getLength(); counter++) {
			Element elementData = (Element) mResumeNodeList.item(counter);
			mFirstName = elementData.getAttribute(LAST_NAME_TAG);
		}
		return mFirstName;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected ArrayList<InterestsDetailsObject> parseInterestData(Document doc) {
		ArrayList<InterestsDetailsObject> mInterestData = new ArrayList<InterestsDetailsObject>(0);
		NodeList mInterestNodeList = doc.getElementsByTagName(INTERESTS_DETAILS_TAG);
		for (int counter = 0; counter < mInterestNodeList.getLength(); counter++) {
			Element data = (Element) mInterestNodeList.item(counter);
			mInterestData.add(new InterestsDetailsObject(data.getAttribute(NAME_TAG), getElementValue(data)));
		}
		return mInterestData;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected ArrayList<WorkExpDetialsObject> parseWorkExpDetails(Document doc) {
		ArrayList<WorkExpDetialsObject> mWorkExpData = new ArrayList<WorkExpDetialsObject>(0);
		NodeList mWorkExpNodeList = doc.getElementsByTagName(WORK_DETAILS_TAG);
		for (int counter = 0; counter < mWorkExpNodeList.getLength(); counter++) {
			Element data = (Element) mWorkExpNodeList.item(counter);
			mWorkExpData.add(new WorkExpDetialsObject(data.getAttribute(NAME_TAG), 
					data.getAttribute(PERIOD_TAG), getElementValue(data)));
		}
		return mWorkExpData;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected ContactDetailsObject parseContactDetails(Document doc) {
		NodeList mContactNodeList = doc.getElementsByTagName(ADDRESS_TAG);
		String address = "";
		for(int counter = 0; counter < mContactNodeList.getLength(); counter++) {
			address = getElementValue(mContactNodeList.item(counter));
		}
		NodeList mPhoneNodeList = doc.getElementsByTagName(PHONE_TAG);
		String[] phone = new String[mPhoneNodeList.getLength()];
		for (int counter = 0; counter < mPhoneNodeList.getLength(); counter++) {
			phone[counter] = getElementValue(mPhoneNodeList.item(counter));
		}
		NodeList mEmailAddressNodeList = doc.getElementsByTagName(MAIL_TAG);
		String[] mail = new String[mEmailAddressNodeList.getLength()];
		for (int counter = 0; counter < mEmailAddressNodeList.getLength(); counter++) {
			mail[counter] = getElementValue(mEmailAddressNodeList.item(counter));
		}
		
		return new ContactDetailsObject(address, phone, mail);
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected ArrayList<ProjectsObject> parseProjectsDetials(Document doc) {
		ArrayList<ProjectsObject> mProjectsData = new ArrayList<ProjectsObject>(0);
		NodeList mCompanyNodeList = doc.getElementsByTagName(COMPANY_TAG);
		for (int counter = 0; counter < mCompanyNodeList.getLength(); counter++) {
			Element data = (Element) mCompanyNodeList.item(counter);
			NodeList mDetialsNodeList = data.getElementsByTagName(PROJECT_DETAILS_TAG);
			ArrayList<ProjectsDetailsObject> mDetails = new ArrayList<ProjectsDetailsObject>(0);
			for(int counter_2 = 0; counter_2 < mDetialsNodeList.getLength(); counter_2++) {
				Element details_data = (Element) mDetialsNodeList.item(counter_2);
				mDetails.add(new ProjectsDetailsObject(details_data.getAttribute(NAME_TAG), getElementValue(details_data)));
			}
			mProjectsData.add(new ProjectsObject(data.getAttribute(NAME_TAG), mDetails));
		}
		return mProjectsData;
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	protected ArrayList<ProfileDetialsObject> parseProfileDetials(Document doc) {
		ArrayList<ProfileDetialsObject> mData = new ArrayList<ProfileDetialsObject>(0);		
		NodeList mProfileNodeList = doc.getElementsByTagName(PROFILE_DETAILS_TAG);
		for (int counter = 0; counter < mProfileNodeList.getLength(); counter++) {
			Element data = (Element) mProfileNodeList.item(counter);
			ArrayList<String> mDetailsData = new ArrayList<String>(0);
			NodeList mAchivementNodeList = data.getElementsByTagName(ITEM_DETAILS_TAG);
			for(int counter_2 = 0; counter_2 < mAchivementNodeList.getLength(); counter_2++) {
				mDetailsData.add(getElementValue(mAchivementNodeList.item(counter_2)));
			}
			mData.add(new ProfileDetialsObject(data.getAttribute(NAME_TAG), mDetailsData));
		}		
		return mData;
	}
	
	/**
	 * 
	 * @param elem
	 * @return
	 */
	private final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}
