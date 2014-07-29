package picture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xhamstertube.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Fragment_SinglePicture extends Fragment {

	private String imageUrl;
	@InjectView(R.id.singleimage) ImageView imageView;
	PhotoViewAttacher mAttacher;

	public static Fragment_SinglePicture newInstance(String gsonString) {
		Fragment_SinglePicture myFragment = new Fragment_SinglePicture();
		Bundle args = new Bundle();
		args.putString("IMAGEURL", gsonString);
		myFragment.setArguments(args);
		return myFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_singlepicture, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		imageUrl = getArguments().getString("IMAGEURL");
		Log.i("TEST", "imageUrl " + imageUrl);

		Picasso.with(getActivity()).load(imageUrl).placeholder(android.R.drawable.progress_indeterminate_horizontal).into(imageView, new Callback() {

			@Override
			public void onSuccess() {
				mAttacher = new PhotoViewAttacher(imageView);

			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		});

	}
}
