package events;

import java.util.ArrayList;

import dao.XhamsterGallery;

public class EventGalleryListLoaded {
	private ArrayList<XhamsterGallery> list;
	private int mode;

	public EventGalleryListLoaded(ArrayList<XhamsterGallery> list, int mode) {
		this.mode = mode;
		this.list = list;
	}

	public ArrayList<XhamsterGallery> getList() {
		return list;
	}

	public void setList(ArrayList<XhamsterGallery> list) {
		this.list = list;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	
}
