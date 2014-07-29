package events;

public class EventUserInfoLoadError {
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	String text;

	public EventUserInfoLoadError(String text) {
		this.text = text;
	}
}
