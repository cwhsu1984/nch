package com.example.novel.asynctask;

import java.util.ArrayList;

import com.example.novel.R;
import com.example.novel.SearchActivity;
import com.example.novel.parser.Novel;
import com.example.novel.parser.SearchResultParser;

import android.app.Activity;

public class AsyncSearchTask extends CommonAsyncTask {
	
	ArrayList<Novel> mNovels;
	
	public AsyncSearchTask(Activity activity, String request) {
		super(activity, request);
	}

	public static void run(Activity activity, String request) {
		new AsyncSearchTask(activity, request).execute();
	}

	@Override
	void doInBackGround() {
		// Get result of data 
		mNovels = SearchResultParser.parse(responseBody);
		// Show not found when no result
		if (mNovels.size() == 0) {
			Novel empty = new Novel();
			empty.name = activity.getResources().getString(R.string.not_found);
			mNovels.add(empty);
		}
	}
	
	@Override
	protected void onPostExecute(){
		// Put retrieved data into ListView
		((SearchActivity) activity).geSearchFragment().setListView(mNovels);
	}
}
