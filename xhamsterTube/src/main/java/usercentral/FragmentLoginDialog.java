package usercentral;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.xhamstertube.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import events.EventLogin;
import helper.PreferenceHelper;

public class FragmentLoginDialog extends DialogFragment {
	@InjectView(R.id.btn_login_ok) Button btnOK;
	@InjectView(R.id.btn_login_cancel) Button btnCancel;
	@InjectView(R.id.txtUsername) EditText txtUsername;
	@InjectView(R.id.txtPassword) EditText txtPassword;
	@InjectView(R.id.checkBoxSavePassword) CheckBox mCheckSavePw;
	private PreferenceHelper helper;
	int counter = 0;

	String mUrl = "http://m.xhamster.com/login.html";
	String mCookie;
	String mUserName;
	String mPassword;
	OnEditorActionListener actionListener;
	@InjectView(R.id.player_webview) WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new PreferenceHelper(getActivity());
		int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
		setStyle(style, theme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_login, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mCheckSavePw.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				helper.setSaveLogin(arg1);
			}
		});
		mCheckSavePw.setEnabled(helper.getSaveLogin());
		if (helper.getSaveLogin()) {
			txtUsername.setText(helper.getLoginName());
			txtPassword.setText(helper.getPassword());
		}

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("TEST", url);
				if (url.equals("http://m.xhamster.com/login.html")) {
					mWebView.setVisibility(View.VISIBLE);
				}
				if (url.equals("http://m.xhamster.com/")) {
					CookieManager cookieManager = CookieManager.getInstance();
					CookieSyncManager.getInstance().sync();
					dismiss();
					EventBus.getDefault().post(new EventLogin(true));
					// finish();
				}

				return true;
			}

			public void onPageFinished(WebView view, String url) {
				counter++;
				if (counter > 1) {
					btnOK.setVisibility(View.GONE);
					btnCancel.setVisibility(View.GONE);
					mWebView.setVisibility(View.VISIBLE);
				}
			}

		});

		mWebView.getSettings().setJavaScriptEnabled(true);
		if (Build.VERSION.SDK_INT <= 18) {
			mWebView.getSettings().setSavePassword(false);
		} else {
			// do nothing. because as google mentioned in the documentation -
			// "Saving passwords in WebView will not be supported in future versions"
		}
		mWebView.getSettings().setUserAgentString("Chrome");
		mWebView.loadUrl(mUrl);
		actionListener = new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				boolean handled = false;
				if (arg1 == EditorInfo.IME_ACTION_SEND) {
					hideSoftKeyboard();
					onClickOk();
					handled = true;
				}
				return handled;
			}
		};

		txtUsername.setOnEditorActionListener(actionListener);
		txtPassword.setOnEditorActionListener(actionListener);
	}

	public void hideSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
	}

	@OnClick(R.id.btn_login_ok)
	public void onClickOk() {

		mUserName = txtUsername.getText().toString();
		mPassword = txtPassword.getText().toString();
		mWebView.loadUrl("javascript:" + "document.getElementById('email').value = '" + mUserName + "';"
				+ "document.getElementById('pass').value='" + mPassword + "';");
		mWebView.loadUrl("javascript:var myList = document.getElementsByClassName('form');+" + "myList[0].submit()");

	}
}
