package com.touchmenotapps.myresume.functions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.touchmenotapps.myresume.R;
import com.touchmenotapps.myresume.variables.AppProjectsObject;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

public class AppCommonFunctions {
	
	private Context context;
	
	public AppCommonFunctions(Context _context) {
		this.context = _context;
	}
	
	public int getScreenDPI() {
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 1, context.getResources().getDisplayMetrics());
		return dip;
	}
	
	public String readFileData() throws Exception {
		InputStream in=context.getResources().openRawResource(R.xml.projects_data);
		InputStreamReader is = new InputStreamReader(in);
		StringBuilder sb=new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();
		while(read != null) {
		    //System.out.println(read);
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
	
	private Document getProjectsData() {
		String data = null;
		Document doc = null;
		try {
			data = readFileData();
			if (data != null) {
				doc = getDomElement(data);
				Log.i("Test", data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public ArrayList<AppProjectsObject> getProjectDetails() {
		ArrayList<AppProjectsObject> mDetails = new ArrayList<AppProjectsObject>(0);
		getProjectsData();
		return mDetails;
	}
}
