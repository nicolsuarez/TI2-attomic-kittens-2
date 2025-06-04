package graphStructures;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraph<T> implements GraphInterface<T> {

    private List<Node<T>> nodes; // Graph's adjacency list
    private List<Edge<T>> edges; // Edge's list

    public WeightedGraph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void addNode(T value) {
        if (indexOfNodeValue(value) == -1) {
            nodes.add(new Node<>(value));
        }
    }

    public int indexOfNodeValue(T value) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getData().equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addEdge(T from, T to) {
        addEdge(from, to, 0);
    }

    public void addEdge(T from, T to, int weight) {
        int fromIndex = indexOfNodeValue(from);
        int toIndex = indexOfNodeValue(to);

        if (fromIndex == -1 || toIndex == -1) {
            return;
        }

        Node<T> fromNode = nodes.get(fromIndex);
        Node<T> toNode = nodes.get(toIndex);

        if (!fromNode.getAdjacent().contains(toNode)) {
            fromNode.addAdjacents(toNode);
        }

        boolean exists = false;
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(from) && edge.getTo().equals(to)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            edges.add(new Edge<>(from, to, weight));
        }
    }

    @Override
    public List<T> getNeighbors(T nodeId) {
        int nodeIndex = indexOfNodeValue(nodeId);
        if (nodeIndex == -1) {
            return List.of(); // Nodo no existe
        }
        Node<T> node = nodes.get(nodeIndex);
        List<T> neighbors = new ArrayList<>();
        // Vecinos serán solo los nodos destino de las aristas salientes
        for (Node<T> adj : node.getAdjacent()) {
            neighbors.add(adj.getData());
        }
        return neighbors;
    }

    @Override
    public boolean hasEdge(T from, T to) {
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(from) && edge.getTo().equals(to)) {
                return true;
            }
        }
        return false;
    }

    public int getEdgeWeight(T origen, T destino) {
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(origen) && edge.getTo().equals(destino)) {
                return edge.getWeight();
            }
        }
        return -1; // No connection between nodes
    }

    public List<Pair<T, Integer>> getNeighborsWithWeights(T nodeId) {
        List<Pair<T, Integer>> result = new ArrayList<>();
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(nodeId)) {
                result.add(new Pair<>(edge.getTo(), edge.getWeight()));
            }
        }
        return result;
    }

    public static class Pair<T, U> {
        public T first;
        public U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    public List<Node<T>> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node<T>> nodes) {
        this.nodes = nodes;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge<T>> edges) {
        this.edges = edges;
    }
}
