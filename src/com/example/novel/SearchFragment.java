package com.example.novel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novel.asynctask.AsyncSearchTask;
import com.example.novel.parser.SearchResult;

public class SearchFragment extends Fragment {

	Spinner mOption; // Search option
	TextView mInput; // Search input value
	Button mSearch; // Search button
	ListView mResult; // ListView of search result.
	static final String NCH_URL = "http://www.nch.com.tw/"; // Novel Channel address
	static final String SEARCH_URL = NCH_URL + "search.php?exec=search"; // Search query api
	static final String SEARCH_KEY = "&key="; // Search key is 0:writer 1:book
	static final String SEARCH_VALUE = "&various="; // Search input
	static final String ENCODING_BIG5 = "big5"; // Encoding type of Novel Channel site
	static final String VARIOUS = "various="; // String of various field
	static final String PAGE = "&page="; // String of page field
	int mOptionSelected; // Option selected 0:writer 1: book
	static final int LOAD_ITEM_THRESHOLD = 15; // Item threshold before loading more items.
	// The larger this value is, the earlier the remaining data is loaded.
	SearchResult mPreviousResult; // Previous search result
	ArrayList<String> mItems; // Content of ArrayAdapter

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_fragment, container, false);
		setView(view);
		setSpinner();
		setButton();
		setListView();
		return view;
	}

	/*
	 * Setup view with layout component.
	 * @param view is the top-level view of this fragment.
	 */
	private void setView(View view) {
		mOption = (Spinner) view.findViewById(R.id.option);
		mInput = (TextView) view.findViewById(R.id.input);
		mSearch = (Button) view.findViewById(R.id.search_button);
		mResult = (ListView) view.findViewById(R.id.result);
	}

	/*
	 * Setup spinner.
	 */
	private void setSpinner() {
		Resources resources = getActivity().getResources();
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
				android.R.layout.simple_spinner_item,
				new String[] {
			resources.getString(R.string.search_writer),
			resources.getString(R.string.search_book)}
				);
		mOption.setAdapter(adapter);
		mOption.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				mOptionSelected = position;
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
	}

	/*
	 * Setup Button.
	 */
	private void setButton() {
		mSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Reset ListView when searching a new value.
				setListView();
				// Execute search task with query string.
				AsyncSearchTask.run(
						getActivity(),
						SEARCH_URL + SEARCH_KEY + mOptionSelected + SEARCH_VALUE +
						encode(mInput.getText().toString(), ENCODING_BIG5));
			}

		});
	}

	/*
	 * Encode the given string with given code.
	 * @param value is the string to be encoded.
	 * @param code is the encoding-type such as utf-8 and big5.
	 * @return the encoded string.
	 */
	private String encode(String value, String code) {
		try {
			value = java.net.URLEncoder.encode(value, code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	/*
	 * Setup ListView.
	 */
	private void setListView() {
		mPreviousResult = null;
		mItems = new ArrayList<String> ();
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
				android.R.layout.simple_list_item_1,
				mItems
				);
		mResult.setAdapter(adapter);
	}

	/*
	 * Encode the various field in the url.
	 * @param url is the string containing the various.
	 * @return the string that its various is being encoded.
	 */
	private String encodeVariousInUrl(String url) {
		String various = url.substring(
				url.indexOf(VARIOUS) + VARIOUS.length(),
				url.indexOf(PAGE));
		System.out.println(url.replace(various, encode(various, ENCODING_BIG5)));
		return url.replace(various, encode(various, ENCODING_BIG5));
	}

	/*
	 * Setup ListView.
	 * @param currentResult is the current search result.
	 */
	public void setListView(SearchResult currentResult) {
		// No match result.
		if (currentResult.name.size() == 0) {
			currentResult.name.add(getResources().getString(R.string.no_match_result));
			currentResult.nameUrl.add("");
		}
		// Add result to data set of ArrayAdapter.
		for (String item : currentResult.name) {
			mItems.add(item);
		}
		// Insert previous result to front if there is any.
		if (mPreviousResult != null) {
			currentResult.insertFront(mPreviousResult);
		}
		final SearchResult result = currentResult;
		(ArrayAdapter.class.cast(mResult.getAdapter())).notifyDataSetChanged();
		mResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				//System.out.println("position: " + position + " result size: " + result.name.size());
				// TODO: Replace the toast by starting an article activity.
				Toast.makeText(getActivity(), "position: " + position +
						", id: " + id +
						", novel name: " + result.name.get(position) +
						"\n" + "novel url:" + result.nameUrl.get(position) , Toast.LENGTH_LONG).show();
			}
		});
		mResult.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// Load the next page when it's about to the bottom of the list and the current
				// page is not the last page.
				if (firstVisibleItem + LOAD_ITEM_THRESHOLD + visibleItemCount >= totalItemCount // About to bottom
						&& !AsyncSearchTask.isRunning() // No task running
						&& result.totalPages > 1 // There is more than one page.
						&& result.currentPage < result.totalPages // The current page is not the last page.
						) {
					AsyncSearchTask.run(getActivity(), encodeVariousInUrl(result.nextPageUrl));
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// Do nothing for now.
			}
		});
		mPreviousResult = currentResult;
	}
}