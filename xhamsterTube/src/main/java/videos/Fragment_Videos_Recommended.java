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
import events.EventVideoListLoaded;
import siteinteraction.XhamsterVideoListLoader;

public class Fragment_Videos_Recommended extends Fragment {

	@InjectView(R.id.gridview) GridView mGridView;
	@InjectView(R.id.progress_grid) ProgressBar progressBar;
	EventBus bus = EventBus.getDefault();
	ArrayList<XhamsterVideo> mVideoList;
	int selectedVideo;
	Adapter_Videos_Grid mAdapter;
	ActionBar mActionBar;
	private XhamsterVideoListLoader mLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TEST", "oncreate "+"Fragment_Videos_Recommended");
		bus.register(this);
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
		mActionBar = getActivity().getActionBar();
		mActionBar.setTitle("Recommended Videos");
		mActionBar.setSubtitle(null);
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
		mAdapter = new Adapter_Videos_Grid(getActivity(), R.layout.listitem_video, mVideoList);
		mGridView.setAdapter(mAdapter);
		if (mVideoList.size() == 0) {
			mLoader = new XhamsterVideoListLoader(getActivity());
			mLoader.loadRecommended();
		}
	}

	public void onEvent(EventVideoListLoaded event) {
		progressBar.setVisibility(View.GONE);
		mAdapter.addAll(event.getList());
	}

}