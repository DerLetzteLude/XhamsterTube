package usercentral;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.xhamstertube.ActivityGalleryViewer;
import com.example.xhamstertube.ActivityVideoplayer;
import com.example.xhamstertube.R;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import adapter.Adapter_UpdateItems;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterGallery;
import dao.XhamsterVideo;
import helper.HeaderStringRequest;
import helper.Helper;
import helper.VolleySingleton;

public class Fragment_usercentral_ContentFeed extends Fragment {
	

	@InjectView(R.id.gridview) GridView mGridView;
	ArrayList<UpdateItem> mUpatelist;
	int selectedItem;
	Adapter_UpdateItems mAdapter;
	ActionBar mActionBar;	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUpatelist = new ArrayList<UpdateItem>();
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
		mActionBar.setTitle("Content Feed");
		mActionBar.setSubtitle(null);
		mAdapter = new Adapter_UpdateItems(getActivity(), R.layout.listitem_contentfeeditem, mUpatelist);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
				UpdateItem item = mAdapter.getItem(i);
				if (item.getType() == UpdateItem.TYPE_VIDEO) {
					Gson gson = new Gson();
					XhamsterVideo video = item.getVideo();
					String bundleString = gson.toJson(video);
					Intent inte = new Intent(getActivity(), ActivityVideoplayer.class);
					inte.putExtra("DATA", bundleString);
					startActivity(inte);
				}
				if (item.getType() == UpdateItem.TYPE_PICTURES) {
					Gson gson = new Gson();
					XhamsterGallery gallery = item.getGallery();					
					String bundleString = gson.toJson(gallery);
					Intent inte = new Intent(getActivity(), ActivityGalleryViewer.class);
					inte.putExtra("DATA", bundleString);
					startActivity(inte);
				}

			}

		});
		if (savedInstanceState == null) {
			loadGalleryPage();
		}
	}

	public void loadGalleryPage() {
		String url = "http://xhamster.com/my_news.php?show=content";
		String cookie = null;
		if (Helper.getOnlineState(getActivity())) {
			cookie = Helper.getCookie(getActivity());
		}

		RequestQueue requestQueue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
		HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				Document doc = Jsoup.parse(response);
				Elements items = doc.select("div.el");
				for (Element tempItem : items) {
					// VIDEO CHECK
					Elements videos = tempItem.getElementsByClass("video");

					if (videos.size() > 0) {
						for (Element videoelement : videos) {
							Element ele = videoelement.select("img[src]").first();
							String thumbUrl = ele.attr("src");
							ele = videoelement.getElementsByTag("u").first();
							String title = ele.text();

							ele = videoelement.getElementsByAttribute("href").first();
							String link = null;
							try {
								link = ele.attr("href");
							} catch (Exception e) {
								e.printStackTrace();
							}
							int id = 0;
							if (link != null) {
								String[] ids = link.split("/");
								id = Integer.valueOf(ids[4]);
							}
							UpdateItem item = new UpdateItem(title, UpdateItem.TYPE_VIDEO, link, thumbUrl, id);
							mAdapter.add(item);
						}
					}

					// PICTURE CHECK
					Elements gallerys = tempItem.getElementsByClass("gallery");
					if (gallerys.size() > 0) {
						for (Element galleryelement : gallerys) {
							Element ele = galleryelement;
							Log.i("TEST", ele.toString());
							// TITEL FUNKTIONIERT
							String title = ele.getElementsByClass("gtitle").first().text();

							// URL
							ele = galleryelement.getElementsByClass("gtitle").first();
							String url = ele.attr("href");

							// ThumbURL FUNKTIONIERT
							ele = galleryelement.getElementsByClass("img").first();
							String[] thumbURLSmalls = ele.toString().split("src=\"");
							thumbURLSmalls = thumbURLSmalls[1].split("\" />");
							String thumbURLSmall = thumbURLSmalls[0];

							// TEST
							StringBuilder builder = new StringBuilder(thumbURLSmall);
							builder.replace(0, 9, "http://ep");
							builder.replace(thumbURLSmall.length() - 7, thumbURLSmall.length() - 4, "1000");
							String thumbURLBig = builder.toString();

							int id = 0;
							if (url != null) {
								String[] ids = url.split("/");

								ids = ids[ids.length - 1].split("-");

								ids = ids[ids.length - 1].split(".html");

								id = Integer.valueOf(ids[0]);
							}
							UpdateItem item = new UpdateItem(title, UpdateItem.TYPE_PICTURES, url, thumbURLBig);
							item.setId(id);
							mAdapter.add(item);
						}
					}
				}

			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		}, cookie);

		requestQueue.add(request);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
