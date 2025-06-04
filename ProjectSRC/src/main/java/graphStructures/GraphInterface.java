package graphStructures;

import java.util.List;

public interface GraphInterface<T> {
    void addNode(T id);
    void addEdge(T from, T to);
    List<T> getNeighbors(T nodeId);
    boolean hasEdge(T from, T to);
}
