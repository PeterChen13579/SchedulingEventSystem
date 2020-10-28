package Entities;
import java.util.List;
/**
 * <User> is an abstract class that contains basic information
 * every user of this program should have
 */
public abstract class User {
    private String username;
    private String password;
    private List<User> friends;
    private List<Event> eventsAttending;

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




}
