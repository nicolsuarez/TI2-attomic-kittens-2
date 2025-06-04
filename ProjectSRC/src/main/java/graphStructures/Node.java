package graphStructures;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data;
    private List<Node<T>> adjacent;

    public Node(T data) {
        this.data = data;
        this.adjacent = new ArrayList<>();
    }

    public void addAdjacents(Node<T> to) {
        adjacent.add(to);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Node<T>> getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(List<Node<T>> adjacent) {
        this.adjacent = adjacent;
    }
}
