package Presenters;
import Controllers.UserMenuOptions;

public class UserMenu {
    private static UserMenuOptions userMenuOptions;
    private int currentState;

    public UserMenu() {
        userMenuOptions = new UserMenuOptions();
        currentState = 2;
        loadOrNot();
    }

    private void loadOrNot() {
        System.out.println("(1) Load Existing Conference \n (2) Create New Conference");
        userMenuOptions.load();
        signInMenu();
    }

    private void signInMenu() {
            System.out.println("""
                    (1) Log In
                    (2) Create Attendee Account
                    (3) Create Organizer Account
                    (4) Quit""");
            currentState = userMenuOptions.logInSignUp();
        if (currentState == 1) {
            System.out.println("temp log in");
            loggedInMenu();
        }
        if (currentState == 3) {
            System.out.println("saved");
            userMenuOptions.saveProgram();
        }
    }

    private void loggedInMenu() {
        //all options for attendees/organizers/speakers
    }

    private void messageMenu() {
        //all options for messaging
    }

    private void schedulingMenu() {
        //all options for scheduling
    }

    private void signUpMenu() {
        //all options for signing up
    }

}
