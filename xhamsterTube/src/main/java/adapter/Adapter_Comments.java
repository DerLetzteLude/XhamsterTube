package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xhamstertube.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dao.XhamsterComment;
import helper.SquareImageView;

public class Adapter_Comments extends ArrayAdapter<XhamsterComment> {
	private Context mContext;
	int mLayoutResourceId;
	private ArrayList<XhamsterComment> mCommentList;
    Picasso picasso;

	public Adapter_Comments(Context context, int layoutResourceId, ArrayList<XhamsterComment> data) {
		super(context, layoutResourceId, data);
		this.mLayoutResourceId = layoutResourceId;
		this.mContext = context;
		this.mCommentList = data;
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpDownloader downloader = new OkHttpDownloader(okHttpClient);
        picasso = new Picasso.Builder(mContext).downloader(downloader).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, mLayoutResourceId, null);
			holder = new ViewHolder();
			holder.txtText = (TextView) convertView.findViewById(R.id.comment_text);
			holder.txtUsername = (TextView) convertView.findViewById(R.id.commentUsername);
			holder.txtAddedTime = (TextView) convertView.findViewById(R.id.commentAddedAtText);
			holder.imgUserPicture = (SquareImageView) convertView.findViewById(R.id.commentUserImage);

			convertView.setTag(holder); // set the View holder
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtText.setText(mCommentList.get(position).getText());
		holder.txtUsername.setText(mCommentList.get(position).getUserName());
		holder.txtAddedTime.setText(mCommentList.get(position).getPostedtime());
        picasso.load(mCommentList.get(position).getThumbUrl()).fit().into(holder.imgUserPicture);
		return convertView;

	}

	public void deleteAll() {
		mCommentList.clear();
		notifyDataSetChanged();
	}

	static class ViewHolder {
		public TextView txtText, txtUsername, txtAddedTime;
		public SquareImageView imgUserPicture;

	}

}