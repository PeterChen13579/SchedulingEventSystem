package Entities;
import java.util.List;
/**
 * <User> is an abstract class that contains basic information
 * every user of this program should have
 */
public abstract class User {
    private final String username;
    private String password;
    private List <String> friends;


    public User(String username, String password){
        this.username = username;
        this.password = password;
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


    /**
     * Adding a user to user's friend list
     *
     * @param user to add into friends list
     * @return     true if sucessfully added user to friends list. False otherwise.
     */
    public boolean addFriend(User user){
        for (String attendee: friends){
            if (user.getUsername().equals(attendee)){
                System.out.println("This user is already in your friend's list.");
                return false;
            }
        }
        friends.add(user.getUsername());
        return true;
    }


}
