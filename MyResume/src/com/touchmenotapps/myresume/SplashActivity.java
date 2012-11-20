package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.variables.AppGlobalVariables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	private TextView middle_name_text;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        
        middle_name_text = (TextView) findViewById(R.id.middle_name_text);
        if(middle_name_text.getText().toString().trim().length() > 0)
        	middle_name_text.setVisibility(TextView.VISIBLE);
        
        /** Run thread to change screen once the display time is over */
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent MyResumeIntent = new Intent(SplashActivity.this,MainActivity.class);
				SplashActivity.this.startActivity(MyResumeIntent);
				SplashActivity.this.finish();  
            }
		}, AppGlobalVariables.SPLASH_TIMEOUT);
    }
}
