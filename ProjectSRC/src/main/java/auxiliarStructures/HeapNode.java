package auxiliarStructures;

public class HeapNode<T> {
    private T data;
    private int priority;

    public HeapNode(T data, int priority) {
        this.data = data;
        this.priority = priority;
    }

    public T getData() {
        return data;
    }

    public int getPriority() {
        return priority;
    }
}