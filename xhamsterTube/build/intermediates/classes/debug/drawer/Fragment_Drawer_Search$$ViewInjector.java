// Generated code from Butter Knife. Do not modify!
package drawer;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Fragment_Drawer_Search$$ViewInjector {
  public static void inject(Finder finder, final drawer.Fragment_Drawer_Search target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099752, "field 'spinDate'");
    target.spinDate = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131099753, "field 'spinDuration'");
    target.spinDuration = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131099751, "field 'spinSort'");
    target.spinSort = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131099745, "field 'radioType'");
    target.radioType = (android.widget.RadioGroup) view;
    view = finder.findRequiredView(source, 2131099749, "field 'toggleGay'");
    target.toggleGay = (android.widget.ToggleButton) view;
    view = finder.findRequiredView(source, 2131099748, "field 'toggleStraight'");
    target.toggleStraight = (android.widget.ToggleButton) view;
    view = finder.findRequiredView(source, 2131099750, "field 'toggleTrans'");
    target.toggleTrans = (android.widget.ToggleButton) view;
    view = finder.findRequiredView(source, 2131099746, "field 'toggleVideo'");
    target.toggleVideo = (android.widget.RadioButton) view;
    view = finder.findRequiredView(source, 2131099747, "field 'togglePhoto'");
    target.togglePhoto = (android.widget.RadioButton) view;
    view = finder.findRequiredView(source, 2131099744, "field 'txtQuerry'");
    target.txtQuerry = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131099754, "method 'startSearch'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.startSearch();
        }
      });
  }

  public static void reset(drawer.Fragment_Drawer_Search target) {
    target.spinDate = null;
    target.spinDuration = null;
    target.spinSort = null;
    target.radioType = null;
    target.toggleGay = null;
    target.toggleStraight = null;
    target.toggleTrans = null;
    target.toggleVideo = null;
    target.togglePhoto = null;
    target.txtQuerry = null;
  }
}
