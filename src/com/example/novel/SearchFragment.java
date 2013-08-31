package com.example.novel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.novel.asynctask.AsyncSearchTask;
import com.example.novel.parser.Novel;

public class SearchFragment extends Fragment {
	
	Spinner mOption;
	TextView mInput;
	Button mSearch;
	ListView mResult;
	static final int SEARCH_BOOK = 0;
	static final int SEARCH_WRITER = 1;
	static final String NCH_URL = "http://www.nch.com.tw/";
	static final String SEARCH_URL = NCH_URL + "search.php?exec=search";
	static final String SEARCH_KEY = "&key=";
	static final String SEARCH_VALUE = "&various=";
	static final String BIG5 = "big5";
	int mSelection;
	
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
		return view;
	}
	
	private void setView(View view) {
		mOption = (Spinner) view.findViewById(R.id.option);
		mInput = (TextView) view.findViewById(R.id.input);
		mSearch = (Button) view.findViewById(R.id.search_button);
		mResult = (ListView) view.findViewById(R.id.result);
	}
	
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
				mSelection = position;
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}				
		});
	}
	
	private void setButton() {
		mSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// Encode search value as big5.
					String value = java.net.URLEncoder.encode(
							mInput.getText().toString(),
							BIG5);						
					AsyncSearchTask.run(
							getActivity(),
							SEARCH_URL + SEARCH_KEY + mSelection + SEARCH_VALUE + value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	public void setListView(ArrayList<Novel> novels) {
		// Replace from here to adapter later.
		ArrayList<String> books = new ArrayList<String> ();
		for (Novel novel : novels) {
			books.add(novel.name);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
				android.R.layout.simple_list_item_1,
				books
		);
		mResult.setAdapter(adapter);
		mResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Toast.makeText(getActivity(), position + " " + id + " ", Toast.LENGTH_LONG).show();
			}
			
		});
	}
}