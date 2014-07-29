package siteinteraction;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import dao.XhamsterComment;
import dao.XhamsterVideo;
import de.greenrobot.event.EventBus;
import events.EventCommentsLoaded;
import events.EventRelatedVideosLoaded;
import events.EventVideoLoadError;
import events.EventVideoLoaded;
import helper.HeaderStringRequest;
import helper.Helper;
import helper.VolleySingleton;

public class XhamsterVideoLoader {
    Context context;
    String mCookie = "";
    XhamsterVideo mvideo;
    ArrayList<XhamsterVideo> mRelatedList;
    ArrayList<XhamsterComment> mCommentList;
    int currentCommentPage = 1;
    String commentText;
    WebView mWebView;

    public XhamsterVideoLoader(Context context) {
        this.context = context;
        mRelatedList = new ArrayList<XhamsterVideo>();
        mCommentList = new ArrayList<XhamsterComment>();
        mvideo = new XhamsterVideo();
    }

    public void setCookie(String cookie) {
        mCookie = cookie;
    }

    public void submitComment(String inp) {
        Log.i("TEST", "submitComment");
        commentText = inp;
        mWebView = new WebView(context);

        mWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT <= 18) {
            mWebView.getSettings().setSavePassword(false);
        } else {
            // do nothing. because as google mentioned in the documentation -
            // "Saving passwords in WebView will not be supported in future versions"
        }
        mWebView.getSettings().setUserAgentString("Chrome");
        mWebView.setWebViewClient(new WebViewClient() {
            boolean loaded = false;

            public void onPageFinished(WebView view, String url) {
                Log.i("TEST", "onPageFinished siteurl: " + url);
                if (!loaded) {
                    mWebView.loadUrl("javascript:" + "document.getElementById('commentInput').value = '" + commentText + "';");
                    mWebView.loadUrl("javascript:(function(){" + "l=document.getElementById('commentSend');"
                            + "e=document.createEvent('HTMLEvents');" + "e.initEvent('click',true,true);" + "l.dispatchEvent(e);" + "})()");
                    loaded = true;
                }
            }

        });
        String url = mvideo.getSiteUrl();
        url = "http://m." + url.substring(7);
        Log.i("TEST", "loadUrl " + url);
        mWebView.loadUrl(url);
    }

    public void loadMoreComments() {
        mCommentList.clear();
        if (mvideo.getCommentCount() / 30 >= currentCommentPage) {
            String url = "http://xhamster.com/ajax/comment2.php?act=video%3A" + mvideo.getId() + "%3A" + (currentCommentPage + 1);
            Log.i("TEST", url);
            String cookie = Helper.getCookie(context);
            RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
            HeaderStringRequest request = new HeaderStringRequest(url, new Listener<String>() {

                @Override
                public void onResponse(String arg0) {
                    Document doc = Jsoup.parse(arg0);
                    Elements commentElements = doc.getElementsByClass("item");
                    for (Element tempItem : commentElements) {
                        XhamsterComment com = new XhamsterComment();
                        // TEXT
                        Element ele = tempItem.getElementsByClass("txt").first();
                        String text = ele.text();
                        String[] splited = text.split("http");
                        if (splited[0] != null) {
                            text = splited[0];
                            com.setText(text);
                        }

                        // USERTHUMB
                        ele = tempItem.getElementsByClass("thumb").first();
                        if (ele != null) {
                            String[] split = ele.toString().split("<img src=\"");
                            if (split[1] != null) {
                                split = split[1].split("\"");
                            }
                            String thumbUrl = split[0];
                            com.setThumbUrl(thumbUrl);
                        }

                        // USERNAME und USERLink
                        ele = tempItem.getElementsByClass("name").first();
                        if (ele != null) {
                            String name = ele.attr("hint");
                            if (name == "") {
                                name = ele.text();
                            }
                            com.setUserName(name);

                            String userlink = ele.attr("href");
                            com.setUserUrl(userlink);
                        }

                        // USERGENDER
                        ele = tempItem.getElementsByClass("di").first();
                        if (ele != null) {
                            ele = ele.child(0);
                            String gender = ele.attr("hint");
                            if (gender.contains("Transsexual")) {
                                com.setUsergender("http://www.uvm.edu/~lgbtqa/images/trans280.png");
                            }
                            if (gender.contains("Male")) {
                                com.setUsergender("http://commons.wikimedia.org/wiki/File:Symbol_mars.png");
                            }
                            if (gender.contains("Couple")) {
                                com.setUsergender("http://upload.wikimedia.org/wikipedia/commons/thumb/2/24/HeteroSym-pinkblue2.svg/525px-HeteroSym-pinkblue2.svg.png");
                            }
                            if (gender.contains("Female")) {
                                com.setUsergender("http://upload.wikimedia.org/wikipedia/commons/6/63/FemalePink.png");
                            }
                        }

                        // added time
                        ele = tempItem.getElementsByClass("tool").first();
                        if (ele != null) {
                            String[] split = ele.toString().split("<span>");
                            if (split[1] != null) {
                                split = split[1].split("</span>");
                                com.setPostedtime(split[0]);
                            }
                        }
                        mCommentList.add(com);
                    }
                    currentCommentPage = currentCommentPage + 1;
                    EventBus.getDefault().post(new EventCommentsLoaded(mCommentList));
                }
            }, new ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub

                }
            }, cookie);
            requestQueue.add(request);
        }
    }

    public void loadVideo(XhamsterVideo video) {
        mvideo = video;
        String cookie = Helper.getCookie(context);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        HeaderStringRequest request = new HeaderStringRequest(video.getSiteUrl(), new Listener<String>() {

            @Override
            public void onResponse(String arg0) {

                if (arg0.contains("This video was deleted")) {
                    EventBus.getDefault().post(new EventVideoLoadError("This video was deleted"));
                    Log.i("TEST", "This video was deleted ");

                } else if (arg0.contains("This video is visible for") && arg0.contains("friends only")) {
                    EventBus.getDefault().post(new EventVideoLoadError("That Video is for " + mvideo.getUserName() + "s friends only"));
                    Log.i("TEST", "Friends only");

                } else if (arg0.contains("This video needs password")) {
                    EventBus.getDefault().post(new EventVideoLoadError("This video needs password"));
                    Log.i("TEST", "This video needs password");

                } else {
                    Log.i("TEST", "onResponse ");
                    Document doc = Jsoup.parse(arg0);

                    // VIDEO DATEN LADEN

                    Element item = doc.select("div.noFlash").first();
                    item = item.getElementsByAttribute("href").first();
                    String link = item.attr("href");

                    item = doc.select("div.item").first();
                    if (item.toString().contains("anonymous")) {
                    } else {
                        item = item.getElementsByAttribute("href").first();
                        mvideo.setUserName(item.text());

                        String relHref = item.attr("href");
                        String mUserLink = "http://xhamster.com/" + relHref;
                        mvideo.setUserURL(mUserLink);

                    }

                    item = doc.getElementById("playerBox");
                    item = item.child(0);
                    if (item != null) {
                        mvideo.setTitle( item.text());
                    }

                    if (link == "") {
                        return;
                    } else {
                        mvideo.setStreamUrl(link);
                    }

                    // Comment Count
                    item = doc.select("#commentBox").first();
                    if (item != null) {
                        item = item.child(0);
                    }
                    if (item != null) {
                        String[] split = item.toString().split("\\(");
                        if (split.length > 1) {
                            split = split[1].split("\\)");
                            if (split[0] != null) {
                                mvideo.setCommentCount(Integer.valueOf(split[0]));
                            }
                        } else {
                            Log.i("TEST", "keine comments");
                        }
                    }

                    // RELATED VIDEOS LADEN
                    Elements relatedElements = doc.getElementsByClass("video");
                    Log.i("TEST", "RELATED VIDEOS: " + relatedElements.size());
                    for (Element tempItem : relatedElements) {
                        Element ele = tempItem.select("img[src]").first();
                        String thumbUrl = ele.attr("src");

                        ele = tempItem.getElementsByTag("u").first();
                        String title = ele.text();

                        ele = tempItem.getElementsByTag("b").first();
                        String runtime = ele.text();

                        ele = tempItem.getElementsByAttribute("href").first();
                        String templink = ele.attr("href");

                        String[] ids = templink.split("/");
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

                        XhamsterVideo related = new XhamsterVideo(id);
                        related.setTitle(title);
                        related.setRuntime(runtime);
                        related.setSiteUrl(templink);
                        related.setRating(rating);
                        related.setThumbUrl(thumbUrl);
                        related.setViewCount(view);
                        mRelatedList.add(related);
                    }

                    // Comments Laden
                    Element commentBlock = doc.select("#commentList").first();
                    Elements commentElements = commentBlock.getElementsByClass("item");
                    Log.i("TEST", "Comments: " + commentElements.size());
                    for (Element tempItem : commentElements) {
                        XhamsterComment com = new XhamsterComment();
                        // TEXT
                        Element ele = tempItem.getElementsByClass("txt").first();
                        String text = ele.text();
                        com.setText(text);

                        // USERTHUMB
                        ele = tempItem.getElementsByClass("thumb").first();
                        // Log.i("TEST", "ele: "+ele.toString());
                        if (ele != null) {
                            String[] split = ele.toString().split("<img src=\"");
                            if (split[1] != null) {
                                split = split[1].split("\"");
                            }
                            String thumbUrl = split[0];
                            com.setThumbUrl(thumbUrl);
                        }

                        // USERNAME und USERLink
                        ele = tempItem.getElementsByClass("name").first();
                        if (ele != null) {
                            String name = ele.attr("hint");
                            if (name == "") {
                                name = ele.text();
                            }
                            com.setUserName(name);

                            String userlink = ele.attr("href");
                            com.setUserUrl(userlink);
                        }

                        // USERGENDER
                        ele = tempItem.getElementsByClass("di").first();
                        if (ele != null) {
                            ele = ele.child(0);
                            String gender = ele.attr("hint");
                            if (gender.contains("Transsexual")) {
                                com.setUsergender("http://www.uvm.edu/~lgbtqa/images/trans280.png");
                            }
                            if (gender.contains("Male")) {
                                com.setUsergender("http://commons.wikimedia.org/wiki/File:Symbol_mars.png");
                            }
                            if (gender.contains("Couple")) {
                                com.setUsergender("http://upload.wikimedia.org/wikipedia/commons/thumb/2/24/HeteroSym-pinkblue2.svg/525px-HeteroSym-pinkblue2.svg.png");
                            }
                            if (gender.contains("Female")) {
                                com.setUsergender("http://upload.wikimedia.org/wikipedia/commons/6/63/FemalePink.png");
                            }
                        }

                        // added time
                        ele = tempItem.getElementsByClass("tool").first();
                        if (ele != null) {
                            String[] split = ele.toString().split("<span>");
                            if (split[1] != null) {
                                split = split[1].split("</span>");
                                com.setPostedtime(split[0]);
                            }
                        }
                        mCommentList.add(com);
                    }
                    EventBus.getDefault().post(new EventCommentsLoaded(mCommentList));
                    EventBus.getDefault().post(new EventVideoLoaded(mvideo));
                    EventBus.getDefault().post(new EventRelatedVideosLoaded(mRelatedList));

                }

            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        }, cookie);
        requestQueue.add(request);

    }
}
