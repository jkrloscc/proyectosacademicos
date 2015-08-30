package com.example.monitortrafico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.rcmmapp.AppListAdapter;
import com.example.rcmmapp.R;
import com.example.rcmmapp.R.id;
import com.example.rcmmapp.R.menu;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ListActivity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TrafficStatsActivity extends ListActivity {

	AppListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a new TodoListAdapter for this ListActivity's ListView
		mAdapter = new AppListAdapter(getApplicationContext());

		TrafficStats stats = new TrafficStats();

		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		for (RunningAppProcessInfo processInfo : activityManager
				.getRunningAppProcesses()) {

			if (stats.getUidRxBytes(processInfo.uid) != TrafficStats.UNSUPPORTED
					&& stats.getUidRxBytes(processInfo.uid) != 0) {
				AppItem newAppItem = new AppItem(processInfo.processName,
						stats.getUidRxBytes(processInfo.uid));

				// La a�adimos al adaptador
				mAdapter.add(newAppItem);

			}
		}

		setListAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.traffic_stats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
