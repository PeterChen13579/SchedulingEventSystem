package Controllers;

import UseCase.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class RequestSystem {
    RequestManager rm;

    /**
     * Constructor for RequestSystem
     * @param rm the RequestManager for this execution of the program
     */
    public RequestSystem(RequestManager rm) {
        this.rm = rm;
    }

    /**
     * Gets all requests possible to display;
     * @return   A list of requests
     */
    public String[] displayAllEvents() {
        return rm.allRequestInfo();
    }
}
