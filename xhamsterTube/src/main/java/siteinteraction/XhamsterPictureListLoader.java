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
import org.jsoup.select.Elements;

import java.util.ArrayList;

import dao.XhamsterGallery;
import dao.XhamsterPicture;
import de.greenrobot.event.EventBus;
import events.EventGalleryIDLoaded;
import events.EventGalleryUsernameLoaded;
import events.EventPictureListLoaded;
import helper.HeaderStringRequest;
import helper.VolleySingleton;

public class XhamsterPictureListLoader {
    private Context context;
    private ArrayList<XhamsterPicture> mPictureList;
    private XhamsterGallery mGallery;
    private String mUserLink;
    private String mUserName;
    private RequestQueue mRequestQueue;

    public XhamsterPictureListLoader(Context context, XhamsterGallery gallery) {
        this.context = context;
        mPictureList = new ArrayList<XhamsterPicture>();
        mGallery = gallery;
        mRequestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
    }

    public void loadGalleryPage() {
        Log.i("TEST", "loadGalleryPage onResponse");
        String url = mGallery.getGalleryUrl();
        Log.i("TEST", "Sideurl: " + url);
        HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                // get User
                Element user = doc.getElementById("galleryUser");
                Element link = user.select("a").first();
                String relHref = link.attr("href");
                mUserLink = "http://xhamster.com/" + relHref;
                mUserName = link.text();
                EventBus.getDefault().post(new EventGalleryUsernameLoaded(mUserName,mUserLink));

                //Elements items = doc.getElementsByClass("gallery iItem");
                Element item = doc.getElementsByClass("boxC").first();
                Elements items = item.getElementsByClass("gallery");
                for (Element tempItem : items) {
                    tempItem = tempItem.getElementsByClass("vert").first();
                    // picturesmall
                    String pictureSmallUrl = tempItem.attr("src");
                    // picturebig
                    StringBuilder builder = new StringBuilder(pictureSmallUrl);
                    builder.replace(0, 9, "http://ep");
                    builder.replace(pictureSmallUrl.length() - 7, pictureSmallUrl.length() - 4, "1000");
                    String pictureBigUrl = builder.toString();

                    XhamsterPicture picture = new XhamsterPicture();
                    picture.setThumbSmallUrl(pictureSmallUrl);
                    picture.setThumbBigUrl(pictureBigUrl);
                    picture.setUserName(mUserName);
                    picture.setUserURL(mUserLink);

                    mPictureList.add(picture);
                }
                if (!mPictureList.isEmpty()) {
                    EventBus.getDefault().post(new EventPictureListLoaded(mPictureList));
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.i("TEST", arg0.toString());
            }
        });
        mRequestQueue.add(request);
    }

    public void loadGalleryUrl(final String pictureUrl) {
        HeaderStringRequest request = new HeaderStringRequest(pictureUrl, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Element viewBox = doc.getElementById("viewBox");

                Elements links = viewBox.select("a[href]");
                String galleryURL = null;
                for (Element temp : links) {
                    if (temp.toString().contains("xhamster.com/photos/gallery")) {
                        galleryURL = temp.attr("href");
                    }
                }
                if (galleryURL.length() > 0) {
                    mGallery.setGalleryUrl(galleryURL);
                    urlToId(galleryURL);

                    loadGalleryPage();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
            }
        });
        mRequestQueue.add(request);
    }

    public void urlToId(String url) {
        Long id = new Long(0);
        String[] split = url.split("/gallery/");
        if (split[1] != null) {
            split = split[1].split("/");
            if (split[0] != null) {
                id = Long.valueOf(split[0]);
                EventBus.getDefault().post(new EventGalleryIDLoaded(id));
            }
        }
    }

}
