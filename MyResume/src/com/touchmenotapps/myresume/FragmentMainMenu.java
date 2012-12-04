package com.touchmenotapps.myresume;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FragmentMainMenu extends Fragment {
	
	private Button mProfileBtn, mWorkBtn, mProjectsBtn, mInterestsBtn, mContactBtn;
	private TextView mNameFirstText, mNameMiddleText, mNameLastText;
	private Callbacks mCallbacks = sCallbacks;
	
	public interface Callbacks {
		public void onProfileSelected();
		public void onWorkSelected();
		public void onProjectSelected();
		public void onInterestSelected();
		public void onContactSelected();
	}
	
	private static Callbacks sCallbacks = new Callbacks() {

		public void onProfileSelected() { }

		public void onWorkSelected() { }

		public void onProjectSelected() { }

		public void onInterestSelected() { }

		public void onContactSelected() { }
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mHolderView = inflater.inflate(R.layout.fragment_main, null);
		mProfileBtn = (Button) mHolderView.findViewById(R.id.profile_btn);
        mWorkBtn = (Button) mHolderView.findViewById(R.id.work_btn);
        mProjectsBtn = (Button) mHolderView.findViewById(R.id.projects_btn);
        mInterestsBtn = (Button) mHolderView.findViewById(R.id.interests_btn);
        mContactBtn = (Button) mHolderView.findViewById(R.id.contact_btn);
        
        mNameFirstText = (TextView) mHolderView.findViewById(R.id.main_first_name_text);
        mNameMiddleText = (TextView) mHolderView.findViewById(R.id.main_middle_name_text);
        mNameLastText = (TextView) mHolderView.findViewById(R.id.main_last_name_text);
        
        if(SplashActivity.firstName.length() > 0) {
        	mNameFirstText.setText(SplashActivity.firstName);
        	mNameFirstText.setVisibility(TextView.VISIBLE);
        }		        
        if(SplashActivity.middleName.length() > 0) {
        	mNameMiddleText.setText(SplashActivity.middleName);
        	mNameMiddleText.setVisibility(TextView.VISIBLE);
        }		        
        if(SplashActivity.lastName.length() > 0) {
        	mNameLastText.setText(SplashActivity.lastName);
        	mNameLastText.setVisibility(TextView.VISIBLE);
        }
                
        mProfileBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mCallbacks.onProfileSelected();
			}
		});
        
        mWorkBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mCallbacks.onWorkSelected();
			}
		});
        
        mProjectsBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mCallbacks.onProjectSelected();
			}
		});
        
        mInterestsBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mCallbacks.onInterestSelected();
			}
		});
        
        mContactBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mCallbacks.onContactSelected();
			}
		});
		return mHolderView;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		// Reset the active callbacks interface to the fragment implementation.
		mCallbacks = sCallbacks;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}
		mCallbacks = (Callbacks) activity;
	}
}