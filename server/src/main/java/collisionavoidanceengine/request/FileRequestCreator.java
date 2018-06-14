package collisionavoidanceengine.request;

import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.request.exception.NodeNotFoundException;
import collisionavoidanceengine.request.exception.RequestNotMatchException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

import static collisionavoidanceengine.request.RandomRequestCreator.startTimeComparator;

/**
 * Created by Ziji Shi on 4/1/18.
 *
 * Create request from file in csv form.
 *
 * Input file must strictly follow the form of (requestID, startTime, sourceID, destinationID)
 *
 */
public class FileRequestCreator implements RequestCreator{
    String pathToRequestFile=null;
    private AirspaceStructure airMap;
    private PriorityQueue<Request> requestQueue = new PriorityQueue<Request>(100, startTimeComparator);


    FileRequestCreator(String f){
        this.pathToRequestFile = f;
    }


    @Override
    public PriorityQueue<Request> generateRequest(int numRequest, AirspaceStructure airMap) {
        BufferedReader br = null;
        String line = "";

        try{
            br = new BufferedReader(new FileReader(pathToRequestFile));
            while ((line=br.readLine())!=null){

                String [] requestInfo = line.split(",");

                Request request = new Request(requestInfo[0],requestInfo[2],requestInfo[3],Integer.valueOf(requestInfo[1]));

                requestQueue.add(request);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.printf("ERROR!!! Input Request File Not Found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sanity check
        try {
            // Check 1: number of requests in the file matches the required requests
            if (requestQueue.size() != numRequest) {
                throw new RequestNotMatchException();
            }
            // Check 2 : all nodes in requests can be found in airmap
            for (Request request : requestQueue) {
                if (airMap.getNodes().getWaypointMap().containsKey(request.getOriginID())
                        || airMap.getNodes().getWaypointMap().containsKey(request.getDestinationID())) {
                    System.out.printf("ERROR! Either source or destination of Request " + request.getRequestID() + " does not exist in AirMap!");
                    throw new NodeNotFoundException();
                }

            }
        } catch (NodeNotFoundException e) {
            e.printStackTrace();
        } catch (RequestNotMatchException e) {
            e.printStackTrace();
            System.out.printf("ERROR! Number of requests does not match with input.");
        }


        return this.requestQueue;
    }

    @Override
    public void writeToCsv() {
        System.out.printf("Request file is already at "+pathToRequestFile);
    }
}

