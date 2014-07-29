package events;

public class EventLogin {
	Boolean logedIn = false;

	public EventLogin(Boolean logedIn) {
		this.logedIn = logedIn;
	}

	public Boolean getLogedIn() {
		return logedIn;
	}

	public void setLogedIn(Boolean logedIn) {
		this.logedIn = logedIn;
	}
}
