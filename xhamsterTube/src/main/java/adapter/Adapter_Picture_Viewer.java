package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xhamstertube.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dao.XhamsterPicture;

public class Adapter_Picture_Viewer extends ArrayAdapter<XhamsterPicture> {
	private Context mContext;
	int mLayoutResourceId;
	private ArrayList<XhamsterPicture> mPictureList;
    Picasso picasso;

	public Adapter_Picture_Viewer(Context context, int layoutResourceId,
			ArrayList<XhamsterPicture> data) {
		super(context, layoutResourceId, data);
		this.mLayoutResourceId = layoutResourceId;
		this.mContext = context;
		this.mPictureList = data;
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
			holder.imgThumb = (ImageView) convertView
					.findViewById(R.id.grid_item_image);
			holder.txtViews = (TextView) convertView
					.findViewById(R.id.grid_item_views);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.content_item_title);

			convertView.setTag(holder); // set the View holder
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTitle.setText(mPictureList.get(position).getTitle());

		holder.txtTitle.setVisibility(View.GONE);
		holder.txtViews.setText(mPictureList.get(position).getViewCount());

        picasso.load(mPictureList.get(position).getThumbBigUrl())
				.into(holder.imgThumb);

		return convertView;

	}

	public void deleteAll() {
		mPictureList.clear();
		notifyDataSetChanged();
	}

	static class ViewHolder {
		public TextView txtTitle, txtViews;
		public ImageView imgThumb;
	}

}