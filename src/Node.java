public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> last;

    public Node(T data, Node<T> next, Node<T> last) {
        this.data = data;
        this.next = next;
        this.last = last;
    }

    public T Get_Data(){return this.data;}
}
