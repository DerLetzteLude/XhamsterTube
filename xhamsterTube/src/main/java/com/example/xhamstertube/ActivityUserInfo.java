package com.example.xhamstertube;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.cengalabs.flatui.FlatUI;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterUser;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import picture.Fragment_Picture_Gallerys;
import siteinteraction.XhamsterGalleryListLoader;
import siteinteraction.XhamsterSiteInteraction;
import siteinteraction.XhamsterUserInfoLoader;
import siteinteraction.XhamsterVideoListLoader;
import usercentral.Fragment_UserInfo;
import videos.Fragment_Videos_Grid;

public class ActivityUserInfo extends FragmentActivity {
	@InjectView(R.id.pager) ViewPager pager;
	@InjectView(R.id.tabs) PagerSlidingTabStrip tabs;
	private MyPagerAdapter adapter;
	XhamsterUser mUser;
	XhamsterUserInfoLoader mLoader;
	boolean subscribed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FlatUI.initDefaultValues(this);
		FlatUI.setDefaultTheme(FlatUI.ORANGE);
		getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.ORANGE, false));
		setContentView(R.layout.activity_userinfo);
		ButterKnife.inject(this);

		String UserString = getIntent().getStringExtra("DATA");
		Gson gson = new Gson();
		mUser = gson.fromJson(UserString, XhamsterUser.class);
		getActionBar().setTitle("Userpage of: " + mUser.getUserName());
		adapter = new MyPagerAdapter(getSupportFragmentManager(), UserString);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(2);
		tabs.setViewPager(pager);
		mLoader = new XhamsterUserInfoLoader(this, mUser);
		mLoader.loadUserPage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.userinfo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_subscribe:
			XhamsterSiteInteraction inter = new XhamsterSiteInteraction(getApplicationContext());
			inter.addUserSubscription(mUser);

			if (subscribed) {
				Crouton.makeText(this, "unsubscribed", Style.INFO).show();
				subscribed = false;
			} else {
				Crouton.makeText(this, "subscribed", Style.INFO).show();
				subscribed = true;
			}
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Info", "Videos", "Bilder" };
		private String userString;

		public MyPagerAdapter(FragmentManager fm, String userString) {
			super(fm);
			this.userString = userString;

		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = Fragment_UserInfo.newInstance(userString);
				break;
			case 1:
				fragment = Fragment_Videos_Grid.newInstance(XhamsterVideoListLoader.MODE_USERGALLERY, mUser.getUserName());
				break;
			case 2:
				fragment = Fragment_Picture_Gallerys.newInstance(XhamsterGalleryListLoader.MODE_USERGALLERY, mUser.getUserName());
				break;
			}
			return fragment;
		}
	}

}
