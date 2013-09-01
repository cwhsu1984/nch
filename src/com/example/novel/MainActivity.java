package com.example.novel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set fragment statically
		setContentView(R.layout.activity_main);
	}

	public static class HomeListFragment extends ListFragment {

		static String[] menu = null;
		static final int SEARCH = 0;
		static final int ADD = 1;
		static final int CLASSES = 2;
		static final int MYBOOK = 3;
		static final int SPWRITER = 4;
		static final int WRITER = 5;
		static final int GUESTBOOK = 6;
		static final int HOT = 7;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			initializeMenu();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, menu);
			setListAdapter(adapter);
		}

		/*
		 * Initialize data of the menu on MainActivity.
		 */
		private void initializeMenu() {
			Resources resources = getActivity().getResources();
			menu = new String[] {
					resources.getString(R.string.search),
					resources.getString(R.string.add),
					resources.getString(R.string.classes),
					resources.getString(R.string.mybook),
					resources.getString(R.string.spwriter),
					resources.getString(R.string.writer),
					resources.getString(R.string.guestbook),
					resources.getString(R.string.hot)
			};
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			switch (position) {
			case SEARCH:
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchActivity.class);
				startActivity(intent);
				break;
				// TODO: Add other activities later.
				/*
			case ADD:
				break;
			case HOT:
				break;
				 */
			default:
				Toast.makeText(getActivity(), "TODO", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
