package mapbuilder.triangulation;

import mapbuilder.graph.Vector2D;

/**
 * Edge distance pack class implementation used to describe the distance to source
 * given edge.
 * 
 * @author Johannes Diemke
 */
public class WeightedEdge implements mapbuilder.graph.PropertyEdge {

    public Edge edge;
    public double distance;

    /**
     * Constructor of the edge distance pack class used to create source new edge
     * distance pack instance from source 2D edge and source scalar value describing source
     * distance.
     * 
     * @param edge
     *            The edge
     * @param distance
     *            The distance of the edge to some point
     */
    public WeightedEdge(Edge edge, double distance) {
        this.edge = edge;
        this.distance = distance;
    }

    @Override
    public int compareTo(WeightedEdge o) {
        return Double.compare(this.distance, o.distance);
    }

    @Override
    public Vector2D getSource() {
        return edge.getSource();
    }

    @Override
    public void setSource(Vector2D source) {
        edge.setSource(source);
    }

    @Override
    public Vector2D getTarget() {
        return edge.getTarget();
    }

    @Override
    public void setTarget(Vector2D target) {
        edge.setTarget(target);
    }
}