package Controllers;

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
    public List<List> loadData(String filename) {
        List<List> results = new ArrayList<>(5);
        try {
            results.add(loadHelper("attendees.txt"));
        } catch(ClassNotFoundException e) {
            System.out.println("loadData");
        }
        return results;
    }

    private List loadHelper(String filename) throws ClassNotFoundException {
        ArrayList helper = new ArrayList();
        try {
            InputStream file = new FileInputStream("attendees.txt");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //Repeat this 5 times?? to read all 5 lists that are stored
            List temp = (List) input.readObject();
            helper.add(temp);
            input.close();
        } catch (IOException e) {
            System.out.println(":(");
        } catch (ClassNotFoundException h) {
            System.out.println(":(((");
        }
        return helper;
    }

}
