package helper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HeaderStringRequest extends StringRequest {

	String cookie = "";
	String text = "";
	String act = "";

	public HeaderStringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
	}

	public HeaderStringRequest(String url, Listener<String> listener, ErrorListener errorListener, String cookie) {
		super(url, listener, errorListener);
		this.cookie = cookie;

	}

	public HeaderStringRequest(String url, Listener<String> listener, ErrorListener errorListener, String cookie, String videoID,
			String text) {
		super(Request.Method.POST, url, listener, errorListener);
		this.cookie = cookie;
		this.text = text;
		this.act = videoID;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.102 Safari/537.36");
		headers.put("Cookie: ", cookie);

		return headers;
	}




}
