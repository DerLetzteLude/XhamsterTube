// Generated code from Butter Knife. Do not modify!
package usercentral;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_UserInfo$$ViewInjector {
  public static void inject(Finder finder, final usercentral.Fragment_UserInfo target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099792, "field 'webview1'");
    target.webview1 = (android.webkit.WebView) view;
    view = finder.findRequiredView(source, 2131099793, "field 'errorText'");
    target.errorText = (android.widget.TextView) view;
  }

  public static void reset(usercentral.Fragment_UserInfo target) {
    target.webview1 = null;
    target.errorText = null;
  }
}
