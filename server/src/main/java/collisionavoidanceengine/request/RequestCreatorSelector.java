package collisionavoidanceengine.request;

import config.Config;

/**
 * Created by Ziji Shi on 26/12/17.
 */
public class RequestCreatorSelector {
    private RequestCreator requestCreator;

    public RequestCreatorSelector(Config config) {
        switch(config.requestType) {
            case "RANDOM":{
                this.requestCreator=new RandomRequestCreator();
                break;
            }

            case "FILE":{
                this.requestCreator = new FileRequestCreator(config.pathToRequest);
                break;
            }

            default:
                System.out.println("!!! Default request creator printed. This should not happen !!!");
        }
    }

    public RequestCreator getRequestCreator() {
        return requestCreator;
    }
}
