package collisionavoidanceengine.request;

import airspaceengine.airspacestructure.AirspaceStructure;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class RandomRequestCreator implements RequestCreator{
    private PriorityQueue<Request> requestQueue = new PriorityQueue<Request>(100, startTimeComparartor);

    // Compare two requests based on their startTime, if they are equal, randomly choose one request
    public static Comparator<Request> startTimeComparartor = new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return o1.getStartTime()-o2.getStartTime();
        }
    };

    // Pick a pair of node from airMap to generate random flight request
    public Request addRequest(AirspaceStructure airMap){
        Random randomizer = new Random();

        //Pick a pair of nodes from graph
        String src = airMap.getNodes().getByIndex(randomizer.nextInt(airMap.getNodes().getSize())).getNodeID();
        String dest= airMap.getNodes().getByIndex(randomizer.nextInt(airMap.getNodes().getSize())).getNodeID();

        //Make sure those two nodes are not identical
        while(src.equals(dest)){
            dest= airMap.getNodes().getByIndex(randomizer.nextInt(airMap.getNodes().getSize())).getNodeID();
        }

        //We are simulating one day, i.e., 1440 minutes.
        return new Request(src,dest,randomizer.nextInt(1440));
    }

    @Override
    public PriorityQueue<Request> generateRequest(int numRequest, AirspaceStructure airMap) {
        for (int i=0; i<numRequest; i++){
            // Generate random requests given a graph
            requestQueue.add(this.addRequest(airMap));
        }
        return this.requestQueue;
    }
}
