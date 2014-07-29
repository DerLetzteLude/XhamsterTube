package events;

import dao.XhamsterVideo;

public class EventVideoLoaded {
	XhamsterVideo video;

	public EventVideoLoaded(XhamsterVideo video) {
		this.video = video;
		
	}

	public XhamsterVideo getVideo() {
		return video;
	}

	public void setVideo(XhamsterVideo video) {
		this.video = video;
	}	
}
