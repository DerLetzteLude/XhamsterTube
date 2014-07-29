// Generated code from Butter Knife. Do not modify!
package usercentral;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FragmentLoginDialog$$ViewInjector {
  public static void inject(Finder finder, final usercentral.FragmentLoginDialog target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099721, "field 'btnOK' and method 'onClickOk'");
    target.btnOK = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClickOk();
        }
      });
    view = finder.findRequiredView(source, 2131099720, "field 'btnCancel'");
    target.btnCancel = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131099724, "field 'txtUsername'");
    target.txtUsername = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131099727, "field 'txtPassword'");
    target.txtPassword = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131099729, "field 'mCheckSavePw'");
    target.mCheckSavePw = (android.widget.CheckBox) view;
    view = finder.findRequiredView(source, 2131099728, "field 'mWebView'");
    target.mWebView = (android.webkit.WebView) view;
  }

  public static void reset(usercentral.FragmentLoginDialog target) {
    target.btnOK = null;
    target.btnCancel = null;
    target.txtUsername = null;
    target.txtPassword = null;
    target.mCheckSavePw = null;
    target.mWebView = null;
  }
}
