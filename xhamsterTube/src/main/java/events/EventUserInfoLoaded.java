package events;

import dao.XhamsterUser;

public class EventUserInfoLoaded {
	XhamsterUser user;

	public EventUserInfoLoaded(XhamsterUser user) {
		this.user = user;
	}

	public XhamsterUser getUser() {
		return user;
	}

	public void setUser(XhamsterUser user) {
		this.user = user;
	}
	
	
}
