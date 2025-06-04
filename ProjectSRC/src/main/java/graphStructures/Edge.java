package graphStructures;

public class Edge<T> {
    private T from;
    private T to;
    private int weight;

    // with weight
    public Edge(T from, T to, int peso) {
        this.from = from;
        this.to = to;
        this.weight = peso;
    }

    // without weight
    public Edge(T from, T to) {
        this.from  = from;
        this.to = to;
        this.weight = 0;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}