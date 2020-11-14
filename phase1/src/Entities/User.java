package Entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * <User> is an abstract class that contains basic information
 * every user of this program should have
 */
public abstract class User implements Serializable {
    private final String username;
    private String password;
    private List <String> friends;
    private List<String> eventAttending;


    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.eventAttending = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    /**
     * Getter for this <User></User>'s username.
     *
     * @return this <User></User>'s username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for this <User></User>'s password.
     *
     * @return this <User></User>'s password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for this <User></User>'s password
     *
     * @param newPassword the new password
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public List<String> getEventAttending() {
        return eventAttending;
    }

    public void setEventAttending(List<String> eventAttending) {
        this.eventAttending = eventAttending;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
