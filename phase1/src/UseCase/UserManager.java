package UseCase;
import java.util.List;
import Entities.User;
import Entities.Event;
import Entities.Attendee;

/**
 * An abstract class UseCase class that manages the
 * functionalities of User class.
 */
public abstract class UserManager {
    private List <User> allUser;

    /**
     * Authorization of a user trying to log into their account
     *
     * @param enteredUsername  the username entered into the system
     * @param enteredPassword  the password entered into the system
     * @return a boolean value if the user successfully logged into the system of not.
     */
    public boolean credentialAuthorization(String enteredUsername, String enteredPassword){
        for (User user : allUser) {
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
     * @param user The user we want to check if it's signed up for an event
     * @param event  The event to see if user is attending
     * @return      true if user successfully booked his/her event. false otherwise
     */
    public boolean signUpEvent(Attendee user, Event event){
        if (user.isAttendingEvent(event.getTitle())){
            System.out.println("You already booked this event. Please book another event.");
            return false;
        }
        user.addEvent(event);
        System.out.println("You have successfully booked this event.");
        return true;
    }

    public boolean cancelSpot(Attendee user, Event event){
        if (user.isAttendingEvent(event.getTitle())){
            user.cancelEvent(event);
            return true;
        }
        return false;
    }


    public boolean createAttendeeAccount(String userName, String password){
        for (User user: allUser){
            if (user.getUsername().equals(userName)) {
                System.out.println("This Username has already been taken. Please enter another Username.");
                return false;
            }
        }
        allUser.add(new Attendee(userName, password));
        return true;
    }


}
