// Generated code from Butter Knife. Do not modify!
package picture;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_Picture_Gallerys_Top$$ViewInjector {
  public static void inject(Finder finder, final picture.Fragment_Picture_Gallerys_Top target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099796, "field 'mGridView'");
    target.mGridView = (android.widget.GridView) view;
    view = finder.findRequiredView(source, 2131099797, "field 'progressBar'");
    target.progressBar = (android.widget.ProgressBar) view;
  }

  public static void reset(picture.Fragment_Picture_Gallerys_Top target) {
    target.mGridView = null;
    target.progressBar = null;
  }
}
