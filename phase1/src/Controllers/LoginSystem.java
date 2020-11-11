package Controllers;

import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import Presenters.UserMenu;
import UseCase.EventManager;
import UseCase.UserManager;
import Entities.User;

/**
 * How Users or any type are able to login into the app. The run method will also hold information on what Usertype it
 * is in userType to be used in the corresponding UserMenu.
 *
 */
public class LoginSystem implements Serializable {
    UserManager manager;

    public LoginSystem(UserManager manager) {
        this.manager = manager;
    }

    public boolean run() {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        UserMenu menu = new UserMenu();
        String input = "";
        boolean verified = false;
        menu.printStatement("Type 'cancel' to exit the program; otherwise hit enter to login:");
        try {
            input = br.readLine();
            if (!input.equals("cancel")) {
                menu.printStatement("Please enter your username: ");
                String enteredUsername = br.readLine();
                menu.printStatement("Please enter your password: ");
                String enteredPassword = br.readLine();
                if (verifyLogin(enteredUsername, enteredPassword)) {
                    String username = enteredUsername;
                    String userType = verifyUserType(username);
                    verified = true;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
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
        int i = 0;
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
}