package UseCase;
import java.util.List;
import Entities.User;
import Entities.Event;
import Entities.Attendee;
import Entities.Organizer;
import Entities.Speaker;

/**
 * A UseCase class that manages the functionalities of User class.
 *
 */
public class UserManager {
    private List <Attendee> allAttendee;
    private List <Organizer> allOrganizer;
    private List <Speaker> allSpeaker;

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

        System.out.println("This user is not signed up into the system.");
        return false;
    }

    /**
     * Signs up attendee for a particular event
     *
     * @param user The user we wants to sign up for an event
     * @param event  The event the user wants to sign up for
     * @return      true if user successfully booked his/her event. false otherwise
     */
    public boolean signUpEventAttendee(Attendee user, Event event){
        if (user.isAttendingEvent(event.getTitle())){
            System.out.println("You already booked this event. Please book another event.");
            return false;
        }
        user.addEvent(event);
        System.out.println("You have successfully booked this event.");
        return true;
    }

    /**
     * Cancels an event that the attendee has already signed up for
     *
     * @param user   The user wants to cancel their event
     * @param event  The event the user wants to cancel
     * @return       true if user successfully cancelled his/her event. false otherwise.
     */
    public boolean cancelSpotAttendee(Attendee user, Event event){
        if (user.isAttendingEvent(event.getTitle())){
            user.cancelEvent(event);
            return true;
        }
        return false;
    }


    /**
     * Creates an Attendee Account
     *
     * @param userName  Username for an Attendee Account
     * @param password  Password for an Attendee Account
     * @return          true if successfully created an attendee account. False otherwise.
     */
    public boolean createAttendeeAccount(String userName, String password){
        for (User user: allAttendee){
            if (user.getUsername().equals(userName)) {
                System.out.println("This Username has already been taken. Please enter another Username.");
                return false;
            }
        }
        allAttendee.add(new Attendee(userName, password));
        return true;
    }


    /**
     * Creates an Orgainzer Account
     *
     * @param userName  Username for an Organizer Account
     * @param password  Password for an Organizer Account
     * @return          true if successfully created an organizer account. False otherwise
     */
    public boolean createOrganizerAccount(String userName, String password){
        for (User user: allOrganizer){
            if (user.getUsername().equals(userName)) {
                System.out.println("This Username has already been taken. Please enter another Username.");
                return false;
            }
        }
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
        for (User user: allSpeaker){
            if (user.getUsername().equals(userName)) {
                System.out.println("This Username has already been taken. Please enter another Username.");
                return false;
            }
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
    public User stringtoUser(String Username){
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
        System.out.println("Username not found in database.");
        return null;
    }

    /**
     * Method to add user to friend's list
     *
     * @param a  user wants to add user b
     * @param b  user that is being added
     * @return   true if successfully added, false otherwise
     */
    public boolean addFriend(User a, User b){
        return a.addFriend(b);
    }


}
