package com.touchmenotapps.myresume.variables;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import android.content.Context;

public class ResumeDataObject {

	private final String LOCAL_DATA_SOURCE = "projects_data.xml";
	
	private String mFirstName = null;
	private String mMiddleName = null;
	private String mLastName = null;
	private ArrayList<ProfileDetialsObject> mProfile = null;
	private ArrayList<WorkExpDetialsObject> mWorkDetails= null;
	private ArrayList<ProjectsObject> mProjectsData = null;
	private ArrayList<InterestsDetailsObject> mInterestsData = null;
	private ContactDetailsObject mContactsData = null;
	private Context mContext;
	private DataParsingFunctions mPraserFunction;
	
	/**
	 * @param mFirstName
	 * @param mMiddleName
	 * @param mLastName
	 * @param mProfile
	 * @param mWorkDetails
	 * @param mProjectsData
	 * @param mInterestsData
	 * @param mContactsData
	 */
	public ResumeDataObject(Context context) {
		this.mContext = context;
		this.mPraserFunction = new DataParsingFunctions();
	}
	
	public String readFileData() throws Exception {
		InputStream in=mContext.getAssets().open(LOCAL_DATA_SOURCE);
		InputStreamReader is = new InputStreamReader(in);
		StringBuilder sb=new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();
		while(read != null) {
		    sb.append(read);
		    read =br.readLine();
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	private Document getDomElement(String xml) throws Exception {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		doc = db.parse(is);
		return doc;
	}

	/**
	 * @return the mFirstName
	 */
	public String getFirstName() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mFirstName = mPraserFunction.parseFirstName(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mFirstName;
	}

	/**
	 * @return the mMiddleName
	 */
	public String getMiddleName() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mMiddleName = mPraserFunction.parseMiddleName(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mMiddleName;
	}

	/**
	 * @return the mLastName
	 */
	public String getLastName() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mLastName = mPraserFunction.parseLastName(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mLastName;
	}

	/**
	 * @return the mProfile
	 */
	public ArrayList<ProfileDetialsObject> getProfile() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mProfile = mPraserFunction.parseProfileDetials(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mProfile;
	}

	/**
	 * @return the mWorkDetails
	 */
	public ArrayList<WorkExpDetialsObject> getWorkDetails() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mWorkDetails = mPraserFunction.parseWorkExpDetails(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mWorkDetails;
	}

	/**
	 * @return the mProjectsData
	 */
	public ArrayList<ProjectsObject> getProjectsData() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mProjectsData = mPraserFunction.parseProjectsDetials(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mProjectsData;
	}

	/**
	 * @return the mInterestsData
	 */
	public ArrayList<InterestsDetailsObject> getInterestsData() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mInterestsData = mPraserFunction.parseInterestData(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mInterestsData;
	}

	/**
	 * @return the mContactsData
	 */
	public ContactDetailsObject getContactsData() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				mContactsData = mPraserFunction.parseContactDetails(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mContactsData;
	}
}
