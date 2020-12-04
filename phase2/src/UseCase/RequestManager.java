package UseCase;

import Entities.Request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestManager implements Serializable {
    private final List<Request> allRequests;

    public RequestManager(){
        allRequests = new ArrayList<>();
    }

    //create Request object, add it to all Requests list
    public void createRequest(String senderUserName, LocalDateTime time, String content){
        Request request = new Request(senderUserName, time, content);
        allRequests.add(request);
    }

}
