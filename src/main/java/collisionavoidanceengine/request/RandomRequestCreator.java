package collisionavoidanceengine.request;

import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.waypoint.Waypoint;

import java.util.*;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class RandomRequestCreator implements RequestCreator{
    private AirspaceStructure airMap;
    private PriorityQueue<Request> requestQueue = new PriorityQueue<Request>(100, startTimeComparator);

    // Compare two requests based on their startTime, if they are equal, randomly choose one request
    public static Comparator<Request> startTimeComparator = new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return o1.getStartTime()-o2.getStartTime();
        }
    };



    @Override
    public PriorityQueue<Request> generateRequest(int numRequest, AirspaceStructure airMap) {
        Random       random    = new Random();
        List<String> keys      = new ArrayList<String>(airMap.getNodes().getWaypointMap().keySet());

        for (int i=0; i<numRequest; i++){
            // Randomly pick a pair of nodes from graph
            Waypoint randomSrc  =   airMap.getNodes().getWaypointMap().get(keys.get(random.nextInt(keys.size())));
            Waypoint randomDest =   airMap.getNodes().getWaypointMap().get(keys.get(random.nextInt(keys.size())));

            // We are simulating one day, i.e., 1440 minutes.
            int time = random.nextInt(1440);

            // Make sure those two nodes are not identical
            while(randomSrc.getNodeID().equals(randomDest.getNodeID())){
                // Regenerate the destination node if duplicate is found
                randomDest= airMap.getNodes().getWaypointMap().get(keys.get(random.nextInt(keys.size())));
            }

            requestQueue.add(new Request(randomSrc.getNodeID(),randomDest.getNodeID(),time));
            System.out.printf("Request to route from "+randomSrc.getNodeID()+" to "+randomDest.getNodeID()+" at time"+ Integer.toString(time));
        }
        return this.requestQueue;
    }
}
