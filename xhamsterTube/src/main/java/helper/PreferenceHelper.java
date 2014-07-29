package helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
	private static final String ARG_SAVELOGIN = "ARGsavelogin";
	private static final String PREFS_NAME = "HamsterPrefs";
	private static final String ARG_LOGINNAME = "ARGloginname";
	private static final String ARG_PASSWORD = "ARGpassword";

	private SharedPreferences sPreferences;

	public PreferenceHelper(Context context) {
		super();
		sPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	}

	public boolean getSaveLogin() {
		return sPreferences.getBoolean(ARG_SAVELOGIN, false);
	}

	public void setSaveLogin(boolean save) {
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.putBoolean(ARG_SAVELOGIN, save);
		editor.apply();
	}

	public String getLoginName() {
		return sPreferences.getString(ARG_LOGINNAME, "");
	}

	public void setLogin(String name) {
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.putString(ARG_LOGINNAME, name);
		editor.apply();
	}

	public String getPassword() {
		return sPreferences.getString(ARG_PASSWORD, "");
	}

	public void setPassword(String password) {
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.putString(ARG_PASSWORD, password);
		editor.apply();
	}

}
