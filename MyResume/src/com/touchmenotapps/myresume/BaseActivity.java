package com.touchmenotapps.myresume;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity implements
		FragmentMainMenu.Callbacks {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.base_single_pane_container,
						new FragmentMainMenu()).commit();
	}

	public void onProfileSelected() {
		if (findViewById(R.id.base_second_pane_container) != null)
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_second_pane_container,
							new FragmentProfile()).commit();
		else
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_single_pane_container,
							new FragmentProfile()).addToBackStack(null)
					.commit();
	}

	public void onWorkSelected() {
		if (findViewById(R.id.base_second_pane_container) != null)
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_second_pane_container,
							new FragmentWorkExp()).commit();
		else
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_single_pane_container,
							new FragmentWorkExp()).addToBackStack(null)
					.commit();
	}

	public void onProjectSelected() {
		if (findViewById(R.id.base_second_pane_container) != null)
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_second_pane_container,
							new FragmentProjects()).commit();
		else
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_single_pane_container,
							new FragmentProjects()).addToBackStack(null)
					.commit();
	}

	public void onInterestSelected() {
		if (findViewById(R.id.base_second_pane_container) != null)
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_second_pane_container,
							new FragmentInterests()).commit();
		else
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_single_pane_container,
							new FragmentInterests()).addToBackStack(null)
					.commit();
	}

	public void onContactSelected() {
		if (findViewById(R.id.base_second_pane_container) != null)
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_second_pane_container,
							new FragmentContact()).commit();
		else
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.base_single_pane_container,
							new FragmentContact()).addToBackStack(null)
					.commit();
	}
}
