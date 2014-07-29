package usercentral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.xhamstertube.R;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterUser;
import de.greenrobot.event.EventBus;
import events.EventUserInfoLoadError;
import events.EventUserInfoLoaded;
import siteinteraction.XhamsterUserInfoLoader;

public class Fragment_UserInfo extends Fragment {
	@InjectView(R.id.userWerbview) WebView webview1;
	@InjectView(R.id.usererrortext) TextView errorText;
	XhamsterUser mUser;
	MenuItem mMenuRelated;
	XhamsterUserInfoLoader mLoader;
	EventBus bus = EventBus.getDefault();

	public static Fragment newInstance(String gsonString) {
		Fragment_UserInfo myFragment = new Fragment_UserInfo();
		Bundle args = new Bundle();
		args.putString("USER", gsonString);
		myFragment.setArguments(args);
		return myFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bus.register(this);
		setHasOptionsMenu(true);
		String jsonUser = getArguments().getString("USER");
		Gson gson = new Gson();
		mUser = gson.fromJson(jsonUser, XhamsterUser.class);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_userinfo, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	public void onEvent(EventUserInfoLoaded event) {
		webview1.setVisibility(View.VISIBLE);
		webview1.loadData(event.getUser().getInfoBlock(), "text/html; charset=UTF-8", null);
	}

	public void onEvent(EventUserInfoLoadError event) {
		errorText.setText(event.getText());
	}
}
