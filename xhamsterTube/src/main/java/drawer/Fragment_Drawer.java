package drawer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatButton;
import com.example.xhamstertube.ActivitySearch;
import com.example.xhamstertube.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import events.EventChangeFragment;
import events.EventLogin;
import helper.Helper;
import picture.Fragment_Picture_Gallery_Favorites;
import picture.Fragment_Picture_Gallerys;
import picture.Fragment_Picture_Gallerys_Top;
import siteinteraction.XhamsterGalleryListLoader;
import siteinteraction.XhamsterVideoListLoader;
import usercentral.Fragment_usercentral_ContentFeed;
import videos.Fragment_Video_Favorites;
import videos.Fragment_Video_Top;
import videos.Fragment_Videos_Grid;
import videos.Fragment_Videos_Recommended;

public class Fragment_Drawer extends Fragment {
    @InjectView(R.id.drawer_spinner) Spinner spinCategories;
    @InjectView(R.id.drawer_row_video_recommended) TableRow rowVideoRecommended;
    @InjectView(R.id.drawer_row_video_favorites) TableRow rowVideoFavorites;
    @InjectView(R.id.drawer_layout_videos) LinearLayout layoutVideos;
    @InjectView(R.id.drawer_layout_pictures) LinearLayout layoutPictures;
    @InjectView(R.id.drawer_layout_usercentral) LinearLayout layoutUserCentral;
    @InjectView(R.id.drawer_header_UserCentral) TextView headerUserCentral;
    @InjectView(R.id.btn_search) FlatButton btnSearch;
    @InjectView(R.id.imgarrowusercentral) ImageView imgUserCentral;
    @InjectView(R.id.imgarrowvideos) ImageView imgvideos;
    @InjectView(R.id.imgarrowpictures) ImageView imgPictures;
    String[] mUngeschnittenListe = null;
    String[] mListeDrawerAnzeige = null;
    EventBus bus = EventBus.getDefault();
    private String mOrientation;
    private SharedPreferences mSharedPref;
    private boolean loggedIn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bus.register(this);
        mSharedPref = getActivity().getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        mOrientation = mSharedPref.getString("preference_categories", "Straight");
        ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spinCategories.getAdapter();
        int spinnerPosition = myAdap.getPosition(mOrientation);
        spinCategories.setSelection(spinnerPosition);
        spinCategories.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                SharedPreferences.Editor prefsEditor = mSharedPref.edit();
                String auswahl = (String) spinCategories.getItemAtPosition(arg2);
                prefsEditor.putString("preference_categories", auswahl);
                prefsEditor.apply();
                mOrientation = auswahl;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLoginState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public String getCategorie() {
        return mOrientation;
    }

    public void showVideos() {
        layoutVideos.setVisibility(View.VISIBLE);
        imgvideos.setImageResource(R.drawable.arrow_down);
    }

    public void showPictures() {
        layoutPictures.setVisibility(View.VISIBLE);
        imgPictures.setImageResource(R.drawable.arrow_down);
    }

    public void hideVideo() {
        layoutVideos.setVisibility(View.GONE);
        imgvideos.setImageResource(R.drawable.arrow_up);
    }

    public void hidePictures() {
        layoutPictures.setVisibility(View.GONE);
        imgPictures.setImageResource(R.drawable.arrow_up);
    }

    public void showUserCentral() {
        layoutUserCentral.setVisibility(View.VISIBLE);
        imgUserCentral.setImageResource(R.drawable.arrow_down);
    }

    public void hideUserCentral() {
        layoutUserCentral.setVisibility(View.GONE);
        imgUserCentral.setImageResource(R.drawable.arrow_up);
    }

    @OnClick(R.id.btn_search)
    public void onBtnSearchClick() {
        Intent inte = new Intent(getActivity(), ActivitySearch.class);
        getActivity().startActivity(inte);
    }

    @OnClick(R.id.drawer_header_videos)
    public void onVideoHeaderClick() {
        if (layoutVideos.getVisibility() == View.VISIBLE) {
            hideVideo();
        } else {
            hidePictures();
            hideUserCentral();
            if (loggedIn) {
                rowVideoRecommended.setVisibility(View.VISIBLE);
            }
            showVideos();
        }
    }

    @OnClick(R.id.drawer_header_pictures)
    public void onPictureHeaderClick() {
        if (layoutPictures.getVisibility() == View.VISIBLE) {
            hidePictures();
        } else {
            hideUserCentral();
            hideVideo();
            showPictures();
        }
    }

    @OnClick(R.id.drawer_header_UserCentral)
    public void onUserCentralHeaderClick() {
        if (layoutUserCentral.getVisibility() == View.VISIBLE) {
            hideUserCentral();
        } else {
            hidePictures();
            hideVideo();
            showUserCentral();
        }
    }

    @OnClick(R.id.drawer_row_video_favorites)
    public void onClickVideoFavorites() {
        Fragment fragment = new Fragment_Video_Favorites();
        bus.post(new EventChangeFragment(fragment));
    }

    @OnClick(R.id.drawer_row_video_recommended)
    public void onClickRecommended() {
        Fragment fragment = new Fragment_Videos_Recommended();
        bus.post(new EventChangeFragment(fragment));
    }

    @OnClick(R.id.drawer_row_video_top)
    public void onClickVideosTop() {
        Fragment_Video_Top topfragment = new Fragment_Video_Top();
        bus.post(new EventChangeFragment(topfragment));
    }

    @OnClick(R.id.drawer_row_pictures_favoriten)
    public void onClickPictureFavorites() {
        Fragment fragment = new Fragment_Picture_Gallery_Favorites();
        bus.post(new EventChangeFragment(fragment));
    }

    @OnClick(R.id.drawer_row_usercentral_content_feed)
    public void onCLickContentFeed() {
        Fragment fragment = new Fragment_usercentral_ContentFeed();
        bus.post(new EventChangeFragment(fragment));
    }

    @OnClick(R.id.drawer_row_pictures_new)
    public void onPictuesNew() {
        Fragment_Picture_Gallerys pictureFragment = new Fragment_Picture_Gallerys();
        Bundle data = new Bundle();
        data.putInt("mode", XhamsterGalleryListLoader.MODE_NEW);
        data.putString("query", "new");
        pictureFragment.setArguments(data);
        bus.post(new EventChangeFragment(pictureFragment));
    }

    @OnClick((R.id.drawer_row_video_categories))
    public void onClickVideosCategorys() {
        String[] categories = getResources().getStringArray(R.array.orientation);
        if (mOrientation.equals(categories[0])) {
            mUngeschnittenListe = getResources().getStringArray(R.array.categories_straight);
        }
        if (mOrientation.equals(categories[1])) {
            mUngeschnittenListe = getResources().getStringArray(R.array.categories_gay);
        }
        if (mOrientation.equals(categories[2])) {
            mUngeschnittenListe = getResources().getStringArray(R.array.categories_transsexual);
        }

        ArrayList<String> listeSpinnerAnzeige = new ArrayList<String>();

        for (String tempString : mUngeschnittenListe) {
            String[] teil = tempString.split(",");
            listeSpinnerAnzeige.add(teil[0]);
        }

        mListeDrawerAnzeige = listeSpinnerAnzeige.toArray(new String[listeSpinnerAnzeige.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mOrientation + " Categories").setItems(mListeDrawerAnzeige, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                Fragment fragment = new Fragment_Videos_Grid();
                String query = Helper.getCategorieString(mUngeschnittenListe[position]);
                Bundle data = new Bundle();
                data.putInt("mode", XhamsterVideoListLoader.MODE_CHANNELS);
                data.putString("query", query);
                fragment.setArguments(data);
                bus.post(new EventChangeFragment(fragment));
                getActivity().getActionBar().setTitle(mListeDrawerAnzeige[position]);
            }
        });
        builder.create();
        builder.show();

    }

    @OnClick(R.id.drawer_row_pictures_categories)
    public void onClickPictureCategory() {
        ArrayList<String> listeSpinnerAnzeige = new ArrayList<String>();
        mUngeschnittenListe = getResources().getStringArray(R.array.categories_pictures);
        for (String tempString : mUngeschnittenListe) {
            String[] teil = tempString.split(",");
            listeSpinnerAnzeige.add(teil[0]);
        }
        mListeDrawerAnzeige = listeSpinnerAnzeige.toArray(new String[listeSpinnerAnzeige.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mOrientation + " Categories").setItems(mListeDrawerAnzeige, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                Fragment fragment = new Fragment_Picture_Gallerys();
                String query = Helper.getCategorieString(mUngeschnittenListe[position]);
                Bundle data = new Bundle();
                data.putInt("mode", XhamsterGalleryListLoader.MODE_CHANNELS);
                data.putString("query", query);
                fragment.setArguments(data);
                bus.post(new EventChangeFragment(fragment));
                getActivity().getActionBar().setTitle(mListeDrawerAnzeige[position]);
            }
        });
        builder.create();
        builder.show();
    }

    @OnClick(R.id.drawer_row_pictures_tops)
    public void onClickPictureTop(){
        Fragment_Picture_Gallerys_Top pictureFragment = new Fragment_Picture_Gallerys_Top();
        bus.post(new EventChangeFragment(pictureFragment));
    }

    public void checkLoginState() {
        Log.i("TEST", "Logincheck");
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(".xhamster.com");

        if (cookie == null) {
            loggedIn = false;
            headerUserCentral.setVisibility(View.GONE);
            rowVideoFavorites.setVisibility(View.GONE);

        } else {
            loggedIn = true;
            headerUserCentral.setVisibility(View.VISIBLE);
            rowVideoFavorites.setVisibility(View.VISIBLE);
        }
    }

    public void onEvent(EventLogin event) {
        checkLoginState();
    }

}
