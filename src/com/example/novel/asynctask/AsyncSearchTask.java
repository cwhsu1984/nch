package com.example.novel.asynctask;

import android.app.Activity;

import com.example.novel.SearchActivity;
import com.example.novel.parser.SearchResult;
import com.example.novel.parser.SearchResultParser;

public class AsyncSearchTask extends CommonAsyncTask {

	static boolean mRunning = false;

	public AsyncSearchTask(Activity activity, String request) {
		super(activity, request);
	}

	public static void run(Activity activity, String request) {
		new AsyncSearchTask(activity, request).execute();
	}

	@Override
	protected final void onPreExecute() {
		super.onPreExecute();
		mRunning = true;
	}

	@Override
	protected Object doInBackGround() {
		// Get result of data
		return SearchResultParser.parse(responseBody);
	}

	@Override
	protected void onPostExecuteMethod(Object result){
		// Put retrieved data into ListView
		((SearchActivity) activity).geSearchFragment().setListView((SearchResult) result);
		mRunning = false;
	}

	public static boolean isRunning() {
		return mRunning;
	}
}
