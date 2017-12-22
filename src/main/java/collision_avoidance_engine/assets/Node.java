package collision_avoidance_engine.assets;

import java.util.List;

/**
 * Created by Ziji Shi on 19/12/17.
 */

public class Node {
    private final String NodeID;
    private final boolean isTransferable;
    private final double XCord;
    private final double YCord;
    private final List<Node> AdjacientNodes;

    public Node(String ID, boolean type, double x_input, double y_input, List<Node> adjacientNodes) {
        NodeID = ID;
        isTransferable=type;
        XCord = x_input;
        YCord = y_input;
        AdjacientNodes = adjacientNodes;
    }


    public double getX() {
        return XCord;
    }

    public double getY() {
        return YCord;
    }

    public boolean isTransferable() {
        return isTransferable;
    }

    public String getNodeID() {
        return NodeID;
    }

    public List<Node> getAdjacientNodes() {
        return AdjacientNodes;
    }
}
