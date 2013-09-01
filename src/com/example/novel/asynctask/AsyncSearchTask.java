package com.example.novel.asynctask;

import com.example.novel.R;
import com.example.novel.SearchActivity;
import com.example.novel.parser.Novel;
import com.example.novel.parser.SearchResult;
import com.example.novel.parser.SearchResultParser;

import android.app.Activity;

public class AsyncSearchTask extends CommonAsyncTask {
	
	SearchResult mResult;
	
	public AsyncSearchTask(Activity activity, String request) {
		super(activity, request);
	}

	public static void run(Activity activity, String request) {
		new AsyncSearchTask(activity, request).execute();
	}

	@Override
	void doInBackGround() {
		// Get result of data 
		mResult = SearchResultParser.parse(responseBody);
		// Show not found when no result
		if (mResult.novels.size() == 0) {
			Novel empty = new Novel();
			empty.name = activity.getResources().getString(R.string.not_found);
			mResult.novels.add(empty);
		}
	}
	
	@Override
	protected void onPostExecute(){
		// Put retrieved data into ListView
		((SearchActivity) activity).geSearchFragment().setListView(mResult);
	}
}
