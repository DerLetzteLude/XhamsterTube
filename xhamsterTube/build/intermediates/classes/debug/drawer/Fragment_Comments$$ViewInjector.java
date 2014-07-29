// Generated code from Butter Knife. Do not modify!
package drawer;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_Comments$$ViewInjector {
  public static void inject(Finder finder, final drawer.Fragment_Comments target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099756, "field 'listComments'");
    target.listComments = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131099757, "field 'txtComment'");
    target.txtComment = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131099758, "field 'empty'");
    target.empty = (android.widget.TextView) view;
  }

  public static void reset(drawer.Fragment_Comments target) {
    target.listComments = null;
    target.txtComment = null;
    target.empty = null;
  }
}
