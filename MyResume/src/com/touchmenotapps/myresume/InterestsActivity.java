package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.widgets.AppTextView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
public class InterestsActivity extends Activity {

	private final int NO_IMAGE = 0;
	private LinearLayout mInterestsHolder;
	private Animation shake;
	
	private String[] mInterestsWithLink, mInterestsLink, mInterestsWithoutLink;
	private int[] mImages;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_interests);
        
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        mInterestsWithLink = getResources().getStringArray(R.array.interests_name_with_links);
        mInterestsLink = getResources().getStringArray(R.array.interests_link);
        mInterestsWithoutLink = getResources().getStringArray(R.array.interests_name_without_links);
        //Pass custom images for items with links.
        mImages = new int[] {R.drawable.ic_camera, R.drawable.ic_blog, R.drawable.ic_play};
        
        mInterestsHolder = (LinearLayout) findViewById(R.id.interests_container_view);
        
        for(int count = 0; count < mInterestsWithLink.length; count++) 
        	mInterestsHolder.addView(addItem(true, mInterestsWithLink[count], mInterestsLink[count], mImages[count]));
        
        for(int count = 0; count < mInterestsWithoutLink.length; count++) 
        	mInterestsHolder.addView(addItem(false, mInterestsWithoutLink[count], "", NO_IMAGE));
    }
    
   /**
    * 
    * @param withLink
    * @param name
    * @param link
    * @param mImage
    * @return
    */
    private AppTextView addItem(final boolean withLink, final String name, final String link, final int mImage) {
    	final AppTextView mTextView= new AppTextView(this);
    	LayoutParams mViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	mViewParams.setMargins(0, 5, -5, 0);
    	mTextView.setLayoutParams(mViewParams);
    	mTextView.setText(name);
    	mTextView.setGravity(Gravity.LEFT);
    	mTextView.setTextColor(Color.WHITE);
    	mTextView.setTextSize(32);
    	mTextView.setPadding(3, 15, 0, 0);
    	try {
    		mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, mImage, 0);
    	} catch (Exception e) {
    		Log.w(this.getClass().getName(), "Drawable not found for : " + name);
    	}
    	if(withLink) {
	    	mTextView.setBackgroundResource(R.drawable.button_config);
	    	mTextView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(link.trim().length() > 0) 
						openWebPage(link, mTextView);
					else {
						mTextView.startAnimation(shake);
						Toast.makeText(getBaseContext(), R.string.error_redirection_link, Toast.LENGTH_SHORT).show();
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
    	ConnectivityManager cm = (ConnectivityManager) InterestsActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() == null) {
			selectedView.startAnimation(shake);
			Toast.makeText(this, R.string.error_interenet_connection, Toast.LENGTH_SHORT).show();
		} else  
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageURL)));
    }
}
