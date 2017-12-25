package collisionavoidanceengine.flightplan;

import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.routesegment.RSList;
import airspaceengine.waypoint.WPList;

import javax.xml.soap.Node;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class FlightSchedule {
    private AirspaceStructure airmap;
    private List<EdgeRecord> edgeAvailability;
    private List<NodeRecord> nodeAvailability;
    private List<Flight> flightPlan;

    // To ensure thread-safety. Not yet used.
    private boolean mutex;

    public FlightSchedule(AirspaceStructure airmap) {

        int numNodes = airmap.getNodeNumbers();
        int numEdges = airmap.getEdgeNumbers();

        WPList myWPL = airmap.getNodes();
        RSList myRSL = airmap.getEdges();

        // Initialize node availability
        for(int i=0; i<numNodes;i++){
            nodeAvailability.add(new NodeRecord(myWPL.getByIndex(i).getNodeID(),myWPL.getByIndex(i).isTransferable()));
        }

        // Initialize edge availability
        for (int i =0; i<numEdges; i++){
            edgeAvailability.add(new EdgeRecord(myRSL.getByIndex(i).getEdgeID(),myRSL.getByIndex(i).getWeight()));
        }
    }

    public EdgeRecord getEdgeRecByID(String edgeID){
        for (EdgeRecord record:edgeAvailability){
            if (record.getEdgeID().equals(edgeID))
                return record;
        }
        return null;
    }

    public NodeRecord getNodeRecByID(String nodeID){
        for (NodeRecord record:nodeAvailability){
            if (record.getNodeID().equals(nodeID))
                return record;
        }
        return null;
    }


    // Get the waiting period for waiting at a specific node
    public double getWaitingPenaltyAtNode(String nodeID, int time){
        return this.getNodeRecByID(nodeID).getWaitingPenalty(time);
    }

    // Get the waiting period for using a specific edge
    public  double getWaitingPenaltyAtEdge(String edgeID, int time){
        return  this.getEdgeRecByID(edgeID).getWaitingPenalty(time);
    }








}
