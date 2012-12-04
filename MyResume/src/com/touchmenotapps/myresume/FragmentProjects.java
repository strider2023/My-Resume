package com.touchmenotapps.myresume;

import java.util.ArrayList;

import com.touchmenotapps.myresume.adapters.ProjectsListAdapter;
import com.touchmenotapps.myresume.functions.AppCommonFunctions;
import com.touchmenotapps.myresume.preferences.AppPreferences;
import com.touchmenotapps.myresume.variables.AppGlobalVariables;
import com.touchmenotapps.myresume.variables.ProjectsObject;
import com.touchmenotapps.myresume.variables.ResumeDataObject;
import com.touchmenotapps.myresume.widgets.AppTextView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class FragmentProjects extends Fragment {
	
	private int MAX_COUNT = 0;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;

	private ListView projects_list;
	private ProjectsListAdapter adapter;
	private TextSwitcher comapny_switcher;
	private RelativeLayout instruction_btn;
	
	private Animation shake;
	private AppCommonFunctions functions;
	private AppPreferences pref;
	private int count = 0;
	private ArrayList<ProjectsObject> mProjectsData = null;
	private ResumeDataObject resumeData;
	private ProgressDialog mProgressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mHolderView = inflater.inflate(R.layout.fragment_projects, null);
		shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        adapter = new ProjectsListAdapter(getActivity());
        functions = new AppCommonFunctions(getActivity());
        pref = new AppPreferences(getActivity());
        resumeData = new ResumeDataObject(getActivity());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.loading));
        
        projects_list = (ListView) mHolderView.findViewById(R.id.projects_listview);
        comapny_switcher = (TextSwitcher) mHolderView.findViewById(R.id.projects_company_name_text);
        instruction_btn = (RelativeLayout) mHolderView.findViewById(R.id.instrunstions_layout);
        
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
				AppTextView textSwitcher_text = new AppTextView(getActivity());
		        textSwitcher_text.setTextColor(Color.argb(225, 245, 242, 11));
		        textSwitcher_text.setTextSize(32 * functions.getScreenDPI());
		        textSwitcher_text.setSingleLine(true);
		        textSwitcher_text.setEllipsize(TruncateAt.MARQUEE);
		        textSwitcher_text.setMarqueeRepeatLimit(-1);
		        textSwitcher_text.setHorizontallyScrolling(true);
		        return textSwitcher_text;
			}
		});
                
        mProgressDialog.show();
        new LoadData().execute(new Void[0]);
        
        // Gesture detection
        gestureDetector = new GestureDetector(getActivity(), new OnGestureListener() {
			
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
		return mHolderView;
	}
    
    private void nextCompany(){
    	count++;
    	comapny_switcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));
    	comapny_switcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));
    	projects_list.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rail_in));
    	comapny_switcher.setText(mProjectsData.get(count).getCompanyName());
    	adapter.notifyDataSetChanged();
    	adapter.setListData(mProjectsData.get(count).getProjectDescription());
        projects_list.setAdapter(adapter);
    }
    
    private void previousComapny() {
    	count--;
    	comapny_switcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
    	comapny_switcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out));
    	projects_list.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rail_out));
    	comapny_switcher.setText(mProjectsData.get(count).getCompanyName());
    	adapter.notifyDataSetChanged();
    	adapter.setListData(mProjectsData.get(count).getProjectDescription());
        projects_list.setAdapter(adapter);
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
				mProjectsData = resumeData.getProjectsData();
				loaderResponse = "true";
			} catch (Exception e) {
				e.printStackTrace();
			}
			return loaderResponse;
		}

		protected void onPostExecute(String result) {
			if(result.equals("true")) {
				MAX_COUNT = mProjectsData.size();
				comapny_switcher.setText(mProjectsData.get(count).getCompanyName());
		        adapter.setListData(mProjectsData.get(count).getProjectDescription());
		        projects_list.setAdapter(adapter);
		        mProgressDialog.dismiss();
			} else 
				Toast.makeText(getActivity(), R.string.error_parsing, Toast.LENGTH_SHORT).show();
		}
    }
}
