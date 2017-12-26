package mapbuilder.graph;

import mapbuilder.triangulation.WeightedEdge;

import java.io.Serializable;

public interface PropertyEdge extends Comparable<WeightedEdge>, Serializable, Edge2D {
    @Override
    int compareTo(WeightedEdge o);

}
