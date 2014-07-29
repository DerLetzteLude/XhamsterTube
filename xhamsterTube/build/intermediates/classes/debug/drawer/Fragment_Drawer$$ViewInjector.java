// Generated code from Butter Knife. Do not modify!
package drawer;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_Drawer$$ViewInjector {
  public static void inject(Finder finder, final drawer.Fragment_Drawer target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099788, "field 'spinCategories'");
    target.spinCategories = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131099769, "field 'rowVideoRecommended' and method 'onClickRecommended'");
    target.rowVideoRecommended = (android.widget.TableRow) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickRecommended();
        }
      });
    view = finder.findRequiredView(source, 2131099773, "field 'rowVideoFavorites' and method 'onClickVideoFavorites'");
    target.rowVideoFavorites = (android.widget.TableRow) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickVideoFavorites();
        }
      });
    view = finder.findRequiredView(source, 2131099768, "field 'layoutVideos'");
    target.layoutVideos = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131099779, "field 'layoutPictures'");
    target.layoutPictures = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131099761, "field 'layoutUserCentral'");
    target.layoutUserCentral = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131099759, "field 'headerUserCentral' and method 'onUserCentralHeaderClick'");
    target.headerUserCentral = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onUserCentralHeaderClick();
        }
      });
    view = finder.findRequiredView(source, 2131099789, "field 'btnSearch' and method 'onBtnSearchClick'");
    target.btnSearch = (com.cengalabs.flatui.views.FlatButton) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onBtnSearchClick();
        }
      });
    view = finder.findRequiredView(source, 2131099760, "field 'imgUserCentral'");
    target.imgUserCentral = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131099767, "field 'imgvideos'");
    target.imgvideos = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131099778, "field 'imgPictures'");
    target.imgPictures = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131099766, "method 'onVideoHeaderClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onVideoHeaderClick();
        }
      });
    view = finder.findRequiredView(source, 2131099777, "method 'onPictureHeaderClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onPictureHeaderClick();
        }
      });
    view = finder.findRequiredView(source, 2131099771, "method 'onClickVideosTop'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickVideosTop();
        }
      });
    view = finder.findRequiredView(source, 2131099784, "method 'onClickPictureFavorites'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickPictureFavorites();
        }
      });
    view = finder.findRequiredView(source, 2131099762, "method 'onCLickContentFeed'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onCLickContentFeed();
        }
      });
    view = finder.findRequiredView(source, 2131099780, "method 'onPictuesNew'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onPictuesNew();
        }
      });
    view = finder.findRequiredView(source, 2131099775, "method 'onClickVideosCategorys'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickVideosCategorys();
        }
      });
    view = finder.findRequiredView(source, 2131099786, "method 'onClickPictureCategory'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickPictureCategory();
        }
      });
    view = finder.findRequiredView(source, 2131099782, "method 'onClickPictureTop'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickPictureTop();
        }
      });
  }

  public static void reset(drawer.Fragment_Drawer target) {
    target.spinCategories = null;
    target.rowVideoRecommended = null;
    target.rowVideoFavorites = null;
    target.layoutVideos = null;
    target.layoutPictures = null;
    target.layoutUserCentral = null;
    target.headerUserCentral = null;
    target.btnSearch = null;
    target.imgUserCentral = null;
    target.imgvideos = null;
    target.imgPictures = null;
  }
}
