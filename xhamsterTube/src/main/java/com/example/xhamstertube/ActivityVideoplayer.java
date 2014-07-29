package com.example.xhamstertube;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import android.widget.VideoView;

import com.cengalabs.flatui.FlatUI;
import com.google.gson.Gson;

import java.util.ArrayList;

import adapter.Adapter_Videos_Grid;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterUser;
import dao.XhamsterVideo;
import de.greenrobot.event.EventBus;
import drawer.Fragment_Comments;
import events.EventFavoriteChange;
import events.EventLoadComments;
import events.EventRelatedVideosLoaded;
import events.EventSubmitComment;
import events.EventVideoLoadError;
import events.EventVideoLoaded;
import siteinteraction.XhamsterSiteInteraction;
import siteinteraction.XhamsterVideoLoader;

public class ActivityVideoplayer extends Activity {

    @InjectView(R.id.right_drawer)
    ListView relatedList;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    @InjectView(R.id.videoView1)
    VideoView mVideoView;
    @InjectView(R.id.videoprogress)
    ProgressBar progress;
    private ArrayList<XhamsterVideo> mVideoList;
    private Adapter_Videos_Grid mAdapter;
    EventBus bus = EventBus.getDefault();
    XhamsterVideo mVideo;
    XhamsterVideoLoader mVideoloader;
    MyMediaController mController;
    Boolean isFavorite = false;
    boolean firstLoad = true;
    String streamUrl;
    int mPosition = 0;
    private ShareActionProvider mShareActionProvider;
    private MenuItem mMenuFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.ORANGE);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.ORANGE, false));
        setContentView(R.layout.activity_videoplayer);
        ButterKnife.inject(this);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        bus.register(this);

        String jsonevent = getIntent().getStringExtra("DATA");
        Gson gson = new Gson();
        mVideo = gson.fromJson(jsonevent, XhamsterVideo.class);
        mVideoList = new ArrayList<XhamsterVideo>();
        relatedList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Gson gson = new Gson();
                String bundleString = gson.toJson(mAdapter.getItem(arg2));
                Intent inte = new Intent(getApplicationContext(), ActivityVideoplayer.class);
                inte.putExtra("DATA", bundleString);
                startActivity(inte);
            }
        });
        mAdapter = new Adapter_Videos_Grid(this, R.layout.listitem_video, mVideoList);
        relatedList.setAdapter(mAdapter);
        prepareVideoView();
        mVideoloader = new XhamsterVideoLoader(this);
        progress.setVisibility(View.VISIBLE);
        if (mVideo != null) {
            getActionBar().setTitle(mVideo.getTitle());
        } else {
            mVideo = new XhamsterVideo();
            Uri data = getIntent().getData();
            String url = data.toString();
            if (url.contains("m.xhamster.com")) {
                url = url.replace("m.xhamster.com", "xhamster.com");
                mVideo.setSiteUrl(url);
            } else {
                mVideo.setSiteUrl(url);
            }
        }
        if (savedInstanceState == null) {
            Log.i("TEST", "Firstload");
            getFragmentManager().beginTransaction().add(R.id.left_drawer, Fragment_Comments.newInstance()).commit();
            mVideoloader.loadVideo(mVideo);
            firstLoad = false;
        } else {
            mVideoView.setVideoPath(streamUrl);
        }
    }

    @Override
    protected void onResume() {
        drawer.closeDrawer(Gravity.END);
        getActionBar().show();
        Log.i("TEST", "Resume");
        mVideoView.start();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.videoplayer, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mMenuFavorite = menu.findItem(R.id.action_favorite);
        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_related:
                if (drawer.isDrawerOpen(Gravity.END)) {
                    drawer.closeDrawer(Gravity.END);
                } else {
                    drawer.openDrawer(Gravity.END);
                }
                return false;
            case R.id.action_userinfio:
                openUserInfo();
                return false;
            case R.id.action_comments:
                if (drawer.isDrawerOpen(Gravity.START)) {
                    drawer.closeDrawer(Gravity.START);
                } else {
                    drawer.openDrawer(Gravity.START);
                }
                return false;
            case R.id.action_favorite:
                XhamsterSiteInteraction inter = new XhamsterSiteInteraction(getApplicationContext());
                inter.addVideoToFavorites(mVideo);
                mMenuFavorite.setIcon(R.drawable.ic_action_favorite);
                return false;
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)
                        || getIntent().getAction() != null) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    // Stay in same task
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void openUserInfo() {
        Gson gson = new Gson();
        XhamsterUser user = new XhamsterUser();
        user.setUserName(mVideo.getUserName());
        user.setUserUrl(mVideo.getUserURL());
        String bundleString = gson.toJson(user);
        Intent inte = new Intent(getApplicationContext(), ActivityUserInfo.class);
        inte.putExtra("DATA", bundleString);
        startActivity(inte);
    }

    @Override
    public void onPause() {
        mPosition = mVideoView.getCurrentPosition();
        super.onPause();
    }

    public void onEvent(EventFavoriteChange event){
        if(event.getSuccess()){
            Toast.makeText(this,"Als Favorit gespeichert",Toast.LENGTH_LONG).show();
        }
    }

    public void onEvent(EventRelatedVideosLoaded event) {
        mAdapter.addAll(event.getList());
    }

    public void onEvent(EventLoadComments event) {
        Log.i("TEST", "loadmorecomments");
        mVideoloader.loadMoreComments();
    }

    public void onEvent(EventSubmitComment event) {
        mVideoloader.submitComment(event.getText());
    }

    public void onEvent(EventVideoLoadError error) {
        Toast.makeText(getApplicationContext(), error.getError(), Toast.LENGTH_LONG).show();
    }

    public void prepareVideoView() {
        mController = new MyMediaController(this);
        mVideoView.setMediaController(mController);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mVideoView.seekTo(mPosition);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                getActionBar().hide();
                mVideoView.seekTo(mPosition);
                progress.setVisibility(View.GONE);
            }
        });
        mVideoView.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("TEST", "Mediaplayer Error");
                getActionBar().show();
                return false;
            }
        });
    }

    public void onEvent(EventVideoLoaded videoEvent) {
        getActionBar().setTitle(mVideo.getTitle());
        getActionBar().setSubtitle("Uploaded by: " + videoEvent.getVideo().getUserName());
        streamUrl = videoEvent.getVideo().getStreamUrl();
        mVideo = videoEvent.getVideo();
        mVideoView.setVideoPath(streamUrl);
    }

    private class MyMediaController extends MediaController {

        public MyMediaController(Context context) {
            super(context, false);
        }

        @Override
        public void hide() {
            try {
                getActionBar().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.hide();
        }

        @Override
        public void show() {
            getActionBar().show();
            super.show(5000);
        }

        @Override
        public void show(int timeout) {
            getActionBar().show();
            super.show(5000);
        }

    }
}
