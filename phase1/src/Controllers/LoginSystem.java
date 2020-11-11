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
 * How Users or any type are able to login into the app. The run method will
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
        menu.displayLogin();
        try {
            input = br.readLine();
                if (!input.equals("cancel")) {
                    menu.displayUsername();
                    String enteredUsername = br.readLine();
                    menu.displayPassword();
                    String enteredPassword = br.readLine();
                    if (verifyLogin(enteredUsername, enteredPassword)) {
                        String username = enteredUsername;
                        User userType = verifyUserType(username);
                        verified = true;
                    }
                }
        } catch(IOException e){
            menu.loginFail();
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
     * When a User logs in, the system should recognize what kind of User they are and provide the appropriate menu
     * @param username that we want to verify user type
     * @return User object to corresponding Username
     */
    public User verifyUserType(String username) {
        return manager.stringtoUser(username);
        }
}
