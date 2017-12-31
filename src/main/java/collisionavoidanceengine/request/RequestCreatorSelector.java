package collisionavoidanceengine.request;

/**
 * Created by Ziji Shi on 26/12/17.
 */
public class RequestCreatorSelector {
    private RequestCreator requestCreator;

    // Set the concrete request creation strategy at run time
    public void setRequestCreator(String type) throws Exception {
        switch(type) {
            case "RANDOM":
                this.requestCreator=new RandomRequestCreator();
            default:
                System.out.println("!!! Default request creator printed. This should not happen !!!");
                throw new Exception();
        }
    }

    public RequestCreator getRequestCreator() {
        return requestCreator;
    }
}
