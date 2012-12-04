package com.touchmenotapps.myresume;

import java.util.ArrayList;

import com.touchmenotapps.myresume.variables.InterestsDetailsObject;
import com.touchmenotapps.myresume.variables.ResumeDataObject;
import com.touchmenotapps.myresume.widgets.AppTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * http://www.youtube.com/watch?v=qxxM7X4Zpr8&feature=plcp&context=C3b2e024UDOEgsToPDskIKlaDd4I-hyl4W-2Hs1O7Z
 * @author Arindam Nath
 *
 */
public class FragmentInterests extends Fragment {

	private LinearLayout mInterestsHolder;
	private Animation shake;
	private ArrayList<InterestsDetailsObject> mInterestsData = new ArrayList<InterestsDetailsObject>(0);
	private ResumeDataObject resumeData;
	private ProgressDialog mProgressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mHolderView = inflater.inflate(R.layout.fragment_interests, null);
		shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        resumeData = new ResumeDataObject(getActivity());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.loading));
        
        mInterestsHolder = (LinearLayout) mHolderView.findViewById(R.id.interests_container_view);
        mProgressDialog.show();
        new LoadData().execute(new Void[0]);
		return mHolderView;
	}
    
   /**
    * 
    * @param withLink
    * @param name
    * @param link
    * @param mImage
    * @return
    */
    private AppTextView addItem(final InterestsDetailsObject data) {
    	final AppTextView mTextView= new AppTextView(getActivity());
    	LayoutParams mViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	mViewParams.setMargins(0, 5, -5, 0);
    	mTextView.setLayoutParams(mViewParams);
    	mTextView.setText(data.getInterestName());
    	mTextView.setGravity(Gravity.LEFT);
    	mTextView.setTextColor(Color.WHITE);
    	mTextView.setTextSize(32);
    	mTextView.setPadding(3, 15, 0, 0);
    	/*try {
    		mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, mImage, 0);
    	} catch (Exception e) {
    		Log.w(this.getClass().getName(), "Drawable not found for : " + name);
    	}*/
    	if(data.getInterestLink().length() > 0) {
	    	mTextView.setBackgroundResource(R.drawable.button_config);
	    	mTextView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(data.getInterestLink().trim().length() > 0) 
						openWebPage(data.getInterestLink(), mTextView);
					else {
						mTextView.startAnimation(shake);
						Toast.makeText(getActivity(), R.string.error_redirection_link, Toast.LENGTH_SHORT).show();
					}
				}
			});
    	}
    	return mTextView;
    }
    
    /**
     * 
     * @param pageURL
     * @param selectedView
     */
    private void openWebPage(String pageURL, View selectedView) {
    	ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() == null) {
			selectedView.startAnimation(shake);
			Toast.makeText(getActivity(), R.string.error_interenet_connection, Toast.LENGTH_SHORT).show();
		} else  
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageURL)));
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
				mInterestsData = resumeData.getInterestsData();
				loaderResponse = "true";
			} catch (Exception e) {
				e.printStackTrace();
			}
			return loaderResponse;
		}

		protected void onPostExecute(String result) {
			if(result.equals("true")) {
				for(int count = 0; count < mInterestsData.size(); count++) 
		        	mInterestsHolder.addView(addItem(mInterestsData.get(count)));
				mProgressDialog.dismiss();
			} else 
				Toast.makeText(getActivity(), R.string.error_parsing, Toast.LENGTH_SHORT).show();
		}
    }
}
