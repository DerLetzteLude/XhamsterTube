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

import usercentral.UpdateItem;

public class Adapter_UpdateItems extends ArrayAdapter<UpdateItem> {
	private Context mContext;
	int mLayoutResourceId;
	private ArrayList<UpdateItem> mUpdateList;
    Picasso picasso;

	public Adapter_UpdateItems(Context context, int layoutResourceId, ArrayList<UpdateItem> data) {
		super(context, layoutResourceId, data);
		this.mLayoutResourceId = layoutResourceId;
		this.mContext = context;
		this.mUpdateList = data;
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
			holder.imgThumb = (ImageView) convertView.findViewById(R.id.grid_item_image);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.content_item_title);
			holder.txtType = (TextView) convertView.findViewById(R.id.content_item_type);
			convertView.setTag(holder); // set the View holder
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtTitle.setText(mUpdateList.get(position).getTitle());
		holder.txtType.setText(mUpdateList.get(position).getTypeString());
        picasso.load(mUpdateList.get(position).getThumbUrl()).into(holder.imgThumb);

		return convertView;

	}

	public void deleteAll() {
		mUpdateList.clear();
		notifyDataSetChanged();
	}

	static class ViewHolder {
		public TextView txtTitle, txtType;
		public ImageView imgThumb;
	}

}
