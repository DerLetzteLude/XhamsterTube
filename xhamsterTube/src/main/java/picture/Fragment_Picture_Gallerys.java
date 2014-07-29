package picture;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import helper.EndlessScrollListener;
import siteinteraction.XhamsterGalleryListLoader;

public class Fragment_Picture_Gallerys extends Fragment {

	@InjectView(R.id.gridview) GridView mGridView;
	@InjectView(R.id.progress_grid) ProgressBar progressBar;
	ActionBar mActionbar;
	int mMode;
	String mQuery;
	ArrayList<XhamsterGallery> mGalleryList;
	String mTitle;
	boolean mfirstOpen;
	XhamsterGalleryListLoader listLoader;
	Adapter_Picture_Gallerys mAdapter;
	EventBus bus = EventBus.getDefault();

	public static Fragment newInstance(int mode, String query) {
		Fragment_Picture_Gallerys myFragment = new Fragment_Picture_Gallerys();
		Bundle args = new Bundle();
		args.putInt("mode", mode);
		args.putString("query", query);
		myFragment.setArguments(args);
		return myFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bus.register(this);
		mActionbar = getActivity().getActionBar();
		mMode = getArguments().getInt("mode");
		mQuery = getArguments().getString("query");
		mfirstOpen = true;
		mGalleryList = new ArrayList<XhamsterGallery>();
		listLoader = new XhamsterGalleryListLoader(getActivity(), mMode, mQuery);
		if (mMode == XhamsterGalleryListLoader.MODE_NEW) {
			mActionbar.setTitle("New Gallerys");
		}
		if (mMode == XhamsterGalleryListLoader.MODE_CHANNELS) {
			mActionbar.setTitle(mQuery + " Gallerys");
		}
		if (mMode == XhamsterGalleryListLoader.MODE_SEARCH) {
			mActionbar.setTitle("Search: " + mQuery);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		setHasOptionsMenu(true);
		View v = inflater.inflate(R.layout.layout_grid, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mAdapter = new Adapter_Picture_Gallerys(getActivity(), R.layout.listitem_gallery, mGalleryList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				listLoader.loadMore();
			}
		});
		if (mfirstOpen) {
			listLoader.loadGalleryPage();
			mfirstOpen = false;
		}
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Gson gson = new Gson();
				XhamsterGallery gallery = mAdapter.getItem(arg2);
				String bundleString = gson.toJson(gallery);
				Intent inte = new Intent(getActivity(), ActivityGalleryViewer.class);
				inte.putExtra("DATA", bundleString);
				startActivity(inte);
			}
		});

	}

	public void onEvent(EventGalleryListLoaded event) {
		progressBar.setVisibility(View.GONE);
		mAdapter.addAll(event.getList());
	}

}