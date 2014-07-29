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

import dao.XhamsterVideo;

public class Adapter_Videos_Grid extends ArrayAdapter<XhamsterVideo> {
	private Context mContext;
	int mLayoutResourceId;
	private ArrayList<XhamsterVideo> mVideoList;
	ViewHolder holder;
    Picasso picasso;

	public Adapter_Videos_Grid(Context context, int layoutResourceId, ArrayList<XhamsterVideo> data) {
		super(context, layoutResourceId, data);
		this.mLayoutResourceId = layoutResourceId;
		this.mContext = context;
		this.mVideoList = data;
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpDownloader downloader = new OkHttpDownloader(okHttpClient);
        picasso = new Picasso.Builder(mContext).downloader(downloader).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(mContext, mLayoutResourceId, null);
			holder = new ViewHolder();
			holder.imgThumb = (ImageView) convertView.findViewById(R.id.grid_item_image);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.content_item_title);
			holder.txtRuntime = (TextView) convertView.findViewById(R.id.grid_item_runtime);
			holder.txtViews = (TextView) convertView.findViewById(R.id.grid_item_views);
			convertView.setTag(holder); // set the View holder
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtTitle.setText(mVideoList.get(position).getTitle());
		holder.txtRuntime.setText(mVideoList.get(position).getRuntime());
		holder.txtViews.setText(mVideoList.get(position).getViewCount());
        picasso.load(mVideoList.get(position).getThumbUrl()).into(holder.imgThumb);


		return convertView;

	}

	public void deleteAll() {
		mVideoList.clear();
		notifyDataSetChanged();
	}

	static class ViewHolder {
		public TextView txtTitle, txtRuntime, txtViews;
		public ImageView imgThumb;
	}

    public void removeVideo(int position){
        mVideoList.remove(position);
        notifyDataSetChanged();
    }

}