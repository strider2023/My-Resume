package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.variables.AppGlobalVariables;
import com.touchmenotapps.myresume.variables.ResumeDataObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	private TextView mNameFirstText, mNameMiddleText, mNameLastText;
	public static String firstName, middleName, lastName;
	private ResumeDataObject resumeData;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        resumeData = new ResumeDataObject(this);
        
        mNameFirstText = (TextView) findViewById(R.id.first_name_text);
        mNameMiddleText = (TextView) findViewById(R.id.middle_name_text);
        mNameLastText = (TextView) findViewById(R.id.last_name_text);
        
        try {
			firstName = resumeData.getFirstName().trim();
			middleName = resumeData.getMiddleName().trim();
			lastName = resumeData.getLastName().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if(firstName.length() > 0) {
        	mNameFirstText.setText(firstName);
        	mNameFirstText.setVisibility(TextView.VISIBLE);
        }		        
        if(middleName.length() > 0) {
        	mNameMiddleText.setText(middleName);
        	mNameMiddleText.setVisibility(TextView.VISIBLE);
        }		        
        if(lastName.length() > 0) {
        	mNameLastText.setText(lastName);
        	mNameLastText.setVisibility(TextView.VISIBLE);
        }
        
        /** Run thread to change screen once the display time is over */
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent MyResumeIntent = new Intent(SplashActivity.this, BaseActivity.class);
				SplashActivity.this.startActivity(MyResumeIntent);
				SplashActivity.this.finish();  
            }
		}, AppGlobalVariables.SPLASH_TIMEOUT);    
	}
}
