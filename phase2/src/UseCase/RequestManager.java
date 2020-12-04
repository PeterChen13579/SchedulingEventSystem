package UseCase;

import Entities.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestManager implements Serializable {
    private final List<Request> allRequests;

    public RequestManager(){
        allRequests = new ArrayList<>();
    }

}
