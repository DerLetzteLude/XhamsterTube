package com.example.xhamstertube;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.FrameLayout;

import com.cengalabs.flatui.FlatUI;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import drawer.Fragment_Drawer;
import events.EventChangeFragment;
import events.EventLogin;
import siteinteraction.XhamsterVideoListLoader;
import usercentral.FragmentLoginDialog;
import videos.Fragment_Videos_Grid;

public class MainActivity extends FragmentActivity {

	@InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	@InjectView(R.id.drawer_list) FrameLayout mDrawerList;
	@InjectView(R.id.content_frame) FrameLayout mContentFrame;
	Fragment_Drawer fragmentDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private MenuItem mSearchItem;
	private MenuItem mLoginItem;

	EventBus bus = EventBus.getDefault();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FlatUI.initDefaultValues(this);
		FlatUI.setDefaultTheme(FlatUI.ORANGE);
		getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.ORANGE, false));
		bus.register(this);
		CookieSyncManager.createInstance(this);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		fragmentDrawer = new Fragment_Drawer();
		getSupportFragmentManager().beginTransaction().add(R.id.drawer_list, fragmentDrawer).commit();
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			Fragment fragment = new Fragment_Videos_Grid();
			Bundle data = new Bundle();
			data.putInt("mode", XhamsterVideoListLoader.MODE_NEW);
			data.putString("query", "new");
			fragment.setArguments(data);
			changeFragment(fragment);
		}
		connectivityCheck();
	}

	@Override
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		mLoginItem = menu.findItem(R.id.action_login);

		CookieManager cookieManager = CookieManager.getInstance();
		String cookie = cookieManager.getCookie(".xhamster.com");
		if (cookie == null) {
			showLogin();
		} else {
			hideLogin();
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_login:
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			FragmentLoginDialog newFragment = new FragmentLoginDialog();
			newFragment.show(ft, "dialog");
			break;
		default:
			break;
		}
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void connectivityCheck() {
		Boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = true;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}

		if (!haveConnectedWifi) {
			if (haveConnectedMobile) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Keine WLAN Verbindung");
				builder.setCancelable(false);
				builder.setMessage("Das betrachten von Video Streams erzeugt ein hohes Daten aufkommen. Trotzdem fortfahren?");
				builder.setPositiveButton("weiter", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.setNegativeButton("zurück", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						onBackPressed();
					}
				});
				builder.create();
				builder.show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setCancelable(false);
				builder.setMessage("Leider wurde keine Internet verbindung gefunden");
				builder.setPositiveButton("weiter", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.create();
				builder.show();
			}

		}
	}

	public void showLogin() {
		mLoginItem.setVisible(true);
	}

	public void hideLogin() {
		mLoginItem.setVisible(false);
	}


	public void onEvent(EventChangeFragment event) {
		changeFragment(event.getFragment());
	}

	public void onEvent(EventLogin event) {
		if (event.getLogedIn()) {
			hideLogin();
		} else {
			showLogin();
		}
	}

	public void changeFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}
}
