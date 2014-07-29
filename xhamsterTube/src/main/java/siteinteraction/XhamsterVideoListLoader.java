package siteinteraction;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dao.XhamsterVideo;
import de.greenrobot.event.EventBus;
import events.EventVideoListLoaded;
import helper.HeaderStringRequest;
import helper.Helper;
import helper.VolleySingleton;

public class XhamsterVideoListLoader {
    public static final int MODE_NEW = 0;
    public static final int MODE_CHANNELS = 1;
    public static final int MODE_SEARCH = 2;
    public static final int MODE_USERGALLERY = 3;
    public static final int MODE_TOP = 4;
    public static final int MODE_FAVORITES = 5;

    private int mode;
    private Context context;
    private ArrayList<XhamsterVideo> mVideoList;
    private int mPage = 1;
    private String mQuery;
    private Boolean firstload = true;
    private int mLastpage = 0;

    public XhamsterVideoListLoader(Context context, int mode, String query) {
        this.mode = mode;
        this.context = context;
        mVideoList = new ArrayList<XhamsterVideo>();
        this.mQuery = query;
    }

    public XhamsterVideoListLoader(Context context, int mode) {
        this.mode = mode;
        this.context = context;
        mVideoList = new ArrayList<XhamsterVideo>();
        this.mQuery = "";
    }

    public XhamsterVideoListLoader(Context context) {
        this.mode = 0;
        this.context = context;
        mVideoList = new ArrayList<XhamsterVideo>();
        this.mQuery = "";
    }

    private String buildRequestUrl() {
        String url;

        switch (mode) {
            case MODE_NEW:
                url = "http://xhamster.com/new/" + mPage + ".html";
                break;
            case MODE_SEARCH:
                url = "http://xhamster.com/search.php?q=" + mQuery + "&qcat=video&page=" + mPage;
                break;
            case MODE_CHANNELS:
                url = "http://xhamster.com/channels/new-" + mQuery.toLowerCase(Locale.ENGLISH) + "-" + mPage + ".html";
                break;
            case MODE_USERGALLERY:
                url = "http://xhamster.com/user/video/" + mQuery + "/new-" + mPage + ".html";
                break;
            case MODE_FAVORITES:
                url = "http://xhamster.com/favorites/videos-" + mPage + ".html";
                break;
            default:
                url = "http://xhamster.com";
                break;
        }
        return url;
    }

    public void loadFavorites() {
        Log.i("TEST", "loadFavorites url: " + buildRequestUrl());
        String cookie = Helper.getCookie(context);
        RequestQueue requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        HeaderStringRequest request = new HeaderStringRequest(buildRequestUrl(), new Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                Document doc = Jsoup.parse(arg0);

                if (firstload) {
                    // GET PAGECOUNT
                    Element pager = doc.select("div.pager").first();
                    if (!(pager == null)) {
                        Elements allnumbers = pager.getElementsByAttribute("href");

                        for (Element i : allnumbers) {
                            try {
                                int number = Integer.valueOf(i.text());
                                mLastpage = number;
                            } catch (NumberFormatException e) {

                            }
                        }

                    }
                    firstload = false;
                }

                // GET VIDEOS
                Elements items = doc.select("div.video");
                for (Element tempItem : items) {
                    if (tempItem.hasClass("video")) {
                        Element ele = tempItem.select("img[src]").first();
                        String thumbUrl = ele.attr("src");

                        ele = tempItem.getElementsByTag("u").first();
                        String title = ele.text();

                        ele = tempItem.getElementsByTag("b").first();
                        String runtime = ele.text();

                        ele = tempItem.getElementsByAttribute("href").first();
                        String link = null;
                        try {
                            link = ele.attr("href");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("TEST", e.toString());

                        }

                        int id = 0;
                        if (link != null) {
                            String[] ids = link.split("/");
                            id = Integer.valueOf(ids[4]);
                        }

                        ele = tempItem.select("div.fr").first();
                        String rating = ele.text();

                        String view;

                        try {
                            ele = tempItem.getElementsByClass("hRate").first();
                            if (ele.toString().contains("just")) {
                                view = "just added";
                            } else {
                                String[] views = ele.text().split("Views: ");
                                view = views[1];
                            }
                        } catch (Exception e) {
                            view = "0";
                            e.printStackTrace();
                        }

                        if (id != 0) {
                            XhamsterVideo video = new XhamsterVideo(id);
                            video.setTitle(title);
                            video.setRuntime(runtime);
                            video.setSiteUrl(link);
                            video.setRating(rating);
                            video.setThumbUrl(thumbUrl);
                            video.setViewCount(view);
                            mVideoList.add(video);
                        }

                    }
                }
                if (mVideoList.size() > 0 && mPage <= mLastpage) {
                    mPage = mPage + 1;
                }
                EventBus.getDefault().post(new EventVideoListLoaded(mVideoList, mode));
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.i("TEST", arg0.toString());
            }
        }, cookie);
        requestQueue.add(request);
    }

    public void loadRecommended() {
        String url = "http://xhamster.com/recommended_for_me.php";
        Log.i("TEST", "loadRecommended url: " + url);
        String cookie = Helper.getCookie(context);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

            @Override
            public void onResponse(String response) {

                Document doc = Jsoup.parse(response);
                Elements items = doc.select("div.video");

                for (Element tempItem : items) {
                    if (tempItem.hasClass("video")) {
                        Element ele = tempItem.select("img[src]").first();
                        String thumbUrl = ele.attr("src");

                        ele = tempItem.getElementsByTag("u").first();
                        String title = ele.text();

                        ele = tempItem.getElementsByTag("b").first();
                        String runtime = ele.text();

                        ele = tempItem.getElementsByAttribute("href").first();
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

                        ele = tempItem.select("div.fr").first();
                        String rating = ele.text();

                        ele = tempItem.select("div.hRate").first();
                        if (ele == null) {
                            ele = tempItem.select("div.hUnrate").first();
                        }

                        // String[] views = ele.toString().split("Views: ");
                        // views = views[1].split("</div");
                        // String view = views[0].trim();

                        if (id != 0) {
                            XhamsterVideo video = new XhamsterVideo(id);
                            video.setTitle(title);
                            video.setRuntime(runtime);
                            video.setSiteUrl(link);
                            video.setRating(rating);
                            video.setThumbUrl(thumbUrl);
                            video.setViewCount("");
                            mVideoList.add(video);
                        }
                        Log.i("TEST", "Recommended List size: " + mVideoList.size());
                    }
                }
                EventBus.getDefault().post(new EventVideoListLoaded(mVideoList, mode));
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
            }
        }, cookie);

        requestQueue.add(request);
    }

    public void loadSearchVideoList(final String query, final String category, final String sorting, final String date, final String duration, final String quality) {
        final String cookie = Helper.getCookie(context);
        String url = "http://xhamster.com/search.php?qcat=" + category + "&q=" + query + "&page=" + mPage;
        Log.i("TEST", "Searchurl: " + url);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);

                if (firstload) {
                    // GET PAGECOUNT
                    Element pager = doc.select("div.pager").first();
                    if (!(pager == null)) {
                        Elements allnumbers = pager.getElementsByAttribute("href");

                        for (Element i : allnumbers) {
                            try {
                                int number = Integer.valueOf(i.text());
                                mLastpage = number;
                            } catch (NumberFormatException e) {

                            }
                        }

                    }
                    firstload = false;
                }

                // GET VIDEOS
                Elements items = doc.getElementsByClass("video");
                for (Element tempItem : items) {
                    if (tempItem.hasClass("video")) {
                        Element ele = tempItem.select("img[src]").first();
                        String thumbUrl = ele.attr("src");

                        ele = tempItem.getElementsByTag("u").first();
                        String title = ele.text();

                        ele = tempItem.getElementsByTag("b").first();
                        String runtime = ele.text();

                        ele = tempItem.getElementsByAttribute("href").first();
                        String link = null;
                        try {
                            link = ele.attr("href");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("TEST", e.toString());

                        }

                        int id = 0;
                        if (link != null) {
                            String[] ids = link.split("/");
                            id = Integer.valueOf(ids[4]);
                        }

                        ele = tempItem.select("div.fr").first();
                        String rating = ele.text();

                        String view;

                        try {
                            ele = tempItem.getElementsByClass("hRate").first();
                            if (ele.toString().contains("just")) {
                                view = "just added";
                            } else {
                                String[] views = ele.text().split("Views: ");
                                view = views[1];
                            }
                        } catch (Exception e) {
                            view = "0";
                            e.printStackTrace();
                        }

                        if (id != 0) {
                            XhamsterVideo video = new XhamsterVideo(id);
                            video.setTitle(title);
                            video.setRuntime(runtime);
                            video.setSiteUrl(link);
                            video.setRating(rating);
                            video.setThumbUrl(thumbUrl);
                            video.setViewCount(view);
                            mVideoList.add(video);
                        }

                    }
                }
                if (mVideoList.size() > 0 && mPage <= mLastpage) {
                    mPage = mPage + 1;
                }
                EventBus.getDefault().post(new EventVideoListLoaded(mVideoList, mode));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TEST", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("q", query);
                params.put("quality", quality);
                params.put("channelsGoup[0]", "0");
                params.put("channelsGoup[2]", "2");
                params.put("channelsGoup[1]", "1");
                params.put("sort", sorting);
                params.put("date", date);
                params.put("duration", duration);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.102 Safari/537.36");
                params.put("Cookie: ", cookie);
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void loadTopVideoList(String selection) {
        mVideoList.clear();
        String url = "http://xhamster.com/rankings/" + selection + "-top-videos.html";
        Log.i("TEST", "loadTopVideoList url: " + url);
        String cookie = Helper.getCookie(context);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                Document doc = Jsoup.parse(arg0);
                Elements items = doc.select("div.video, div.vDate");

                Element tempItem;

                for (Element item : items) {
                    tempItem = item;
                    if (tempItem.hasClass("video")) {
                        Element ele = tempItem.select("img[src]").first();
                        String thumbUrl = ele.attr("src");

                        ele = tempItem.getElementsByAttribute("title").first();
                        String title = ele.attr("title");

                        ele = tempItem.getElementsByTag("b").first();
                        String runtime = ele.text();

                        ele = tempItem.getElementsByAttribute("href").first();
                        String link = ele.attr("href");

                        String[] ids = link.split("/");
                        int videoID = Integer.valueOf(ids[4]);
                        XhamsterVideo video = new XhamsterVideo(videoID);
                        video.setTitle(title);
                        video.setRuntime(runtime);
                        video.setSiteUrl(link);
                        video.setThumbUrl(thumbUrl);
                        mVideoList.add(video);
                    }
                }
                EventBus.getDefault().post(new EventVideoListLoaded(mVideoList, mode));
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        }, cookie);
        requestQueue.add(request);
    }

    public void loadVideoList() {
        Log.i("TEST", "loadVideoList url: " + buildRequestUrl());
        String cookie = Helper.getCookie(context);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        HeaderStringRequest request = new HeaderStringRequest(buildRequestUrl(), new Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                Document doc = Jsoup.parse(arg0);
                Elements items = doc.select("div.video");

                Element tempItem;

                for (int i = 6; i < items.size(); i++) {
                    tempItem = items.get(i);
                    if (tempItem.hasClass("video")) {
                        Element ele = tempItem.select("img[src]").first();
                        String thumbUrl = ele.attr("src");

                        //ele = tempItem.getElementsByClass("hSprite").first();
                        //String sprite = ele.attr("sprite");

                        ele = tempItem.getElementsByTag("u").first();
                        String title = ele.text();

                        ele = tempItem.getElementsByTag("b").first();
                        String runtime = ele.text();

                        ele = tempItem.getElementsByAttribute("href").first();
                        String link = ele.attr("href");

                        String[] ids = link.split("/");
                        int id = Integer.valueOf(ids[4]);

                        ele = tempItem.select("div.fr").first();
                        String rating = ele.text();

                        String view;

                        try {
                            ele = tempItem.getElementsByClass("hRate").first();
                            if (ele.toString().contains("just")) {
                                view = "just added";
                            } else {
                                String[] views = ele.text().split("Views: ");
                                view = views[1];
                            }
                        } catch (Exception e) {
                            view = "0";
                            e.printStackTrace();
                        }
                        XhamsterVideo video = new XhamsterVideo(id);
                        video.setTitle(title);
                        video.setRuntime(runtime);
                        video.setSiteUrl(link);
                        video.setRating(rating);
                        video.setThumbUrl(thumbUrl);
                        video.setViewCount(view);
                        mVideoList.add(video);

                    }
                }

                if (firstload) {
                    Element pager = doc.select("div.pager").first();
                    if (!(pager == null)) {
                        Elements allnumbers = pager.getElementsByAttribute("href");

                        for (Element i : allnumbers) {
                            try {
                                int number = Integer.valueOf(i.text());
                                mLastpage = number;
                            } catch (NumberFormatException e) {
                            }
                        }
                        if (allnumbers.size() == 0) {
                            mLastpage = 1;
                        }
                    }

                    firstload = false;
                }
                if (mVideoList.size() > 0 && mPage <= mLastpage) {
                    mPage = mPage + 1;
                }
                EventBus.getDefault().post(new EventVideoListLoaded(mVideoList, mode));

            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            }
        }, cookie);
        requestQueue.add(request);

    }

    public void loadMore() {
        if (!firstload) {
            mVideoList.clear();
            loadVideoList();
        }
    }

    public void loadMoreFavorites() {
        if (!firstload) {
            mVideoList.clear();
            loadFavorites();
        }
    }
}
