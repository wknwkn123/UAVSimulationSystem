package mapBuilder.triangulation;

import mapBuilder.graph.Vector2D;

/**
 * 2D edge class implementation.
 * 
 * @author Johannes Diemke
 */
public class Edge implements mapBuilder.graph.Edge2D {

    private Vector2D source;
    private Vector2D target;


    public Edge(Vector2D source, Vector2D target) {
        this.setSource(source);
        this.setTarget(target);
    }

    @Override
    public int hashCode() {
        if(getSource().getId().compareTo(getTarget().getId())>0)
            return (getSource().getId() + getTarget().getId()).hashCode();
        else
            return (getTarget().getId() + getSource().getId()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Edge other = (Edge) obj;
        return (getSource().getId().equals(other.getSource().getId()) && getTarget().getId().equals(other.getTarget().getId())) ||
                (getSource().getId().equals(other.getTarget().getId()) && getTarget().getId().equals(other.getSource().getId()));
    }

    @Override
    public String toString() {
        return getSource().toString()+" to "+ getTarget().toString();
    }

    public Vector2D getSource() {
        return source;
    }

    @Override
    public void setSource(Vector2D source) {
        this.source = source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Vector2D getTarget() {
        return target;
    }

    @Override
    public void setTarget(Vector2D target) {
        this.target = target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }
}