package Presenters;

public class UserMenu {
    private int currentState;



    public void displayInitialLoad(){
        System.out.println("(1) Load Existing Conference \n(2) Create New Conference");
    }

    public void displayMenu(){
        System.out.println("(1) Log In \n(2) Create Attendee Account  \n(3) Create Organizer Account \n(4) Quit");
    }

    public void displayLogin(){
        System.out.println("Type 'cancel' to exit the program; otherwise hit enter to login:");
    }

    public void loginFail(){
        System.out.println("Oops! Something unexpected happened!");
    }

    public void displayUsername(){
        System.out.println("Please enter a username:");
    }

    public void displayPassword(){
        System.out.println("Please enter a password:");
    }

    public void displayIncorrectLogin(){
        System.out.println("Sorry, your username or password are incorrect. Please try again.\n");
    }

    public void displayErrorUsername(){
        System.out.println("This username is already in our database.");
        System.out.println("Please enter a different username");
    }

    public void createdAccountAttendee(){
        System.out.println("You have successfully created an Attendee account!");
    }

    public void createdAccountOrganizer(){
        System.out.println("You have successfully created an Organizer account!");
    }

    public void createdAccountSpeaker(){
        System.out.println("You have successfully created a Speaker account!");
    }

    public void displayExit(){
        System.out.println("You have exited the program.");
    }


    public void loggedInMenuAttendee() {
        //all options for attendees
    }

    public void loggedInMenuOrganizer(){
        //all options for Organizer
    }

    public void loggedInMenuSpeaker(){
        //all options for speaker
    }

    public void messageMenu() {
        //all options for messaging
    }

    public void schedulingMenu() {
        //all options for scheduling
    }

    public void signUpMenu() {
        //all options for signing up
    }

    public void displayErrorMsg(){
        System.out.println("Please enter the corresponding number and try again");
    }

}
