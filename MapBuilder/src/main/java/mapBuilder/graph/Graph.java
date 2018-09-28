package mapBuilder.graph;

import java.util.List;

public class Graph {
    public Graph(List<Edge2D> edges, List<Vector2D> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public List<Edge2D> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge2D> edges) {
        this.edges = edges;
    }

    public List<Vector2D> getNodes() {
        return nodes;
    }

    public void setNodes(List<Vector2D> nodes) {
        this.nodes = nodes;
    }

    private List<Edge2D> edges;
    private List<Vector2D> nodes;



}
