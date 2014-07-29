package events;

public class EventSubmitComment {
	String text;

	public EventSubmitComment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
