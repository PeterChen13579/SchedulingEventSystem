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


    public boolean signUpEvent(Attendee user, Event event){
        if (user.isAttendingEvent(event)){
            System.out.println("You already booked this event.");
            return false;
        }
        //Need to ask if it's fine for user class to have an add event method.
        //Need to ask if it's fine having userName as naming variables.
        return true;
    }


}
