package mapbuilder.triangulation;

import mapbuilder.graph.Edge2D;
import mapbuilder.graph.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Triangle soup class implementation.
 * 
 * @author Johannes Diemke
 */
class TriangleSoup {

    private List<Triangle2D> triangleSoup;

    public TriangleSoup() {
        this.triangleSoup = new ArrayList<Triangle2D>();
    }

    public void add(Triangle2D triangle) {
        this.triangleSoup.add(triangle);
    }

    public void remove(Triangle2D triangle) {
        this.triangleSoup.remove(triangle);
    }


    public List<Triangle2D> getTriangles() {
        return this.triangleSoup;
    }

    public Triangle2D findContainingTriangle(mapbuilder.graph.Vector2D point) {
        for (Triangle2D triangle : triangleSoup) {
            if (triangle.contains(point)) {
                return triangle;
            }
        }
        return null;
    }

    public Triangle2D findNeighbour(Triangle2D triangle, Edge2D edge) {
        for (Triangle2D triangleFromSoup : triangleSoup) {
            if (triangleFromSoup.isNeighbour(edge) && triangleFromSoup != triangle) {
                return triangleFromSoup;
            }
        }
        return null;
    }

    public Triangle2D findOneTriangleSharing(Edge edge) {
        for (Triangle2D triangle : triangleSoup) {
            if (triangle.isNeighbour(edge)) {
                return triangle;
            }
        }
        return null;
    }


    public Edge findNearestEdge(Vector2D point) {
        List<WeightedEdge> edgeList = new ArrayList<WeightedEdge>();

        for (Triangle2D triangle : triangleSoup) {
            edgeList.add(triangle.findNearestEdge(point));
        }

        WeightedEdge[] weightedEdges = new WeightedEdge[edgeList.size()];
        edgeList.toArray(weightedEdges);

        Arrays.sort(weightedEdges);
        return weightedEdges[0].edge;
    }

    public void removeTrianglesUsing(mapbuilder.graph.Vector2D vertex) {
        List<Triangle2D> trianglesToBeRemoved = new ArrayList<Triangle2D>();

        for (Triangle2D triangle : triangleSoup) {
            if (triangle.hasVertex(vertex)) {
                trianglesToBeRemoved.add(triangle);
            }
        }

        triangleSoup.removeAll(trianglesToBeRemoved);
    }

}