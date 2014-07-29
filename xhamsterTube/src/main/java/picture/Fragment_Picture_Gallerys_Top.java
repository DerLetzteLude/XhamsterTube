package picture;

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

import com.example.xhamstertube.ActivityGalleryViewer;
import com.example.xhamstertube.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import adapter.Adapter_Picture_Gallerys;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterGallery;
import de.greenrobot.event.EventBus;
import events.EventGalleryListLoaded;
import siteinteraction.XhamsterGalleryListLoader;

public class Fragment_Picture_Gallerys_Top extends Fragment implements OnNavigationListener {

	@InjectView(R.id.gridview) GridView mGridView;
	@InjectView(R.id.progress_grid) ProgressBar progressBar;
	EventBus bus = EventBus.getDefault();
	String mQuery;
	ArrayList<XhamsterGallery> mGalleryList;
	int selectedVideo;
	String mTitle;
	ActionBar mActionBar;
	Adapter_Picture_Gallerys mAdapter;
	String mSpinnerSelection;
	private ArrayAdapter<String> mSpinneradapter;
	private XhamsterGalleryListLoader mLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TEST", "oncreate "+"Fragment_Video_Top");
        mGalleryList = new ArrayList<XhamsterGallery>();
		mSpinnerSelection = "weekly";
		bus.register(this);
		mLoader = new XhamsterGalleryListLoader(getActivity(), XhamsterGalleryListLoader.MODE_TOP);
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
		mAdapter = new Adapter_Picture_Gallerys(getActivity(), R.layout.listitem_video, mGalleryList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Gson gson = new Gson();
                String bundleString = gson.toJson(mAdapter.getItem(arg2));
                Intent inte = new Intent(getActivity(), ActivityGalleryViewer.class);
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
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		mSpinnerSelection = mSpinneradapter.getItem(arg0);
		mAdapter.clear();
		mLoader.loadTopGalleryList(mSpinnerSelection);
		return false;
	}

	@Override
	public void onStop() {
		super.onStop();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}
	
	public void onEvent(EventGalleryListLoaded event) {
		progressBar.setVisibility(View.GONE);
		mAdapter.addAll(event.getList());
	}

}