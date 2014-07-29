package com.example.xhamstertube;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

import com.cengalabs.flatui.FlatUI;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterGallery;
import dao.XhamsterPicture;
import dao.XhamsterUser;
import de.greenrobot.event.EventBus;
import events.EventGalleryIDLoaded;
import events.EventGalleryUsernameLoaded;
import events.EventPictureListLoaded;
import picture.Fragment_SinglePicture;
import siteinteraction.XhamsterPictureListLoader;
import siteinteraction.XhamsterSiteInteraction;

public class ActivityGalleryViewer extends FragmentActivity {
    @InjectView(R.id.pager)
    ViewPager pager;
    EventBus bus = EventBus.getDefault();
    XhamsterGallery gallery;
    ActionBar mActionbar;
    Boolean isFavorite = false;
    ArrayList<XhamsterPicture> picturelist;
    private MenuItem mMenuFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.ORANGE);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.ORANGE, false));
        bus.register(this);
        setContentView(R.layout.activity_galleryviewer);
        ButterKnife.inject(this);
        pager.setOffscreenPageLimit(3);
        mActionbar = getActionBar();
        String jsongallery = getIntent().getStringExtra("DATA");
        Gson gson = new Gson();
        picturelist = new ArrayList<XhamsterPicture>();
        gallery = gson.fromJson(jsongallery, XhamsterGallery.class);
        mActionbar.setTitle(gallery.getTitle());
        if (gallery.getGalleryUrl() != null) {
            XhamsterPictureListLoader pictureLoader = new XhamsterPictureListLoader(getApplicationContext(), gallery);
            pictureLoader.loadGalleryPage();
        } else {
            if (gallery.getPictureUrl() != null) {
                XhamsterPictureListLoader pictureLoader = new XhamsterPictureListLoader(getApplicationContext(), gallery);
                pictureLoader.loadGalleryUrl(gallery.getPictureUrl());
            }
        }

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                getActionBar().setSubtitle((arg0+1)+" / "+gallery.getPictureCount());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.galleryviewer, menu);
        mMenuFavorite = menu.findItem(R.id.action_favorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                XhamsterSiteInteraction inter = new XhamsterSiteInteraction(getApplicationContext());
                inter.addGalleryToFavorites(gallery);
                mMenuFavorite.setIcon(R.drawable.ic_action_favorite);
                return false;
            case R.id.action_userinfio:
                openUserInfo();
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openUserInfo() {
        Gson gson = new Gson();
        XhamsterUser user = new XhamsterUser();
        user.setUserName(gallery.getUserName());
        user.setUserUrl(gallery.getUserURL());
        String bundleString = gson.toJson(user);
        Intent inte = new Intent(getApplicationContext(), ActivityUserInfo.class);
        inte.putExtra("DATA", bundleString);
        startActivity(inte);
    }

    public void onEvent(EventPictureListLoaded event) {
        picturelist.addAll(event.getList());
        gallery.setPictureCount(picturelist.size());
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), picturelist, picturelist.size());
        pager.setAdapter(adapter);
        getActionBar().setSubtitle("1 / "+gallery.getPictureCount());
    }

    public void onEvent(EventGalleryIDLoaded event){
        gallery.setId(event.getId());
    }

    public void onEvent(EventGalleryUsernameLoaded event){
        gallery.setUserName(event.getUserName());
        gallery.setUserURL(event.getUserURL());
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public ArrayList<XhamsterPicture> fragmentsA;
        private int count;

        public MyPagerAdapter(FragmentManager fmm, ArrayList<XhamsterPicture> pic, int pictureCount) {
            super(fmm);
            fragmentsA = pic;
            count = pictureCount;
        }

        public void addItem(XhamsterPicture pic) {
            this.fragmentsA.add(pic);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment_SinglePicture.newInstance(fragmentsA.get(position).getThumbBigUrl());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "bla";
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
