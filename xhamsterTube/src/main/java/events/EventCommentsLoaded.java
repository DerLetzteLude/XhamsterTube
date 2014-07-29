package events;

import java.util.ArrayList;

import dao.XhamsterComment;

public class EventCommentsLoaded {
	ArrayList<XhamsterComment> list;

	public EventCommentsLoaded(ArrayList<XhamsterComment> list) {
		this.list = list;
	}

	public ArrayList<XhamsterComment> getList() {
		return list;
	}

	public void setList(ArrayList<XhamsterComment> list) {
		this.list = list;
	}

}
