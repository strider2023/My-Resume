package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.functions.AppCommonFunctions;
import com.touchmenotapps.myresume.preferences.AppPreferences;
import com.touchmenotapps.myresume.widgets.AppTextView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class ProfileActivity extends Activity {
	
	private final int MAX_COUNT = 4;
	private final int SWIPE_MIN_DISTANCE = 120;
    private final int SWIPE_MAX_OFF_PATH = 250;
    private final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;

	private TextSwitcher mHeaderSwitcher, mBodySwitcher;
	private ScrollView mScrollview;
	private RelativeLayout mInstructionBtn;
	
	private Animation shake;
	private AppCommonFunctions functions;
	private AppPreferences pref;
	private int count = 0;
	private String[] mProfileHeaders, mProfileBody;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        functions = new AppCommonFunctions(this);
        pref = new AppPreferences(this);
        
        mHeaderSwitcher = (TextSwitcher) findViewById(R.id.profile_subheader_text);
        mBodySwitcher = (TextSwitcher) findViewById(R.id.profile_body_text);
        mScrollview = (ScrollView) findViewById(R.id.profile_scrollview);
        mInstructionBtn = (RelativeLayout) findViewById(R.id.instrunstions_layout);
        
        if(!pref.getProfileFirstRun()){
        	mInstructionBtn.setVisibility(RelativeLayout.GONE);
        }
        
        mInstructionBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pref.setProfileRunned();
				mInstructionBtn.setVisibility(RelativeLayout.GONE);
			}
		});
        
        mProfileHeaders = getResources().getStringArray(R.array.profile_headers);
        mProfileBody = getResources().getStringArray(R.array.profile_descriptions); 
        
        mHeaderSwitcher.setFactory(new ViewFactory() {
			public View makeView() {
				/** Set up the custom auto scrolling text view class for lengthy album names */
				AppTextView textSwitcher_text = new AppTextView(ProfileActivity.this);
		        textSwitcher_text.setTextColor(Color.argb(225, 245, 242, 11));
		        textSwitcher_text.setTextSize(32 * functions.getScreenDPI());
		        textSwitcher_text.setSingleLine(true);
		        textSwitcher_text.setEllipsize(TruncateAt.MARQUEE);
		        textSwitcher_text.setMarqueeRepeatLimit(-1);
		        textSwitcher_text.setHorizontallyScrolling(true);
		        return textSwitcher_text;
			}
		});
        
        mBodySwitcher.setFactory(new ViewFactory() {
			public View makeView() {
				/** Set up the custom auto scrolling text view class for lengthy album names */
				AppTextView textSwitcher_text = new AppTextView(ProfileActivity.this);
		        textSwitcher_text.setTextColor(Color.argb(225, 225, 225, 225));
		        textSwitcher_text.setTextSize(18 * functions.getScreenDPI());
		        return textSwitcher_text;
			}
		});
        
        mHeaderSwitcher.setText(mProfileHeaders[count]);
        mBodySwitcher.setText(mProfileBody[count]);
        
        // Gesture detection
        gestureDetector = new GestureDetector(new OnGestureListener() {
			
			public boolean onSingleTapUp(MotionEvent e) {return false;}
			
			public void onShowPress(MotionEvent e) {}
			
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {return false;}
			
			public void onLongPress(MotionEvent e) {}
			
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				try {
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                /** Left swipe */
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){ 
	                	if(count < (MAX_COUNT-1))
	                		nextDetail();
	                	else {
	                		mHeaderSwitcher.startAnimation(shake);
	                		mBodySwitcher.startAnimation(shake);
	                	}
	                /** Right Swipe */
	                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                	if(count != 0)
	                		previousDetail();
	                	else {
	                		mHeaderSwitcher.startAnimation(shake);
	                		mBodySwitcher.startAnimation(shake);
	                	}
	                }
	            } catch (Exception e) {}

				return false;
			}
			
			public boolean onDown(MotionEvent e) {return false;}
		});
        
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        mScrollview.setOnTouchListener(gestureListener);
    }
    
    private void nextDetail(){
    	count++;
    	
    	mHeaderSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
    	mHeaderSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
    	mBodySwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
    	mBodySwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
    	
    	mHeaderSwitcher.setText(mProfileHeaders[count]);
    	mBodySwitcher.setText(mProfileBody[count]);
    	mScrollview.scrollTo(0, 0);
    }
    
    private void previousDetail() {
    	count--;
    	
    	mHeaderSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
    	mHeaderSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
    	mBodySwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
    	mBodySwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
    	
    	mHeaderSwitcher.setText(mProfileHeaders[count]);
    	mBodySwitcher.setText(mProfileBody[count]);
    	mScrollview.scrollTo(0, 0);
    }
}
