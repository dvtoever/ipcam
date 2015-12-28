package nl.vantoever.service.events;

/**
 * Created by dvtoever on 28-12-2015.
 */
public class LoginEvent {

    private String username = "unknown";

    public LoginEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
