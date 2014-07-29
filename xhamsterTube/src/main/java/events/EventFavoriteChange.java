package events;

public class EventFavoriteChange {
	Boolean success = false;
	String message = "not saved";

	public EventFavoriteChange(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public EventFavoriteChange() {

	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
