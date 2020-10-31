package Controllers;
import java.io.*;
import java.util.List;

/**
 * A class that deals with the output of data into separate .txt files
 */
public class Writer {
    // users, conferences, events, messages, rooms

    /**
     * A method that saves all the information in a session to a .txt file
     * @param filename name of the file to save to
     * @param allThings all the information needed to save
     * @throws IOException
     */
    public void writeToFile(String filename, List allThings) throws IOException {
        try {
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

//            for (int i = 0; i < 5; i++) {
                writeHelper("attendees.txt", allThings);
//            }

            output.close();
        } catch (Exception e) {
            System.out.println(":((");
        }
    }

    private void writeHelper(String filename, List<List> allThings) throws IOException {
        try {
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            for (int k = 0; k < allThings.size(); k++) {
                output.writeObject(allThings.get(k));
                System.out.println("printed");
            }
            output.close();
        } catch (Exception e) {
            System.out.println("writeerror");
        }
    }

}
