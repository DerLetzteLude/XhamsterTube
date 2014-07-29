package events;

import java.util.ArrayList;

import dao.XhamsterVideo;

public class EventRelatedVideosLoaded {
	ArrayList<XhamsterVideo> list;

	public EventRelatedVideosLoaded(ArrayList<XhamsterVideo> list) {
		this.list = list;
	}

	public ArrayList<XhamsterVideo> getList() {
		return list;
	}

	public void setList(ArrayList<XhamsterVideo> list) {
		this.list = list;
	}

}
