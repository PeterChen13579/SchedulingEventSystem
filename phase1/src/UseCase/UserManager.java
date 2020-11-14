package UseCase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Entities.User;
import Entities.Attendee;
import Entities.Organizer;
import Entities.Speaker;

/**
 * A UseCase class that manages the functionalities of User class.
 *
 */
public class UserManager implements Serializable {
    private final List <Attendee> allAttendee = new ArrayList<>();
    private final List <Organizer> allOrganizer = new ArrayList<>();
    private final List <Speaker> allSpeaker = new ArrayList<>();

    public UserManager() {}

    public List<Attendee> getAllAttendee() {
        return this.allAttendee;
    }

    public List<Speaker> getAllSpeaker() {
        return this.allSpeaker;
    }

    /**
     * Check whether the user account exists.
     * @param userName the name of the user account that you want to check.
     * @return true iff the username exists.
     */

    public boolean isUserExists(String userName){
        for (Attendee attendee: allAttendee){
            if (userName.equals(attendee.getUsername())){return true;}
        }

        for (Organizer organizer: allOrganizer){
            if (userName.equals(organizer.getUsername())){return true;}
        }

        for (Speaker speaker: allSpeaker){
            if (userName.equals(speaker.getUsername())){return true;}
        }
        return false;
    }

    /**
     * Authorization of a user trying to log into their account
     *
     * @param enteredUsername  the username entered into the system
     * @param enteredPassword  the password entered into the system
     * @return a boolean value if the user successfully logged into the system of not.
     */
    public boolean credentialAuthorization(String enteredUsername, String enteredPassword){
        for (User user : allAttendee) {
            if (user.getUsername().equals(enteredUsername)) {
                return user.getPassword().equals(enteredPassword);
            }
        }
        for (User user : allOrganizer) {
            if (user.getUsername().equals(enteredUsername)) {
                return user.getPassword().equals(enteredPassword);
            }
        }
        for (User user : allSpeaker) {
            if (user.getUsername().equals(enteredUsername)) {
                return user.getPassword().equals(enteredPassword);
            }
        }
        return false;
    }

    /**
     * Signs up attendee for a particular event
     *
     * @param username The user we wants to sign up for an event
     * @param eventTitle  The event the user wants to sign up for
     */
    public void signUpEventAttendee(String username, String eventTitle){
        User user = stringToUser(username);
        List<String> eventList = user.getEventAttending();
        eventList.add(eventTitle);
        user.setEventAttending(eventList);
    }

    /**
     * Cancels an event that the attendee has already signed up for
     *
     * @param username   The user wants to cancel their event
     * @param eventTitle  The event the user wants to cancel
     */
    public void cancelSpotAttendee(String username, String eventTitle){
        User user = stringToUser(username);
        List<String> eventList = user.getEventAttending();
        eventList.remove(eventTitle);
        user.setEventAttending(eventList);
    }

    /**
     * Creates an Attendee Account
     *
     * @param userName  Username for an Attendee Account
     * @param password  Password for an Attendee Account
     * @return          true if successfully created an attendee account. False otherwise.
     */
    public boolean createAttendeeAccount(String userName, String password){
        if(isUserExists(userName)) {return false;}

        allAttendee.add(new Attendee(userName, password));
        return true;
    }


    /**
     * Creates an Organizer Account
     *
     * @param userName  Username for an Organizer Account
     * @param password  Password for an Organizer Account
     * @return          true if successfully created an organizer account. False otherwise
     */
    public boolean createOrganizerAccount(String userName, String password){

        if(isUserExists(userName)){ return false; }

        allOrganizer.add(new Organizer(userName, password));
        return true;
    }


    /**
     * Creates a Speaker Account
     *
     * @param userName    Username for a speaker Account
     * @param password    Password for a speaker Account
     * @return            true if successfully created a speaker account. False otherwise
     */
    public boolean createSpeakerAccount(String userName, String password){
        if(isUserExists(userName)){
            return false;
        }

        allSpeaker.add(new Speaker(userName, password));
        return true;
    }


    /**
     * Precondition: Username exists in database
     * Returns a User object that corresponds to Username
     *
     * @param Username Username that wants to be searched for
     * @return         User object that matches with Username
     */
    private User stringToUser(String Username){
        assert isUserExists(Username);
        for (Attendee a: allAttendee){
            if (a.getUsername().equals(Username)){
                return a;
            }
        }
        for (Organizer b: allOrganizer){
            if (b.getUsername().equals(Username)){
                return b;
            }
        }
        for (Speaker c: allSpeaker) {
            if (c.getUsername().equals(Username)) {
                return c;
            }
        }
        throw new IllegalArgumentException("eventTitle does not correspond to any event in event List");
    }

    /**
     * Check whether the user has added this friend.
     * @param usernameA user who wants to add new friend
     * @param usernameB user that is being added
     * @return true iff the user have already added the friends.
     */

    public boolean isAddFriend(String usernameA, String usernameB){
        User userA = stringToUser(usernameA);

        List<String> friends = userA.getFriends();
        for (String friend: friends){
            if(friend.equals(usernameB)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to add user to friend's list
     *
     * @param usernameA  user wants to add user b
     * @param usernameB  user that is being added
     * @return   true if successfully added, false otherwise
     */
    public boolean addFriend(String usernameA, String usernameB){
        User userA = stringToUser(usernameA);

        List<String> friends = userA.getFriends();
        if (isUserExists(usernameB) && isAddFriend(usernameA, usernameB)){
            friends.add(usernameB);
            userA.setFriends(friends);
            return true;
        }
        return false;
    }

    /**
     * Return whether a Speaker object with the given username has been created
     * @param speakerUserName the username of the speaker
     * @return true iff speaker exists; false otherwise
     */
    public boolean doesSpeakerExist(String speakerUserName){
        for(Speaker speaker: allSpeaker){
            if(speaker.getUsername().equals(speakerUserName)) return true;
        }
        return false;
    }

    /**
     * Add the given event title to the given speaker's list of events.
     * @param title the title of the event
     * @param speakerUserName the username of the speaker
     */
    public void addEventToSpeaker(String title, String speakerUserName){
        for (Speaker s: allSpeaker) {
            if (s.getUsername().equals(speakerUserName)) {
                s.addEventToSpeaker(title);
            }
        }
    }

    /**
     * Check the type of the user.
     * @param username the username of the user that you want to check.
     * @return return "Attendee" if the user is an attendee;
     * return "Organizer" if the user is an organizer;
     * return "Speaker" if the user is a speaker.
     */

    public String userType(String username){
        for(Attendee attendee: allAttendee){
            if(username.equals(attendee.getUsername())) {return "Attendee";}
        }
        for (Organizer organizer: allOrganizer){
            if(username.equals(organizer.getUsername())) {return "Organizer";}
        }
        for(Speaker speaker: allSpeaker){
            if(username.equals(speaker.getUsername())) {return "Speaker";}
        }
        return "Invalid Username";
    }
}
