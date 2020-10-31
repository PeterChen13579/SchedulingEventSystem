package Entities;

import java.util.List;

public class Organizer extends Attendee{

    private List<String> eventAttending;

    public Organizer(String username,String password){
        super(username, password);
    }
}
