package helper;

import android.content.Context;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import dao.XhamsterGallery;
import dao.XhamsterPicture;
import dao.XhamsterVideo;

public class Helper {
	public static final String MyPREFERENCES = "MyPrefs";

	public Helper() {

	}

	public static Bundle getBundle(XhamsterVideo video) {
		Bundle data = new Bundle();
		data.putString("url", video.getSiteUrl());
		data.putString("title", video.getTitle());
		data.putLong("videoid", video.getId());
		data.putString("rating", video.getRating());
		data.putString("runtime", video.getRuntime());
		data.putString("thumburl", video.getThumbUrl());
		return data;
	}

	public static Boolean getOnlineState(Context context) {
		CookieSyncManager.createInstance(context);
		String mCookie = CookieManager.getInstance().getCookie(".xhamster.com");
		if (mCookie != null && mCookie.contains("USERNAME")) {
			return true;
		} else {
			return false;
		}
	}

	public static String getCookie(Context context) {
		CookieSyncManager.createInstance(context);
		String mCookie = CookieManager.getInstance().getCookie(".xhamster.com");
		return mCookie;
	}

	public static XhamsterVideo getVideo(Bundle bundle) {

		return null;
	}

	public static Bundle getGalleryBundle(XhamsterGallery gallery) {
		Bundle data = new Bundle();
		data.putLong("id", gallery.getId());
		data.putInt("pictureCount", gallery.getPictureCount());
		data.putString("rating", gallery.getRating());
		data.putString("thumURL", gallery.getThumbBigUrl());
		data.putString("title", gallery.getTitle());
		data.putString("url", gallery.getGalleryUrl());
		data.putString("views", gallery.getViewCount());

		return data;
	}

	public static Bundle getGalleryBundle(XhamsterPicture picture) {
		Bundle data = new Bundle();
		data.putLong("id", picture.getId());
		data.putString("thumURL", picture.getThumbBigUrl());
		data.putString("title", picture.getTitle());
		data.putString("url", picture.getGalleryUrl());

		return data;
	}

	public static String getCategorieString(String input) {
		String[] splittedString;
		String output = input;
		if (input.contains(",")) {
			splittedString = input.split(",");
			output = splittedString[1];
		}
		return output;
	}

}
