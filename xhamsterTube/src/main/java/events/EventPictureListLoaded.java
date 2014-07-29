package events;

import java.util.ArrayList;

import dao.XhamsterPicture;

public class EventPictureListLoaded {
	private ArrayList<XhamsterPicture> list;

	public EventPictureListLoaded(ArrayList<XhamsterPicture> list) {
		this.list = list;
	}

	public ArrayList<XhamsterPicture> getList() {
		return list;
	}

	public void setList(ArrayList<XhamsterPicture> list) {
		this.list = list;
	}

}
