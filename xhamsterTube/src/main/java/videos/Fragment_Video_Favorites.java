package videos;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.xhamstertube.ActivityVideoplayer;
import com.example.xhamstertube.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import adapter.Adapter_Videos_Grid;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterVideo;
import de.greenrobot.event.EventBus;
import events.EventVideoListLoaded;
import helper.EndlessScrollListener;
import helper.Helper;
import siteinteraction.XhamsterSiteInteraction;
import siteinteraction.XhamsterVideoListLoader;

public class Fragment_Video_Favorites extends Fragment {
    ActionMode mActionMode;
    @InjectView(R.id.gridview)
    GridView mGridView;
    @InjectView(R.id.progress_grid)
    ProgressBar progressBar;
    ArrayList<XhamsterVideo> mVideoList;
    Adapter_Videos_Grid mAdapter;
    EventBus bus = EventBus.getDefault();
    ActionBar mActionbar;
    XhamsterVideoListLoader loader;
    int selectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TEST", "oncreate " + "Fragment_Video_Favorites");
        bus.register(this);
        loader = new XhamsterVideoListLoader(getActivity(), XhamsterVideoListLoader.MODE_FAVORITES);
        mVideoList = new ArrayList<XhamsterVideo>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.layout_grid, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionbar = getActivity().getActionBar();
        mActionbar.setTitle("Favorite Videos");
        mActionbar.setSubtitle(null);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
               selectedItem = position;
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                mActionMode.setTitle(mAdapter.getItem(position).getTitle());
                return true;
            }
        });


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Gson gson = new Gson();
                String bundleString = gson.toJson(mAdapter.getItem(arg2));
                Intent inte = new Intent(getActivity(), ActivityVideoplayer.class);
                inte.putExtra("DATA", bundleString);
                startActivity(inte);
            }
        });

        mGridView.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (Helper.getOnlineState(getActivity())) {
                    loader.loadMoreFavorites();
                }
            }
        });
        mAdapter = new Adapter_Videos_Grid(getActivity(), R.layout.listitem_video, mVideoList);
        mGridView.setAdapter(mAdapter);
        if (mVideoList.size() == 0) {
            loader.loadFavorites();
        }

    }

    public void onEvent(EventVideoListLoaded event) {
        progressBar.setVisibility(View.GONE);
        mAdapter.addAll(event.getList());
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_favorite_videos, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete_favorite:
                    XhamsterSiteInteraction interaction = new XhamsterSiteInteraction(getActivity());
                    interaction.removeVideoFromFavorites(mAdapter.getItem(selectedItem));
                    mAdapter.removeVideo(selectedItem);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
         }
    };

}
