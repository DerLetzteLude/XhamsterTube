// Generated code from Butter Knife. Do not modify!
package picture;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_SinglePicture$$ViewInjector {
  public static void inject(Finder finder, final picture.Fragment_SinglePicture target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099791, "field 'imageView'");
    target.imageView = (android.widget.ImageView) view;
  }

  public static void reset(picture.Fragment_SinglePicture target) {
    target.imageView = null;
  }
}
