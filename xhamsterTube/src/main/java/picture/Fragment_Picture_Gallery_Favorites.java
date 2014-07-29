package picture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.xhamstertube.ActivityGalleryViewer;
import com.example.xhamstertube.R;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import adapter.Adapter_Picture_Gallerys;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterGallery;
import helper.HeaderStringRequest;
import helper.Helper;
import helper.VolleySingleton;

public class Fragment_Picture_Gallery_Favorites extends Fragment {
	@InjectView(R.id.gridview) GridView mGridView;
	@InjectView(R.id.grid_layout) FrameLayout mLayout;

	String mCookie;

	private int mPageCount = 0;
	private boolean firstLoaded = false;
	ArrayList<XhamsterGallery> mGalleryList;
	Adapter_Picture_Gallerys mAdapter;

	ActionMode.Callback mCallback;
	ActionMode mMode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGalleryList = new ArrayList<XhamsterGallery>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View v = inflater.inflate(R.layout.layout_grid, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getActivity().getActionBar().setSubtitle(null);
		getActivity().getActionBar().setTitle("favorites");
		mAdapter = new Adapter_Picture_Gallerys(getActivity(), R.layout.listitem_gallery, mGalleryList);
		mGridView.setAdapter(mAdapter);

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

		if (Helper.getOnlineState(getActivity())) {
			mCookie = Helper.getCookie(getActivity());
			loadFavorites();
			Log.i("TEST", "Cookie vorhanden loading favorites. Cookie: " + mCookie);
		}

	}

	private void loadFavorites() {
		RequestQueue requestQueue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();

		HeaderStringRequest request = new HeaderStringRequest("http://xhamster.com/photos/favorites-gallery-1.html",
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						Document doc = Jsoup.parse(response);
						Elements items;
						Element item;
						if (!firstLoaded) {
							// PAGER AUSLESEN
							Element pageCount = doc.getElementsByClass("pager").last();
							// EINZELNE LINKS AUSLESEN
							items = pageCount.getElementsByAttribute("href");
							// LINKS PARSERN

							for (Element tempItem : items) {
								try {
									if (Integer.parseInt(tempItem.text()) > mPageCount) {
										mPageCount = Integer.parseInt(tempItem.text());
									}
								} catch (Exception e) {
								}
							}

						}

						item = doc.select("div.boxC").first();

						items = item.getElementsByClass("gallery");

						for (Element tempItem : items) {
							if (tempItem.hasClass("gallery")) {
								Element ele;
								// TITEL FUNKTIONIERT
								ele = tempItem.getElementsByTag("u").first();
								String title = ele.text();

								// GALLERYID FUNKTIONIERT
								String galleryID = tempItem.id();
								int galleryINT = Integer.valueOf(galleryID.substring(2));

								// URL FUNKTIONIERT
								ele = tempItem.getElementsByAttribute("href").first();
								String url = ele.attr("href");

								// ThumbURL FUNKTIONIERT
								ele = tempItem.getElementsByClass("vert").first();
								String thumbURLSmall = ele.attr("src");

								// TEST
								StringBuilder builder = new StringBuilder(thumbURLSmall);
								builder.replace(0, 9, "http://ep");
								builder.replace(thumbURLSmall.length() - 7, thumbURLSmall.length() - 4, "1000");
								String thumbURLBig = builder.toString();

								// RATING FUNKTIONIERT
								ele = tempItem.getElementsByClass("fr").first();
								String rating = ele.text();

								// VIEWS FUNKTIONIERT
								String[] viewSplit = tempItem.toString().split("Views: ");
								viewSplit = viewSplit[1].split("</div>");
								String views = viewSplit[0].trim();

								// PICTURECOUNT FUNKTIONIERT
								ele = tempItem.getElementsByClass("thumb").first();
								String pictureCount = ele.text();

								// ID

								Long id = Long.valueOf(galleryINT);

								XhamsterGallery gallery = new XhamsterGallery(id);
								if (!title.equals("Deleted")) {
									gallery.setTitle(title);
									gallery.setGalleryUrl(url);
									gallery.setThumbSmallUrl(thumbURLSmall);
									gallery.setThumbBigUrl(thumbURLBig);
									gallery.setRating(rating);
									gallery.setViewCount(views);
									gallery.setPictureCount(Integer.valueOf(pictureCount));
									mAdapter.add(gallery);
								}

							}
						}
						firstLoaded = true;
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.i("TEST", arg0.toString());
					}
				}, mCookie);
		requestQueue.add(request);

	}
}
