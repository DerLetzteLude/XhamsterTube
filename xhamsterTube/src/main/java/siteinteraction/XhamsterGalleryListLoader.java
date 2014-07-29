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
import java.util.Locale;

import dao.XhamsterGallery;
import de.greenrobot.event.EventBus;
import events.EventGalleryListLoaded;
import helper.HeaderStringRequest;
import helper.VolleySingleton;

public class XhamsterGalleryListLoader {
    public static final int MODE_CHANNELS = 1;
    public static final int MODE_SEARCH = 2;
    public static final int MODE_USERGALLERY = 3;
    public static final int MODE_NEW = 4;
    public static final int MODE_TOP = 5;

    private int mode;
    private Context context;
    private ArrayList<XhamsterGallery> mGalleryList;
    private int mPage = 1;
    private String mQuery;
    private Boolean mFirstload = true;
    private int mPageCount = 0;
    private boolean hasGallerys = false;

    public XhamsterGalleryListLoader(Context context, int mode, String query) {
        this.mode = mode;
        this.context = context;
        mGalleryList = new ArrayList<XhamsterGallery>();
        this.mQuery = query;
    }

    public XhamsterGalleryListLoader(Context context, int mode) {
        this.mode = mode;
        this.context = context;
        mGalleryList = new ArrayList<XhamsterGallery>();
        this.mQuery = "";
    }

    public XhamsterGalleryListLoader(Context context) {
        this.mode = 0;
        this.context = context;
        mGalleryList = new ArrayList<XhamsterGallery>();
        this.mQuery = "";
    }

    private String buildRequestUrl() {
        String url = "";
        switch (mode) {
            case MODE_NEW:
                url = "http://xhamster.com/photos/new/" + mPage + ".html";
                break;
            case MODE_SEARCH:
                url = "http://xhamster.com/search.php?q=" + mQuery + "&qcat=pictures&page=" + mPage;
                break;
            case MODE_CHANNELS:
                url = "http://xhamster.com/photos/niches/new-" + mQuery.toLowerCase(Locale.ENGLISH) + "-" + mPage + ".html";
                break;
            case MODE_USERGALLERY:
                url = "http://xhamster.com/user/photo/" + mQuery + "/new-" + mPage + ".html";
                break;
        }
        return url;
    }

    public void loadGalleryPage() {
        RequestQueue requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        String url = buildRequestUrl();
        Log.i("TEST", "URL: " + url);
        HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

            @Override
            public void onResponse(String response) {

                Document doc = Jsoup.parse(response);
                Elements items;
                Element item;
                if (mFirstload) {
                    // PAGER AUSLESEN
                    Element pageCount = doc.getElementsByClass("pager").last();

                    if (pageCount != null) {
                        // EINZELNE LINKS AUSLESEN
                        items = pageCount.getElementsByAttribute("href");
                        // LINKS PARSERN

                        for (Element tempItem : items) {
                            try {
                                mPageCount = Integer.parseInt(tempItem.text());
                                hasGallerys = true;
                            } catch (Exception e) {
                            }
                        }
                    }

                }
                if (hasGallerys) {
                    item = doc.select("div.boxC").first();

                    items = item.getElementsByClass("gallery");

                    for (Element tempItem : items) {
                        if (tempItem.hasClass("gallery")) {
                            try {
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
                                String thumbURL = ele.attr("src");

                                // TEST
                                StringBuilder builder = new StringBuilder(thumbURL);
                                builder.replace(0, 9, "http://ep");
                                builder.replace(thumbURL.length() - 7, thumbURL.length() - 4, "1000");
                                thumbURL = builder.toString();

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
                                gallery.setTitle(title);
                                gallery.setGalleryName(title);
                                gallery.setGalleryUrl(url);
                                gallery.setThumbBigUrl(thumbURL);
                                gallery.setRating(rating);
                                gallery.setViewCount(views);
                                gallery.setPictureCount(Integer.valueOf(pictureCount));
                                mGalleryList.add(gallery);
                            } catch (NullPointerException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    if (mGalleryList.size() > 0 && mPage <= mPageCount) {
                        mPage = mPage + 1;
                    }
                    mFirstload = false;
                    EventBus.getDefault().post(new EventGalleryListLoaded(mGalleryList, mode));
                }

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.i("TEST", arg0.toString());
            }
        });
        requestQueue.add(request);
    }

    public void loadMore() {
        if (!mFirstload && mPage <= mPageCount) {
            mGalleryList.clear();
            loadGalleryPage();
        }
    }

    public void loadTopGalleryList(String mSpinnerSelection) {
        mGalleryList.clear();
        RequestQueue requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        String url = "http://xhamster.com/photos/rankings/" + mSpinnerSelection + "-rated.html";
        Log.i("TEST", "URL: "+url);
        HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

            @Override
            public void onResponse(String response) {

                Document doc = Jsoup.parse(response);
                Elements items = doc.getElementsByClass("info");
                Element tempItem;

                for (Element item : items) {
                    tempItem = item;

                    Element ele = tempItem.select("img[src]").first();
                    String thumbUrl = ele.attr("src");

                    ele = tempItem.getElementsByAttribute("title").first();
                    String title = ele.attr("title");

                    ele = tempItem.getElementsByAttribute("href").first();
                    String link = ele.attr("href");

                    XhamsterGallery gallery = new XhamsterGallery();
                    gallery.setTitle(title);
                    gallery.setThumbBigUrl(thumbUrl);
                    gallery.setThumbSmallUrl(thumbUrl);
                    gallery.setPictureUrl(link);
                    mGalleryList.add(gallery);
                }
                EventBus.getDefault().post(new EventGalleryListLoaded(mGalleryList, mode));
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.i("TEST", arg0.toString());
            }
        });
        requestQueue.add(request);
    }
}
