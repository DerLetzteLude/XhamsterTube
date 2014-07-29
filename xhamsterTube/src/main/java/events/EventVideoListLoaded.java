package events;

import java.util.ArrayList;

import dao.XhamsterVideo;

public class EventVideoListLoaded {
	private ArrayList<XhamsterVideo> list;
	private int mode;

	public EventVideoListLoaded(ArrayList<XhamsterVideo> list, int mode) {
		this.mode = mode;
		this.list = list;
	}

	public ArrayList<XhamsterVideo> getList() {
		return list;
	}

	public void setList(ArrayList<XhamsterVideo> list) {
		this.list = list;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	
}
