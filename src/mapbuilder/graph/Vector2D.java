package mapbuilder.graph;



import java.io.Serializable;

public interface Vector2D extends Serializable{

    Vector2D sub(Vector2D vector);

    Vector2D add(Vector2D vector);

    Vector2D mult(double scalar);

    double mag();

    double dot(Vector2D vector);

    double cross(Vector2D vector);
    double getX();
    void setX(double x);
    double getY();
    void setY(double y);
    String getId();
    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}
