// Generated code from Butter Knife. Do not modify!
package videos;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_Videos_Recommended$$ViewInjector {
  public static void inject(Finder finder, final videos.Fragment_Videos_Recommended target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099796, "field 'mGridView'");
    target.mGridView = (android.widget.GridView) view;
    view = finder.findRequiredView(source, 2131099797, "field 'progressBar'");
    target.progressBar = (android.widget.ProgressBar) view;
  }

  public static void reset(videos.Fragment_Videos_Recommended target) {
    target.mGridView = null;
    target.progressBar = null;
  }
}