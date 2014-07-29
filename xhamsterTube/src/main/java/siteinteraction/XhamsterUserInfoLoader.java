package siteinteraction;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dao.XhamsterUser;
import de.greenrobot.event.EventBus;
import events.EventUserInfoLoadError;
import events.EventUserInfoLoaded;
import helper.HeaderStringRequest;
import helper.Helper;
import helper.VolleySingleton;

public class XhamsterUserInfoLoader {

	private Context context;
	private XhamsterUser mUser;

	public XhamsterUserInfoLoader(Context context, XhamsterUser user) {
		this.context = context;
		mUser = user;
	}

	public void loadUserPage() {

		RequestQueue requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
		String cookie = Helper.getCookie(context);
		String url = mUser.getUserUrl();
		Log.i("TEST", "URL: " + url);
		HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				if (response.contains("This profile is visible to friends only")) {
					EventBus.getDefault().post(new EventUserInfoLoadError("This profile is visible to friends only"));
				} else if (response.contains("This profile is visible to registered users only")) {
					EventBus.getDefault().post(new EventUserInfoLoadError("This profile is visible to registered users only"));
				} else {
					Document doc = Jsoup.parse(response);

					Element ele = doc.getElementsByClass("boxB").first();
					String top = ele.toString();

					mUser.setInfoBlock(top);
					EventBus.getDefault().post(new EventUserInfoLoaded(mUser));

				}

			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.i("TEST", arg0.toString());
			}
		}, cookie);
		requestQueue.add(request);
	}

}
