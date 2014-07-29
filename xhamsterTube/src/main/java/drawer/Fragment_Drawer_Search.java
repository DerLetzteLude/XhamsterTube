package drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.example.xhamstertube.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import events.EventLoadSearchVideos;

public class Fragment_Drawer_Search extends Fragment {
    @InjectView(R.id.searchspinnerdate) Spinner spinDate;
    @InjectView(R.id.searchspinnerduration) Spinner spinDuration;
    @InjectView(R.id.searchspinnersort) Spinner spinSort;
    @InjectView(R.id.toggleGroupType) RadioGroup radioType;
    @InjectView(R.id.searchtogglegay) ToggleButton toggleGay;
    @InjectView(R.id.searchtogglestraight) ToggleButton toggleStraight;
    @InjectView(R.id.searchtoggletrans) ToggleButton toggleTrans;
    @InjectView(R.id.searchtogglevideo) RadioButton toggleVideo;
    @InjectView(R.id.searchtogglephoto) RadioButton togglePhoto;
    @InjectView(R.id.search_querry) EditText txtQuerry;
    public String mQuerry;
    EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity_search, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public String getSorting() {
        int selection = spinSort.getSelectedItemPosition();
        switch (selection) {
            case 0:
                return "rl";
            case 1:
                return "da";
            case 2:
                return "vc";
            case 3:
                return "rt";
            case 4:
                return "dr";
            default:
                return "rl";
        }
    }

    public String getCategory() {
        if (toggleVideo.isChecked()) {
            return "video";
        } else {
            return "photo";
        }
    }

    private String getDate() {
        int selection = spinDate.getSelectedItemPosition();
        switch (selection) {
            case 0:
                return "";
            case 1:
                return "last_day";
            case 2:
                return "last_week";
            case 3:
                return "last_month";
            case 4:
                return "last_year";
            default:
                return "";
        }
    }

    private String getDuration() {
        int selection = spinDuration.getSelectedItemPosition();
        switch (selection) {
            case 0:
                return "";
            case 1:
                return "0-10";
            case 2:
                return "10-40";
            case 3:
                return "40+";
            default:
                return "";
        }
    }


    @OnClick(R.id.searchbuttongo)
    public void startSearch() {
        if (txtQuerry.getText() != null) {
            mQuerry = txtQuerry.getText().toString();
        }
        EventLoadSearchVideos event = new EventLoadSearchVideos(mQuerry, getCategory(), getSorting(), getDate(), getDuration(), "0");
        bus.post(event);
    }


}
