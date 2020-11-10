package Controllers;

import Entities.Attendee;
import Entities.Chat;
import Entities.Event;
import Entities.Organizer;
import Entities.Room;
import Entities.Speaker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that reads data from a file and converts said data to lists
 */
public class Reader {

    /**
     * A method that loads all the information from a previous session from a .txt file
     * @param filename the name of the file to open
     * @return a list of lists that contains all the data needed
     * @throws ClassNotFoundException
     */
    public List loadData(String filename) {
        List results = new ArrayList<>(5);
        if (verifySave("file")) {
            try {
                results.add(loadHelper(filename));
            } catch (ClassNotFoundException e) {
                System.out.println("loadData");
            }
        }
        return results;
    }

    private boolean verifySave(String filename) {
        File attendee = new File("attendees.txt");
        File organizer = new File("organizers.txt");
        File speaker = new File("speakers.txt");
        File room = new File("rooms.txt");
        File event = new File("events.txt");
        File chat = new File("chats.txt");
        return (attendee.exists() && organizer.exists() && speaker.exists() && room.exists() &&
                event.exists() && chat.exists());
    }

    private List loadHelper(String filename) throws ClassNotFoundException {
        ArrayList helper = new ArrayList();
        try {
            InputStream file = new FileInputStream(filename);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            helper = (ArrayList) input.readObject();
            input.close();
        } catch (IOException e) {
            System.out.println(":(");
        } catch (ClassNotFoundException h) {
            System.out.println(":(((");
        }
        return helper;
    }

}
