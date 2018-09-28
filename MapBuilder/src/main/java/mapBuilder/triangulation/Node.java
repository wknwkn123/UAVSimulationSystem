package mapBuilder.triangulation;

import mapBuilder.graph.Vector2D;

/**
 * 2D vector class implementation.
 * 
 * @author Johannes Diemke
 */
public class Node implements mapBuilder.graph.Vector2D {

    private double x;
    private double y;
    private String id;
    public Node(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
    public Node(double x, double y, String id) {
        this.setX(x);
        this.setY(y);
        this.setId(id);
    }

    @Override
    public Node add(Vector2D vector) {
        return new Node(this.getX() + vector.getX(), this.getY() + vector.getY());
    }

    @Override
    public Node sub(Vector2D vector) {
        return new Node(this.x - vector.getX(), this.y - vector.getY());
    }
    @Override
    public Node mult(double scalar) {
        return new Node(this.getX() * scalar, this.getY() * scalar);
    }

    @Override
    public double mag() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
    }

    @Override
    public double dot(Vector2D vector) {
        return this.getX() * vector.getX() + this.getY() * vector.getY();
    }

    @Override
    public double cross(Vector2D vector) {
        return this.getY() * vector.getX() - this.getX() * vector.getY();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}