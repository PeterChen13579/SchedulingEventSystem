package Tests;

import Controllers.Reader;
import Controllers.Writer;
import Entities.Attendee;
import Entities.Event;
import Entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriterTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<List> testLists = new ArrayList<>();
        testLists.add(new ArrayList<Entities.User>());
//        testLists.add(new ArrayList<Entities.Event>());
//        testLists.add(new ArrayList<Entities.Conference>());
//        testLists.add(new ArrayList<Entities.Room>());
//        testLists.add(new ArrayList<Entities.Message>());
        testLists.get(0).add(new Attendee("hi", "bye"));
        testLists.get(0).add(new Attendee("nothi", "notbye"));
        Writer sad = new Writer();
        sad.writeToFile("attendees.txt", testLists);
        Reader stillSad = new Reader();
        List<List> result = stillSad.loadData("attendees.txt");
        System.out.println(result.get(0));
        System.out.println(((User)(result.get(0).get(0))).getUsername());
        System.out.println(((User)(result.get(0).get(1))).getUsername());
    }

}
