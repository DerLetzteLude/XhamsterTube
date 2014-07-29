package com.example.xhamstertube;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import drawer.Fragment_Drawer_Search;
import videos.Fragment_Videos_Search;

public class ActivitySearch extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new Fragment_Videos_Search()).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.left_drawer, new Fragment_Drawer_Search()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
