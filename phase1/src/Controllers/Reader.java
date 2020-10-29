package Controllers;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;

/**
 * A class that reads data from a file and converts said data to lists
 */
public class Reader {

    /**
     * Loads all the data and returns an array of lists.
     * @return a list of arrays containing data
     */
    public List[] loadData() {
        List[] results = new List[5];
        try {
            File file = new File("users.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                // Fill this in once I finish writer
                System.out.println(".");
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found :(");
        }
        return results;
    }

}
