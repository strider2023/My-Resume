package com.touchmenotapps.myresume;

import com.touchmenotapps.myresume.adapters.AppListAdapter;
import com.touchmenotapps.myresume.functions.AppCommonFunctions;
import com.touchmenotapps.myresume.preferences.AppPreferences;
import com.touchmenotapps.myresume.variables.AppGlobalVariables;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class ProjectsActivity extends Activity {
	
	private int MAX_COUNT = 0;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;

	private ListView projects_list;
	private AppListAdapter adapter;
	private TextSwitcher comapny_switcher;
	private RelativeLayout instruction_btn;
	
	private Animation shake;
	private AppCommonFunctions functions;
	private AppPreferences pref;
	private int count = 0;
	private String[] mComapnyNames;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_projects);
        
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        adapter = new AppListAdapter(this);
        functions = new AppCommonFunctions(this);
        pref = new AppPreferences(this);
        
        mComapnyNames = getResources().getStringArray(R.array.company_names);
        MAX_COUNT = mComapnyNames.length;
        
        projects_list = (ListView) findViewById(R.id.projects_listview);
        comapny_switcher = (TextSwitcher) findViewById(R.id.projects_company_name_text);
        instruction_btn = (RelativeLayout) findViewById(R.id.instrunstions_layout);
        
        projects_list.setVerticalScrollBarEnabled(false);
        projects_list.setVerticalFadingEdgeEnabled(false);
        projects_list.setSelector(this.getResources().getDrawable(R.drawable.transparent_shape));
        
        if(!pref.getProjectsFirstRun()){
        	instruction_btn.setVisibility(RelativeLayout.GONE);
        }
        
        instruction_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pref.setProjectsRunned();
				instruction_btn.setVisibility(RelativeLayout.GONE);
			}
		});
        
        comapny_switcher.setFactory(new ViewFactory() {
			public View makeView() {
				/** Set up the custom auto scrolling text view class for lengthy album names */
				AppTextView textSwitcher_text = new AppTextView(ProjectsActivity.this);
		        textSwitcher_text.setTextColor(Color.argb(225, 245, 242, 11));
		        textSwitcher_text.setTextSize(32 * functions.getScreenDPI());
		        textSwitcher_text.setSingleLine(true);
		        textSwitcher_text.setEllipsize(TruncateAt.MARQUEE);
		        textSwitcher_text.setMarqueeRepeatLimit(-1);
		        textSwitcher_text.setHorizontallyScrolling(true);
		        return textSwitcher_text;
			}
		});
        
        comapny_switcher.setText(mComapnyNames[count]);
        adapter.SetListData(getProjectsName(count), getProjectsDetails(count));
        projects_list.setAdapter(adapter);
        
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
	                		comapny_switcher.startAnimation(shake);
	                		projects_list.startAnimation(shake);
	                	}
	                /** Right Swipe */
	                } else if (e2.getX() - e1.getX() > AppGlobalVariables.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > AppGlobalVariables.SWIPE_THRESHOLD_VELOCITY) {
	                	if(count != 0)
	                		previousComapny();
	                	else {
	                		comapny_switcher.startAnimation(shake);
	                		projects_list.startAnimation(shake);
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
        
        projects_list.setOnTouchListener(gestureListener);
    }
    
    private void nextCompany(){
    	count++;
    	
    	comapny_switcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
    	comapny_switcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
    	projects_list.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rail_in));
    	
    	comapny_switcher.setText(mComapnyNames[count]);
    	
    	adapter.notifyDataSetChanged();
    	adapter.SetListData(getProjectsName(count), getProjectsDetails(count));
        projects_list.setAdapter(adapter);
    }
    
    private void previousComapny() {
    	count--;
    	
    	comapny_switcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
    	comapny_switcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
    	projects_list.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rail_out));
    	
    	comapny_switcher.setText(mComapnyNames[count]);
    	
    	adapter.notifyDataSetChanged();
    	adapter.SetListData(getProjectsName(count), getProjectsDetails(count));
        projects_list.setAdapter(adapter);
    }
    
    private String[] getProjectsName(int num) {
    	String[] projects = null;
    	switch(num) {
	    	case 0:
	    		projects = new String[]{"SHOV Reader", "Mobi QUB"};
	    		break;
    		case 1:
    			projects = new String[]{"Xpense Manager", "Vehicle Value Evaluator", "Cobra", "POCs"};
    			break;
    		case 2:
    			projects = new String[]{"VOAPK (Enterprise Application)", 
    					"HanDy Calc", 
    					"Accelerando", 
    					"eFireTrack", 
    					"Blue Penguin",
    					"Comic Book Application",
    					"Personal Dietitian",
    					"Zork for Kindle",
    					"Discover India",
    					"Quick Mail"};
    			break;
    		case 3:
    			projects = new String[]{"Double Hold’em", "Projects from ABCMouse.com and Dr. Laura’s Advice"};
    			break;
    		case 4:
    			projects = new String[]{"It’s Just a Thought" , "R&D on input devices for modern Handsets"};
    			break;
    		case 5:
    			projects = new String[]{"Proteus", "Point Me", "Cocina Mexicana"};
    			break;
    		case 6:
    			projects = new String[]{"Memorabilia", "Radial Menu", "Graph3r"};
    			break;
    	}
    	return projects;
    }
    
    private String[] getProjectsDetails(int num) {
    	String[] projects = null;
    	switch(num) {
	    	case 0:
	    		projects = new String[]{"SHOV Reader is an ebook reader which displays html data content along with the native OS UI to make it more user interactive. It has options to " +
	    				"play video, view images, bookmarks, search content specific to the chapter.", 
	    				"Mobi QUB helps key decision makers of a company by accessing  important enterprise data, to user with access clearances via there mobile devices."};
	    		break;
    		case 1:
    			projects = new String[]{"Xpense Manager is an expense manager application for android devices. The application is currently available at Android Market.",
    					"An iPhone vehicle value evaluator for used cars. It is available at App Store.",
    					"An education portal where students can log in using active directory credentials and view collage related datas like assignments, events etc.",
    					"Worked on multiple POCs for various international clients, on areas like creating high level user interface and usability implementation for pre-sales demonstrations."};
    			break;
    		case 2:
    			projects = new String[]{"Vopak is an enterprise application for the company Vopak. Was involved in building the whole android software architecture and acted Project Manager for this project.",
    					"HanDy Calc was developed during fresher training program. It is available in Android Market.",
    					"Accelerando was a 1 day project. Was involved in building (coding) the whole application. It is available in Android Market.",
    					"eFireTrack is another enterprise application being developed by me and my team. Was involved for architecting the software and high level features coding.",
    					"Developing an educational game prototype for the company Blue Penguin. Was involved as a game logic programmer. The game was developed in Unreal Engine for PC.",
    					"Project involved in developing a comic book reader application. Was involved in, software architecture, user interface design and encryption.",
    					"Was involved as software architect, acting project manager as well as a consultant to the sales department.",
    					"Porting old text based game Zork, to Kindle devices.",
    					"Developed an application that featured details about different places of tourist interest in India. This application was an entrant and category winner at Indian Android Developers Contest. It is available in the Android App Market.",
    					"Developed an application that featured mail server configuration, email templates and 3D user interface. It is available in the Android App Market."};
    			break;
    		case 3:
    			projects = new String[]{"Development of Double Hold’em, for iPhone. The game featured upto 9 AI (artificial intelligence) poker players.", 
    					"Overseeing a team of 3 that involved on various applications for ABCMouse.com. Also acted as the technical advisor for the entire team."};
    			break;
    		case 4:
    			projects = new String[]{"Was involved in conceptualizing the entire project and was a part of the development team. Also prototyped " +
    					"a game engine on android platform to port the game from iOS to Android.  This game is available at iStore.",
    					"Was involved in developing a prototype, which used the phone’s camera input as a tool for game world interaction."};
    			break;
    		case 5:
    			projects = new String[]{"Developed a media player application for android handsets and desktop. The application featured real-time decryption of the songs played by the media player.", 
    					"Point is an application that features reverse geo-coding to get the exact street level address of the user. It also stores this data in a remote server so that it can be viewed by a second person. This feature allows company administrators to track their employees.",
    					"This application featured a list of different Mexican recipes. The application is currently available in the Apple Store."};
    			break;
    		case 6:
    			projects = new String[]{"Memorabilia is an android application that helps anyone store memorable event of their life as text, pictures, videos, audio and map tags. The user can later on view all these data specific to memeories. it is currently under development.",
    					"Radial Menu is a UI compnent that is inspire by the Android webview menu item.",
    					"Graph3r is a free to use android library for plotting various graphs. It is still under development."};
    			break;
    	}
    	return projects;
    }
}
