package Presenters;
import Controllers.UserMenuOptions;

public class UserMenu {
    private static UserMenuOptions userMenuOptions;
    private int currentState;

    public UserMenu() {
        userMenuOptions = new UserMenuOptions();
        currentState = 2;
        signInMenu();
    }

    private void loadOrNot() {
        System.out.println("(1) Load Existing Conference \n (2) Create New Conference");
    }

    private void signInMenu() {
        while (currentState == 2) {
            System.out.println("(1) Log In \n (2) Create Attendee Account \n (3) Create Organizer Account" +
                    "\n (4) Quit");
            currentState = userMenuOptions.initialScreen();
        }
        if (currentState == 1) {
            loggedInOptions();
        }
    }

    private void loggedInOptions() {

    }

}
