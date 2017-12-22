package mapbuilder.graph;

import triangulation.WeightedEdge;

import java.io.Serializable;

public interface PropertyEdge extends Comparable<WeightedEdge>, Serializable, Edge2D {
    @Override
    int compareTo(WeightedEdge o);

}
