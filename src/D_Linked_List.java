public class D_Linked_List<T> {

    private Node<T> head;
    private Node<T> tail;

    public Node<T> getHead() { return this.head; }
    public Node<T> getTail() { return this.tail; }

    // Constructor
    public D_Linked_List(Node<T> head, Node<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    // Default Constructor (Optional, but usually good practice)
    public D_Linked_List() {
        this.head = null;
        this.tail = null;
    }

    // Add an item after our tail
    public void Add_Tail(T data){
        Node<T> item = new Node<>(data, null, null);
        if (this.tail == null){
            this.head = item;
            this.tail = item;
        } else {
            item.last = this.tail;
            this.tail.next = item;
            this.tail = item;
        }
    }

    // Add an item before our head
    public void Add_Head(T data){
        Node<T> item = new Node<>(data, null, null);
        if (this.head == null){
            this.head = item;
            this.tail = item;
        } else {
            item.next = this.head;
            this.head.last = item;
            this.head = item;
        }
    }

    public T Remove(Node<T> r){
        if(r == null) throw new IllegalArgumentException();
        T removed = r.data;

        if(r == this.head){
            if (this.head.next == null) { // Only head in the list
                this.head = null;
                this.tail = null;
            } else {
                this.head = this.head.next;
                this.head.last = null; // remove pointer to last head
            }
        } else if (r == this.tail){
            this.tail = this.tail.last;
            this.tail.next = null; // remove pointer to last tail
        } else { // Has to be in between head and tail
            Node<T> temp = r.last;
            r.next.last = temp; // next item's last will point to the last of r
            temp.next = r.next; // the item before r will point at the item after r
        }
        return removed;
    }

    public T RemoveFirst() {
        if (this.head == null) return null;

        T data = this.head.data;
        Remove(this.head);
        return data;
    }

    public boolean Is_Empty(){
        return this.head == null;
    }
}