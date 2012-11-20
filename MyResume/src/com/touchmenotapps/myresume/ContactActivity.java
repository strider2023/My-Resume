package com.touchmenotapps.myresume;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class ContactActivity extends Activity {
	
	private ImageButton contact_call_btn, contact_mail_btn;
	private ConnectivityManager mConnectivityManager;
	private String[] mPhoneNumbers, mEmailDetials;
	private boolean isCallMenu = true;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Check version and set title bar likewise. **/
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			setTheme(android.R.style.Theme_Black_NoTitleBar);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    } else {
	    	setTheme(android.R.style.Theme_Holo_NoActionBar);
	    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
        setContentView(R.layout.layout_contact);
        
        mPhoneNumbers = getResources().getStringArray(R.array.contact_phone);
        mEmailDetials = getResources().getStringArray(R.array.contact_mail);
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        contact_call_btn = (ImageButton) findViewById(R.id.contact_call_btn);
        contact_mail_btn = (ImageButton) findViewById(R.id.contact_mail_btn);      
        registerForContextMenu(contact_call_btn);
        registerForContextMenu(contact_mail_btn);
        
        contact_call_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				isCallMenu = true;
				openContextMenu(v);
			}
		});
        
        contact_mail_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				isCallMenu = false;
				openContextMenu(v);
			}
		});
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	if(isCallMenu) {
    		menu.setHeaderIcon(R.drawable.ic_call);
    		menu.setHeaderTitle(R.string.call_me);
    		for(int count = 0; count < mPhoneNumbers.length; count++) 
    			menu.add(0, v.getId(), 0, mPhoneNumbers[count]);
    	} else {
    		menu.setHeaderIcon(R.drawable.ic_mail);
    		menu.setHeaderTitle(R.string.mail_me);
    		for(int count = 0; count < mEmailDetials.length; count++) 
    			menu.add(0, v.getId(), 0, mEmailDetials[count]);
    	}
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	if(isCallMenu) {
    		try {
		        startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+item.getTitle().toString().trim())));
		    } catch (ActivityNotFoundException e) {
		    	Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		    }
    	} else {
    		if (mConnectivityManager.getActiveNetworkInfo() == null) 
    			Toast.makeText(this, R.string.error_interenet_connection, Toast.LENGTH_SHORT).show();
			else {
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{item.getTitle().toString().trim()});
				startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_mail)));
			}
    	}
    	return true;
    }
}
