package drawer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.xhamstertube.R;

import java.util.ArrayList;

import adapter.Adapter_Comments;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dao.XhamsterComment;
import de.greenrobot.event.EventBus;
import events.EventCommentsLoaded;
import events.EventLoadComments;
import events.EventSubmitComment;
import helper.EndlessScrollListener;

public class Fragment_Comments extends Fragment {
	@InjectView(R.id.commentlist) ListView listComments;
	@InjectView(R.id.textCommentInput) EditText txtComment;
	@InjectView(R.id.comments_empty) TextView empty;
	EventBus bus = EventBus.getDefault();
	private Adapter_Comments mCommentAdaper;
	private ArrayList<XhamsterComment> mCommentList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_comments, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	public static Fragment_Comments newInstance() {
		Fragment_Comments myFragment = new Fragment_Comments();
		return myFragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		listComments.setAdapter(mCommentAdaper);
		listComments.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				EventBus.getDefault().post(new EventLoadComments());
			}
		});
		txtComment.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					EventBus.getDefault().post(new EventSubmitComment(v.getText().toString()));
					txtComment.setVisibility(View.GONE);
				}
				return handled;
			}
		});
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bus.register(this);
		mCommentList = new ArrayList<XhamsterComment>();
		mCommentAdaper = new Adapter_Comments(getActivity(), R.layout.listitem_comment, mCommentList);

	}

	public void onEvent(EventCommentsLoaded event) {
		if (event.getList().size() > 0) {
			mCommentAdaper.addAll(event.getList());
			empty.setVisibility(View.GONE);
		} else {
			txtComment.setVisibility(View.GONE);
		}

	}
}
