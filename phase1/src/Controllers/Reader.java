package Controllers;

import Entities.Attendee;
import Entities.Chat;
import Entities.Event;
import Entities.Organizer;
import Entities.Room;
import Entities.Speaker;
import UseCase.UserManager;
import UseCase.RoomManager;
import UseCase.EventManager;
import UseCase.ChatManager;

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
    public Object loadData(String filename) {
        File file = new File(filename);
        Object helper = new Object();
        try {
            if (file.exists()) {
                if (file.length() != 0) {
                    InputStream inputFile = new FileInputStream(file);
                    InputStream buffer = new BufferedInputStream(inputFile);
                    ObjectInput input = new ObjectInputStream(buffer);

                    helper = input.readObject();
                    input.close();
                }
            }
        } catch (IOException e) {
            System.out.println(":(");
        } catch (ClassNotFoundException h) {
            System.out.println(":(((");
        }
        System.out.println(helper);
        return helper;
    }

}
