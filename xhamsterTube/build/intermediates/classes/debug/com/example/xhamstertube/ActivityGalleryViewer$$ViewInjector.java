// Generated code from Butter Knife. Do not modify!
package com.example.xhamstertube;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ActivityGalleryViewer$$ViewInjector {
  public static void inject(Finder finder, final com.example.xhamstertube.ActivityGalleryViewer target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099715, "field 'pager'");
    target.pager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(com.example.xhamstertube.ActivityGalleryViewer target) {
    target.pager = null;
  }
}
