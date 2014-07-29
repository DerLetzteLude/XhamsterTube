// Generated code from Butter Knife. Do not modify!
package com.example.xhamstertube;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ActivityVideoplayer$$ViewInjector {
  public static void inject(Finder finder, final com.example.xhamstertube.ActivityVideoplayer target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099742, "field 'relatedList'");
    target.relatedList = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131099730, "field 'drawer'");
    target.drawer = (android.support.v4.widget.DrawerLayout) view;
    view = finder.findRequiredView(source, 2131099737, "field 'mVideoView'");
    target.mVideoView = (android.widget.VideoView) view;
    view = finder.findRequiredView(source, 2131099738, "field 'progress'");
    target.progress = (android.widget.ProgressBar) view;
  }

  public static void reset(com.example.xhamstertube.ActivityVideoplayer target) {
    target.relatedList = null;
    target.drawer = null;
    target.mVideoView = null;
    target.progress = null;
  }
}
