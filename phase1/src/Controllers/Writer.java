package Controllers;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

/**
 * A class that deals with the output of data into separate .txt files
 */
public class Writer {
    private PrintWriter writer;

    /**
     * Saves all the values of every user in the program to a file named users.txt
     * @param allUsers all the users in the program
     */
    public void writeUsersToFile(List<Entities.User> allUsers) {
        try {
            PrintWriter writer = new PrintWriter(new File("users.txt"));
        } catch(IOException e) {
            System.out.println("rip");
        }

        for (int i = 0; i < allUsers.size(); i++) {
            // Update when all variables for users are created
            writer.println(allUsers.get(i).getUsername() + "," + allUsers.get(i).getPassword());
        }
    }

    /**
     * Saves all the values of every event in the program to a file named events.txt
     * @param allEvents all the events in the program
     */
    public void writeEventsToFile(List<Entities.Event> allEvents) {
        try {
            writer = new PrintWriter(new File("events.txt"));
        } catch(IOException e) {
            System.out.println("rip");
        }

        for (int i = 0; i < allEvents.size(); i++) {
            // Update when all variables for events are created
            writer.println(allEvents.get(i));
        }
    }

    /**
     * Saves all the values of every event in the program to a file named events.txt
     * @param allConferences all the events in the program
     */
    public void writeConferencesToFile(List<Entities.Conference> allConferences) {
        try {
            writer = new PrintWriter(new File("conferences.txt"));
        } catch(IOException e) {
            System.out.println("rip");
        }

        for (int i = 0; i < allConferences.size(); i++) {
            // Update when all variables for conferences are created
            writer.println(allConferences.get(i));
        }
    }

    /**
     * Saves all the values of every event in the program to a file named events.txt
     * @param allMessages all the events in the program
     */
    public void writeMessagesToFile(List<Entities.Message> allMessages) {
        try {
            writer = new PrintWriter(new File("messages.txt"));
        } catch(IOException e) {
            System.out.println("rip");
        }

        for (int i = 0; i < allMessages.size(); i++) {
            // Update when all variables for messages are created
            writer.println(allMessages.get(i));
        }
    }

    /**
     * Saves all the values of every event in the program to a file named events.txt
     * @param allRooms all the events in the program
     */
    public void writeRoomsToFile(List<Entities.Room> allRooms) {
        try {
            writer = new PrintWriter(new File("rooms.txt"));
        } catch(IOException e) {
            System.out.println("rip");
        }

        for (int i = 0; i < allRooms.size(); i++) {
            // Update when all variables for messages are created
            writer.println(allRooms.get(i));
        }
    }

}
