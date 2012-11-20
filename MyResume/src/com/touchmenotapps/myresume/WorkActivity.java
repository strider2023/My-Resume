package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.functions.AppCommonFunctions;
import com.touchmenotapps.myresume.preferences.AppPreferences;
import com.touchmenotapps.myresume.variables.AppGlobalVariables;
import com.touchmenotapps.myresume.widgets.AppTextView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class WorkActivity extends Activity {
	
	private int MAX_COUNT = 0;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;
	
	private TextSwitcher mCompanyNameSwitcher, mCompanySpanSwitcher, mCompanySummarySwitcher;
	private ScrollView mScrollContainer;
	private RelativeLayout mInstructionBtn;
	private Animation shake;
	
	private int count = 0;
	private String[] mComapnyNames, mWorkSpans, mWorkDescription;
	private AppCommonFunctions functions;
	private AppPreferences pref;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_work_exp);
        
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        functions = new AppCommonFunctions(this);
        pref = new AppPreferences(this);
        
        mComapnyNames = getResources().getStringArray(R.array.company_names);
        mWorkSpans = getResources().getStringArray(R.array.company_spans);
        mWorkDescription = getResources().getStringArray(R.array.company_work_summary);
        MAX_COUNT = mComapnyNames.length;
        
        mCompanyNameSwitcher = (TextSwitcher) findViewById(R.id.work_company_name_text);
        mCompanySpanSwitcher = (TextSwitcher) findViewById(R.id.work_company_span_text);
        mCompanySummarySwitcher = (TextSwitcher) findViewById(R.id.work_company_summary_text);
        mScrollContainer = (ScrollView) findViewById(R.id.work_scrollview);
        mInstructionBtn = (RelativeLayout) findViewById(R.id.instrunstions_layout);
        
        if(!pref.getWorkFirstRun()){
        	mInstructionBtn.setVisibility(RelativeLayout.GONE);
        }
        
        mInstructionBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pref.setWorkRunned();
				mInstructionBtn.setVisibility(RelativeLayout.GONE);
			}
		});
        
        mCompanyNameSwitcher.setFactory(new ViewFactory() {
        	public View makeView() {
				/** Set up the custom auto scrolling text view class for lengthy album names */
				AppTextView textSwitcher_text = new AppTextView(WorkActivity.this);
		        textSwitcher_text.setTextColor(Color.argb(225, 245, 242, 11));
		        textSwitcher_text.setTextSize(32 * functions.getScreenDPI());
		        textSwitcher_text.setSingleLine(true);
		        textSwitcher_text.setEllipsize(TruncateAt.MARQUEE);
		        textSwitcher_text.setMarqueeRepeatLimit(-1);
		        textSwitcher_text.setHorizontallyScrolling(true);
		        return textSwitcher_text;
			}
		});
        
        mCompanySpanSwitcher.setFactory(new ViewFactory() {
        	public View makeView() {
				/** Set up the custom auto scrolling text view class for lengthy album names */
				AppTextView textSwitcher_text = new AppTextView(WorkActivity.this);
		        textSwitcher_text.setTextColor(Color.argb(225, 245, 242, 11));
		        textSwitcher_text.setTextSize(14 * functions.getScreenDPI());
		        textSwitcher_text.setSingleLine(true);
		        textSwitcher_text.setEllipsize(TruncateAt.MARQUEE);
		        textSwitcher_text.setMarqueeRepeatLimit(-1);
		        textSwitcher_text.setHorizontallyScrolling(true);
		        return textSwitcher_text;
			}
		});
        
        mCompanySummarySwitcher.setFactory(new ViewFactory() {
        	public View makeView() {
				/** Set up the custom auto scrolling text view class for lengthy album names */
        		AppTextView textSwitcher_text = new AppTextView(WorkActivity.this);
		        textSwitcher_text.setTextColor(Color.argb(225, 225, 225, 225));
		        textSwitcher_text.setTextSize(18 * functions.getScreenDPI());
		        return textSwitcher_text;
			}
		});
        
        mCompanyNameSwitcher.setText(mComapnyNames[count]);
        mCompanySpanSwitcher.setText(mWorkSpans[count]);
        mCompanySummarySwitcher.setText(mWorkDescription[count]);
                
        // Gesture detection
        gestureDetector = new GestureDetector(new OnGestureListener() {
			
			public boolean onSingleTapUp(MotionEvent e) {return false;}
			
			public void onShowPress(MotionEvent e) {}
			
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {return false;}
			
			public void onLongPress(MotionEvent e) {}
			
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				try {
	                if (Math.abs(e1.getY() - e2.getY()) > AppGlobalVariables.SWIPE_MAX_OFF_PATH)
	                    return false;
	                /** Left swipe */
	                if(e1.getX() - e2.getX() > AppGlobalVariables.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > AppGlobalVariables.SWIPE_THRESHOLD_VELOCITY){ 
	                	if(count < (MAX_COUNT-1))
	                		nextCompany();
	                	else {
	                		mCompanyNameSwitcher.startAnimation(shake);
	                		mCompanySpanSwitcher.startAnimation(shake);
	                		mCompanySummarySwitcher.startAnimation(shake);
	                	}
	                /** Right Swipe */
	                } else if (e2.getX() - e1.getX() > AppGlobalVariables.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > AppGlobalVariables.SWIPE_THRESHOLD_VELOCITY) {
	                	if(count != 0)
	                		previousComapny();
	                	else {
	                		mCompanyNameSwitcher.startAnimation(shake);
	                		mCompanySpanSwitcher.startAnimation(shake);
	                		mCompanySummarySwitcher.startAnimation(shake);
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
        
        mScrollContainer.setOnTouchListener(gestureListener);
    }
    
    private void nextCompany() {
    	count++;
    	
    	mCompanyNameSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
    	mCompanyNameSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
    	mCompanySpanSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
    	mCompanySpanSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
    	mCompanySummarySwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
    	mCompanySummarySwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
    	
    	mCompanyNameSwitcher.setText(mComapnyNames[count]);
        mCompanySpanSwitcher.setText(mWorkSpans[count]);
        mCompanySummarySwitcher.setText(mWorkDescription[count]);
        mScrollContainer.scrollTo(0, 0);
    }
    
    private void previousComapny() {
    	count--;
    	
    	mCompanyNameSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
    	mCompanyNameSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
    	mCompanySpanSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
    	mCompanySpanSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
    	mCompanySummarySwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
    	mCompanySummarySwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
    	
    	mCompanyNameSwitcher.setText(mComapnyNames[count]);
        mCompanySpanSwitcher.setText(mWorkSpans[count]);
        mCompanySummarySwitcher.setText(mWorkDescription[count]);
        mScrollContainer.scrollTo(0, 0);
    }
}
