package videos;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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

public class Fragment_Video_Top extends Fragment implements OnNavigationListener {

	@InjectView(R.id.gridview) GridView mGridView;
	@InjectView(R.id.progress_grid) ProgressBar progressBar;
	EventBus bus = EventBus.getDefault();
	String mQuery;
	ArrayList<XhamsterVideo> mVideoList;
	int selectedVideo;
	String mTitle;
	ActionBar mActionBar;
	Adapter_Videos_Grid mAdapter;
	String mSpinnerSelection;
	private ArrayAdapter<String> mSpinneradapter;
	private XhamsterVideoListLoader mLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TEST", "oncreate "+"Fragment_Video_Top");
		mVideoList = new ArrayList<XhamsterVideo>();
		mSpinnerSelection = "weekly";
		bus.register(this);
		mLoader = new XhamsterVideoListLoader(getActivity(), XhamsterVideoListLoader.MODE_TOP);
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
		mActionBar.setTitle("Top Videos");
		mActionBar.setSubtitle(null);
		mAdapter = new Adapter_Videos_Grid(getActivity(), R.layout.listitem_video, mVideoList);
		mGridView.setAdapter(mAdapter);
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

		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		final String[] dropdownValues = getResources().getStringArray(R.array.top_options);
		mSpinneradapter = new ArrayAdapter<String>(mActionBar.getThemedContext(), android.R.layout.simple_spinner_item, android.R.id.text1,
				dropdownValues);
		mSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mActionBar.setListNavigationCallbacks(mSpinneradapter, this);
		if (mVideoList.size() == 0) {
			
		//	mLoader.loadTopVideoList(mSpinnerSelection);
		}

	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		mSpinnerSelection = mSpinneradapter.getItem(arg0);
		mAdapter.clear();
		mLoader.loadTopVideoList(mSpinnerSelection);
		return false;
	}

	@Override
	public void onStop() {
		super.onStop();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}
	
	public void onEvent(EventVideoListLoaded event) {
		progressBar.setVisibility(View.GONE);
		mAdapter.addAll(event.getList());
	}

}