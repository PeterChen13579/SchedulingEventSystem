package Controllers;

import java.io.Serializable;
import UseCase.UserManager;
import Entities.User;

/**
 * How Users or any type are able to login into the app.
 */
public class LogInSystem implements Serializable {
    UserManager um;
    private boolean verified = false;
    private int maxAttempts = 5; //the number of attempts, can be changed

    /**
     *
     * @param enteredUsername The Username the User inputs
     * @param enteredPassword The Password the User inputs
     * @return Need to create the UI for the menus for this to make sense. Perhaps return the User type and call on
     * the UI class?
     */
    public boolean verifyLogin(String enteredUsername, String enteredPassword) {
        int i = 0;
        while (i < maxAttempts) {
            if (um.credentialAuthorization(enteredUsername, enteredPassword)) {
                verified = true;
            }
        }
        return verified; //Not sure what to return right now, need to see how the UI menu looks.
    }

    /**
     * When a User logs in, the system should recognize what kind of User they are and provide the appropriate menu
     * @return
     */
    public User verifyUserType(String enteredUsername) {
        return null;
        }
    }
}
