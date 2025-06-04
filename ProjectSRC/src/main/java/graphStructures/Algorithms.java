package graphStructures;

import java.util.*;

import auxiliarStructures.*;
import auxiliarStructures.Queue;

public class Algorithms<T> {

    // DFS
    public List<T> dfs(WeightedGraph<T> graph, T start) {
        List<T> visited = new ArrayList<>();
        HashTable<T, Boolean> visitedMap = new HashTable<>();
        dfsRec(graph, start, visitedMap, visited);
        return visited;
    }

    private void dfsRec(WeightedGraph<T> graph, T current, HashTable<T, Boolean> visitedMap, List<T> visited) {
        visitedMap.insert(current, true); // set the actual node as visited
        visited.add(current); // added to the visited list

        for (Node<T> neighborNode : graph.getNodes().get(graph.indexOfNodeValue(current)).getAdjacent()) {
            T neighbor = neighborNode.getData();
            if (visitedMap.obtain(neighbor) == null) {
                dfsRec(graph, neighbor, visitedMap, visited);
            }
        }
    }

    // BFS
    public List<T> bfs(WeightedGraph<T> graph, T start) {
        List<T> visited = new ArrayList<>();
        HashTable<T, Boolean> visitedMap = new HashTable<>();
        Queue<T> queue = new Queue<>();

        visitedMap.insert(start, true); // set as visited
        queue.enqueue(start);

        while (!queue.isEmpty()) {
            T current = queue.dequeue();
            visited.add(current); // set actual node as visited
            int currentIndex = graph.indexOfNodeValue(current);
            if (currentIndex == -1) continue;

            for (Node<T> neighborNode : graph.getNodes().get(currentIndex).getAdjacent()) {
                T neighbor = neighborNode.getData();
                if (visitedMap.obtain(neighbor) == null) {
                    visitedMap.insert(neighbor, true); // set neighbor as visited
                    queue.enqueue(neighbor); // add the neighbor to the queue
                }
            }
        }
        return visited;
    }

    // Dijkstra
    public HashTable<T, Integer> dijkstra(WeightedGraph<T> graph, T start) {
        HashTable<T, Integer> dist = new HashTable<>();
        HashTable<T, Boolean> visited = new HashTable<>();
        MinHeap<HeapNode<T>> minHeap = new MinHeap<>(
                (a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

        for (Node<T> node : graph.getNodes()) {
            dist.insert(node.getData(), Integer.MAX_VALUE);
        }
        dist.insert(start, 0);
        minHeap.add(new HeapNode<>(start, 0));

        while (!minHeap.isEmpty()) {
            HeapNode<T> currentNode = minHeap.poll();
            T u = currentNode.getData();

            if (visited.obtain(u) != null) continue;
            visited.insert(u, true);

            // Using the method in WeightedGraph to get all the neighbors and their weight
            List<WeightedGraph.Pair<T, Integer>> neighbors = graph.getNeighborsWithWeights(u);

            for (WeightedGraph.Pair<T, Integer> neighbor : neighbors) {
                T v = neighbor.first;
                int pesoUV = neighbor.second;

                if (visited.obtain(v) != null) continue;

                int nuevaDist = dist.obtain(u) == Integer.MAX_VALUE ? Integer.MAX_VALUE : dist.obtain(u) + pesoUV;
                if (nuevaDist < dist.obtain(v)) {
                    dist.insert(v, nuevaDist);
                    minHeap.add(new HeapNode<>(v, nuevaDist)); // finally adding the neighbor with the weight
                }
            }
        }
        return dist;
    }

    // Floyd-Warshall (we are not using this, since our graph is made from an adjacency list)
    public int[][] floydWarshall(WeightedGraph<T> graph) {
        int n = graph.getNodes().size();
        int[][] dist = new int[n][n];

        // dist. matrix
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2);
            dist[i][i] = 0;
        }

        // Mapear nodos a índices
        Map<T, Integer> nodeToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            nodeToIndex.put(graph.getNodes().get(i).getData(), i);
        }

        // Rellenar distancias iniciales para aristas existentes
        for (Edge<T> edge : graph.getEdges()) {
            int fromIdx = nodeToIndex.get(edge.getFrom());
            int toIdx = nodeToIndex.get(edge.getTo());
            dist[fromIdx][toIdx] = edge.getWeight();
        }

        // algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE / 2 && dist[k][j] != Integer.MAX_VALUE / 2) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                        }
                    }
                }
            }
        }
        return dist;
    }

    // Prim
    /*
    The Prim algorithm is made for undirected graphs, not directed. In our context, is not used and doesn't work.
    */
    public List<Edge<T>> prim(WeightedGraph<T> graph) {
        List<Edge<T>> mst = new ArrayList<>();
        if (graph.getNodes().isEmpty()) return mst;

        HashTable<T, Boolean> visited = new HashTable<>();
        MinHeap<Edge<T>> minHeap = new MinHeap<>(
                (edge1, edge2) -> Integer.compare(edge1.getWeight(), edge2.getWeight())
        );

        T start = graph.getNodes().get(0).getData(); // first node
        visited.insert(start, true);

        // adding the first node neighbor's edges to the heap
        for (WeightedGraph.Pair<T, Integer> neighborPair : graph.getNeighborsWithWeights(start)) {
            Edge<T> edge = new Edge<>(start, neighborPair.first, neighborPair.second);
            minHeap.add(edge);
        }

        while (!minHeap.isEmpty() && visited.size() < graph.getNodes().size()) {
            Edge<T> edge = minHeap.poll();

            T u = edge.getFrom();
            T v = edge.getTo();

            // not visited node
            T nextNode = null;
            if (visited.obtain(u) != null && visited.obtain(v) == null) {
                nextNode = v;
            } else if (visited.obtain(v) != null && visited.obtain(u) == null) {
                nextNode = u;
            } else {
                continue;
            }

            // added and set as visited
            mst.add(edge);
            visited.insert(nextNode, true);

            // Add to the heap all outgoing edges from the new node to unvisited nodes
            for (WeightedGraph.Pair<T, Integer> neighborPair : graph.getNeighborsWithWeights(nextNode)) {
                if (visited.obtain(neighborPair.first) == null) {
                    Edge<T> e = new Edge<>(nextNode, neighborPair.first, neighborPair.second);
                    minHeap.add(e);
                }
            }
        }

        return mst;
    }

    // Kruskal
    /*
    Like the Prim Algorithm, the Kruskal Algorithm is made for undirected graphs, not our case.
     */
    public List<Edge<T>> kruskal(WeightedGraph<T> graph) {
        List<Edge<T>> mst = new ArrayList<>();
        if (graph.getNodes().isEmpty()) return mst;

        // sort edged by weights
        List<Edge<T>> sortEdges = new ArrayList<>(graph.getEdges());
        sortEdges.sort(Comparator.comparingInt(Edge::getWeight));

        // Union-Find for cicles
        UnionFind<T> uf = new UnionFind<>(graph.getNodes());

        for (Edge<T> edge : sortEdges) {
            T u = edge.getFrom();
            T v = edge.getTo();
            if (uf.find(u) != uf.find(v)) {
                mst.add(edge);
                uf.union(u, v);
            }
            if (mst.size() == graph.getNodes().size() - 1) break;
        }

        return mst;
    }

    // Auxiliar class for Kruskal
    private class UnionFind<E> {
        private Map<E, E> parent;

        public UnionFind(List<Node<E>> nodes) {
            parent = new HashMap<>();
            for (Node<E> node : nodes) {
                parent.put(node.getData(), node.getData());
            }
        }

        public E find(E x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        public void union(E x, E y) {
            E px = find(x);
            E py = find(y);
            if (!px.equals(py)) {
                parent.put(px, py);
            }
        }
    }

}

