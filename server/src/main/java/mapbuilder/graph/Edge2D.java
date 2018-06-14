package mapbuilder.graph;


import java.io.Serializable;

public interface Edge2D extends Serializable {
    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);

    Vector2D getSource();

    void setSource(Vector2D source);

    Vector2D getTarget();

    void setTarget(Vector2D target);
}
