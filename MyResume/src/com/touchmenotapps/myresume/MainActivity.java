package com.touchmenotapps.myresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button profile_btn, work_btn, projects_btn, interests_btn, contact_btn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        profile_btn = (Button) findViewById(R.id.profile_btn);
        work_btn = (Button) findViewById(R.id.work_btn);
        projects_btn = (Button) findViewById(R.id.projects_btn);
        interests_btn = (Button) findViewById(R.id.interests_btn);
        contact_btn = (Button) findViewById(R.id.contact_btn);
        
        profile_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ProfileActivity.class));
			}
		});
        
        work_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, WorkActivity.class));
			}
		});
        
        projects_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ProjectsActivity.class));
			}
		});
        
        interests_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, InterestsActivity.class));
			}
		});
        
        contact_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ContactActivity.class));
			}
		});
    }
}