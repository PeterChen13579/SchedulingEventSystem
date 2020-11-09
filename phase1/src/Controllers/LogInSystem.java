package Controllers;

import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import UseCase.UserManager;
import Entities.User;

/**
 * How Users or any type are able to login into the app.
 */
public class LogInSystem implements Serializable {
    UserManager um;
    private boolean verified = false;

    public void run() {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        String input = "";
        while (!input.equals("cancel")) {
            System.out.println("Type 'Log In' to log in or 'cancel' to exit the program: ");
            try {
                input = br.readLine();
                System.out.println("Please type in your username.: ");
                String enteredUsername = br.readLine();
                System.out.println("Please type in your password: ");
                String enteredPassword = br.readLine();
                if (verifyLogin(enteredUsername, enteredPassword)) {
                    String username = enteredUsername;
                    User userType = verifyUserType(username);
                }
            } catch (IOException e) {
                System.out.println("Oops! Something unexpected happened!");
                e.printStackTrace();
            }
        }
    }
        /**
     *
     * @param enteredUsername The Username the User inputs
     * @param enteredPassword The Password the User inputs
     * @return Need to create the UI for the menus for this to make sense. Perhaps return the User type and call on
     * the UI class?
     */
    public boolean verifyLogin(String enteredUsername, String enteredPassword) {
        int i = 0;
        int maxAttempts = 5; //the number of attempts, can be changed
        while (i < maxAttempts) {
            if (um.credentialAuthorization(enteredUsername, enteredPassword)) {
                verified = true;
            }
            else {
                i ++;
            }
        }
        return verified; //Not sure what to return right now, need to see how the UI menu looks.
    }

    /**
     * When a User logs in, the system should recognize what kind of User they are and provide the appropriate menu
     * @return
     */
    public User verifyUserType(String username) {
        return null;
        }
}
