// Generated code from Butter Knife. Do not modify!
package picture;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_Picture_Gallery_Favorites$$ViewInjector {
  public static void inject(Finder finder, final picture.Fragment_Picture_Gallery_Favorites target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099796, "field 'mGridView'");
    target.mGridView = (android.widget.GridView) view;
    view = finder.findRequiredView(source, 2131099795, "field 'mLayout'");
    target.mLayout = (android.widget.FrameLayout) view;
  }

  public static void reset(picture.Fragment_Picture_Gallery_Favorites target) {
    target.mGridView = null;
    target.mLayout = null;
  }
}
