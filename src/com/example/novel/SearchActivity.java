package com.example.novel;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class SearchActivity extends FragmentActivity {
	
	static final String FRAGMENT_TAG = "search";
	SearchFragment mSearchFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		// Use fragment tag to check if the fragment existed to prevent fragment overlap
		// by adding new fragment.
		if (fragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
			mSearchFragment = new SearchFragment();
			fragmentTransaction.add(R.id.search_layout, mSearchFragment, FRAGMENT_TAG).commit();
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public SearchFragment geSearchFragment() {
		return mSearchFragment;
	}
}