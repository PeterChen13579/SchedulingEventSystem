package Controllers;

import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import Presenters.StatementPresenter;
import UseCase.UserManager;

/**
 * How Users or any type are able to login into the app. The run method will also hold information on what Usertype it
 * is in userType to be used in the corresponding UserMenu.
 *
 */
public class LoginSystem implements Serializable {
    UserManager manager;
    private String username;

    public LoginSystem(UserManager manager) {
        this.manager = manager;
    }

    public boolean run() {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        StatementPresenter menu = new StatementPresenter();
        boolean verified = false;
        while (true) {
            menu.printStatement("Type 'cancel' to return to the main menu; otherwise hit enter to login:");
            try {
                String input = br.readLine();
                if (input.equals("cancel")){
                    break;
                }
                else {
                    menu.printStatement("Please enter your username: ");
                    String enteredUsername = br.readLine();
                    menu.printStatement("Please enter your password: ");
                    String enteredPassword = br.readLine();
                    if (verifyLogin(enteredUsername, enteredPassword)) {
                        verified = true;
                        break;
                    } else {
                        menu.printStatement("You have entered an incorrect username or password.\n Please try again.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return verified;
    }

    /**
     *
     * @param enteredUsername The Username the User inputs
     * @param enteredPassword The Password the User inputs
     * @return Need to create the UI for the menus for this to make sense. Perhaps return the User type and call on
     * the UI class?
     */
    public boolean verifyLogin(String enteredUsername, String enteredPassword) {
        boolean verified = false;
        if (manager.credentialAuthorization(enteredUsername, enteredPassword)) {
            verified = true;
        }
        return verified;
    }

    /**
     * When a User logs in, the system should recognize what kind of User they are and provide the appropriate menu.
     * This method should only be run once the login is successful.
     * @param username that we want to verify user type
     * @return User object to corresponding Username
     */
    public String verifyUserType(String username) {
        return manager.userType(username);
    }

    /**
     * Grabs the Username of the User once successfully logged in.
     * @return Username of the User
     */
    public String getUsername() { return this.username; }

}