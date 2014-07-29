// Generated code from Butter Knife. Do not modify!
package com.example.xhamstertube;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.example.xhamstertube.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099730, "field 'mDrawerLayout'");
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
    view = finder.findRequiredView(source, 2131099732, "field 'mDrawerList'");
    target.mDrawerList = (android.widget.FrameLayout) view;
    view = finder.findRequiredView(source, 2131099731, "field 'mContentFrame'");
    target.mContentFrame = (android.widget.FrameLayout) view;
  }

  public static void reset(com.example.xhamstertube.MainActivity target) {
    target.mDrawerLayout = null;
    target.mDrawerList = null;
    target.mContentFrame = null;
  }
}
