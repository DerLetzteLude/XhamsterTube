package events;

/**
 * Created by chris_000 on 25.07.2014.
 */
public class EventGalleryUsernameLoaded {
    private String userName;
    private String userURL;

    public EventGalleryUsernameLoaded(String userName, String userURL) {
        this.userName = userName;
        this.userURL = userURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserURL() {
        return userURL;
    }

    public void setUserURL(String userURL) {
        this.userURL = userURL;
    }
}
