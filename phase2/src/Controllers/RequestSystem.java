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
     * Mark the request as addressed
     * @param requestNum the request number that a organizer want to marked addressed
     */
    public void markedAsAddressed(int requestNum){
        rm.markedAsAddressed(requestNum);
    }

    /**
     * Mark the request as addressed
     * @param requestNum the request number that a organizer want to marked addressed
     */
    public void markedAsPending(int requestNum){
        rm.markedAsPending(requestNum);
    }

    /**
     * Gets all requests possible to display;
     * @return   A list of requests
     */
    public String[] displayAllRequests() {
        return rm.allRequestInfo();
    }
}
