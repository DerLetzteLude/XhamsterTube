package videos;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import events.EventLoadSearchVideos;
import events.EventVideoListLoaded;
import helper.EndlessScrollListener;
import siteinteraction.XhamsterVideoListLoader;

public class Fragment_Videos_Search extends Fragment {
	@InjectView(R.id.gridview) GridView mGridView;
	@InjectView(R.id.progress_grid) ProgressBar progressBar;
	EventBus bus = EventBus.getDefault();

	private ArrayList<XhamsterVideo> mVideoList;
	private Adapter_Videos_Grid mAdapter;
	private ActionBar mActionBar;
	private XhamsterVideoListLoader mLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TEST", "oncreate " + "Fragment_Videos_Grid");
		bus.register(this);
		mVideoList = new ArrayList<XhamsterVideo>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		View v = inflater.inflate(R.layout.layout_grid, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mActionBar = getActivity().getActionBar();
		mActionBar.setSubtitle(null);
        mLoader = new XhamsterVideoListLoader(getActivity());
		mAdapter = new Adapter_Videos_Grid(getActivity(), R.layout.listitem_video, mVideoList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				mLoader.loadMore();
			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Gson gson = new Gson();
				String bundleString = gson.toJson(mAdapter.getItem(arg2));
				Intent inte = new Intent(getActivity(), ActivityVideoplayer.class);
				inte.putExtra("DATA", bundleString);
				startActivity(inte);
			}
		});

	}

	public void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	public void hideProgress() {
		progressBar.setVisibility(View.GONE);
	}

	public void onEvent(EventVideoListLoaded event) {
		progressBar.setVisibility(View.GONE);
		mAdapter.addAll(event.getList());
		hideProgress();
	}

    public void onEvent(EventLoadSearchVideos event){
        mLoader.loadSearchVideoList(event.getQuery(),event.getCategory(),event.getSorting(),event.getDate(),event.getDuration(),event.getQuality());
    }

}