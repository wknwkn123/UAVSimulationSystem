package collisionavoidanceengine.request;

/**
 * Created by Ziji Shi on 26/12/17.
 */
public class RequestCreatorSelector {
    private RequestCreator requestCreator;

    public RequestCreatorSelector(String type) {
        switch(type) {
            case "RANDOM":
                this.requestCreator=new RandomRequestCreator();
            default:
                System.out.println("!!! Default request creator printed. This should not happen !!!");
        }
    }

    public RequestCreator getRequestCreator() {
        return requestCreator;
    }
}
