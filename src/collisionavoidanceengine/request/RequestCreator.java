package collisionavoidanceengine.request;

import airspaceengine.airspacestructure.AirspaceStructure;

import java.util.PriorityQueue;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public interface RequestCreator {
    // Given the number of requests to be generated, return a priority queue of requests
    PriorityQueue<Request> generateRequest(int numRequest, AirspaceStructure graph);
}
