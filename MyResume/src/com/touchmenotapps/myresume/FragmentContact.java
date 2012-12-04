package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.variables.ContactDetailsObject;
import com.touchmenotapps.myresume.variables.ResumeDataObject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentContact extends Fragment {
	
	private TextView mAddressText;
	private ImageButton mCallBtn, mMailBtn;
	private ConnectivityManager mConnectivityManager;
	private boolean isCallMenu = true;
	private ContactDetailsObject mConatctsData;
	private ResumeDataObject resumeData;
	private ProgressDialog mProgressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mHolderView = inflater.inflate(R.layout.fragment_contact, null);
		resumeData = new ResumeDataObject(getActivity());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.loading));
        
        mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        mCallBtn = (ImageButton) mHolderView.findViewById(R.id.contact_call_btn);
        mMailBtn = (ImageButton) mHolderView.findViewById(R.id.contact_mail_btn);      
        mAddressText = (TextView) mHolderView.findViewById(R.id.address_text);
        registerForContextMenu(mCallBtn);
        registerForContextMenu(mMailBtn);
        
        mCallBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				isCallMenu = true;
				getActivity().openContextMenu(v);
			}
		});
        
        mMailBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				isCallMenu = false;
				getActivity().openContextMenu(v);
			}
		});
        
        mProgressDialog.show();
        new LoadData().execute(new Void[0]);
		return mHolderView;
	}
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	if(isCallMenu) {
    		menu.setHeaderIcon(R.drawable.ic_call);
    		menu.setHeaderTitle(R.string.call_me);
    		for(int count = 0; count < mConatctsData.getPhoneNumbers().length; count++) 
    			menu.add(0, v.getId(), 0, mConatctsData.getPhoneNumbers()[count]);
    	} else {
    		menu.setHeaderIcon(R.drawable.ic_mail);
    		menu.setHeaderTitle(R.string.mail_me);
    		for(int count = 0; count < mConatctsData.getEmailAddress().length; count++) 
    			menu.add(0, v.getId(), 0, mConatctsData.getEmailAddress()[count]);
    	}
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	if(isCallMenu) {
    		try {
		        startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+item.getTitle().toString().trim())));
		    } catch (ActivityNotFoundException e) {
		    	Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		    }
    	} else {
    		if (mConnectivityManager.getActiveNetworkInfo() == null) 
    			Toast.makeText(getActivity(), R.string.error_interenet_connection, Toast.LENGTH_SHORT).show();
			else {
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{item.getTitle().toString().trim()});
				startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_mail)));
			}
    	}
    	return true;
    }
    
    /**
     * @usage new LoadData().execute(new Void[0]);
     * @author Arindam Nath
     */
    private class LoadData extends AsyncTask<Void, Void, String> {
    	    	
		@Override
		protected String doInBackground(Void... arg0) {
			String loaderResponse = "false";
			try {
				mConatctsData = resumeData.getContactsData();
				loaderResponse = "true";
			} catch (Exception e) {
				e.printStackTrace();
			}
			return loaderResponse;
		}

		protected void onPostExecute(String result) {
			if(result.equals("true")) {
				mAddressText.setText(mConatctsData.getAddress());
				mProgressDialog.dismiss();
			} else 
				Toast.makeText(getActivity(), R.string.error_parsing, Toast.LENGTH_SHORT).show();
		}
    }
}
