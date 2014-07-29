package siteinteraction;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dao.XhamsterGallery;
import dao.XhamsterUser;
import dao.XhamsterVideo;
import de.greenrobot.event.EventBus;
import events.EventFavoriteChange;

public class XhamsterSiteInteraction {
	Context context;
	WebView mWebView;

	public XhamsterSiteInteraction(Context context) {
		this.context = context;
		mWebView = new WebView(context);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("TEST", "shouldOverrideUrlLoading " + url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
			}

		});
	}

	public void addVideoToFavorites(XhamsterVideo video) {
		mWebView.addJavascriptInterface(new SaveListener(), "HTMLOUT");
		mWebView.loadUrl("http://m.xhamster.com/fav/1.html?add=" + video.getId());
	}

	public void removeVideoFromFavorites(XhamsterVideo video) {
		mWebView.addJavascriptInterface(new RemoveListener(), "HTMLOUT");
		mWebView.loadUrl("http://m.xhamster.com/fav/1.html?del=" + video.getId());
	}

	public void addGalleryToFavorites(XhamsterGallery gallery) {
		mWebView.addJavascriptInterface(new SaveListener(), "HTMLOUT");
		mWebView.loadUrl("http://m.xhamster.com/photo_fav.html?add=" + gallery.getId());
	}

	public void removeGalleryFromFavorites(XhamsterGallery gallery) {
		mWebView.addJavascriptInterface(new RemoveListener(), "HTMLOUT");
		mWebView.loadUrl("http://m.xhamster.com/fav/1.html?del=" + gallery.getId());
	}

	
	public void addUserSubscription(XhamsterUser user) {
		Log.i("TEST", "addUserToFavorites");
		mWebView = new WebView(context);

		mWebView.getSettings().setJavaScriptEnabled(true);
		if (Build.VERSION.SDK_INT <= 18) {
			mWebView.getSettings().setSavePassword(false);
		} else {
		}
		mWebView.getSettings().setUserAgentString("Chrome");
		mWebView.setWebViewClient(new WebViewClient() {
			boolean loaded = false;

			public void onPageFinished(WebView view, String url) {
				Log.i("TEST", "onPageFinished siteurl: " + url);
				if (!loaded) {
					mWebView.loadUrl("javascript:(function(){" + "l=document.getElementsByClassName('subscribe')[0];"
							+ "e=document.createEvent('HTMLEvents');" + "e.initEvent('click',true,true);" + "l.dispatchEvent(e);" + "})()");
					loaded = true;
				}
			}

		});
		String url = user.getUserUrl();
		Log.i("TEST", "loadUrl " + url);
		mWebView.loadUrl(url);
	}

	class SaveListener {
		@JavascriptInterface
		public void processHTML(String html) {
			EventFavoriteChange event = new EventFavoriteChange();

			if (html.contains("Video successfully saved")) {
				event.setMessage("Video successfully saved");
				event.setSuccess(true);
			}
			if (html.contains("Video already saved")) {
				event.setMessage("Video already saved");
				event.setSuccess(true);
			}
			if (html.contains("login")) {
				event.setMessage("please log in first");
				event.setSuccess(false);
			}
			EventBus.getDefault().post(event);
		}
	}

	class RemoveListener {
		@JavascriptInterface
		public void processHTML(String html) {
			EventFavoriteChange event = new EventFavoriteChange();

			if (html.contains("Video successfully deleted")) {
				event.setMessage("Video successfully deleted");
				event.setSuccess(true);
			}
			if (html.contains("login")) {
				event.setMessage("please log in first");
				event.setSuccess(false);
			}
			EventBus.getDefault().post(event);

		}
	}

}
