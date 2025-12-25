public class D_Linked_List<T> {

    // Internal class for Nodes
    private class Node{
        T data;
        Node next;
        Node last;

        public Node(T data, Node next, Node last) {
            this.data = data;
            this.next = next;
            this.last = last;
        }
    }

    private Node head;
    private Node tail;

    // Ctor
    public D_Linked_List(Node head, Node tail) {
        this.head = head;
        this.tail = tail;
    }

    // Add an item after our tail
    public void Add_Tail(T data){
        Node item = new Node(data, null, null);
        if (this.tail == null){
            this.head = item;
            this.tail = item;
        } else {
            item.last = this.tail;
            this.tail.next = item;
            this.tail = item;
        }
    }

    // Add an item before our head, Probably won't be needed
    public void Add_Head(T data){
        Node item = new Node(data, null, null);
        if (this.head == null){
            this.head = item;
            this.tail = item;
        } else {
            item.next = this.head;
            this.head.last = item;
            this.head = item;
        }
    }

    public void Remove(Node r){
        if(r == null) throw new IllegalArgumentException();

        if(r == this.head){
            if (this.head.next == null) { // Only head in the list, no need to check in tail case
                this.head = null;
                this.tail = null;
            } else {
                this.head = this.head.next;
                this.head.last = null; // remove pointer to last head
            }
        } else if (r == this.tail){
            this.tail = this.tail.last;
            this.tail.next = null; // remove pointer to last tail
        } else { // Has to be in between head and tail (internal Node)
            Node temp = r.last;
            r.next.last = temp; // next item's last will point to the last of r
            temp.next = r.next; // the item before r will point at the item after r to remove it
        }
    }

    public T RemoveFirst() {
        if (this.head == null) return null;

        T data = this.head.data;
        Remove(this.head); // Re-use your generic logic
        return data;
    }

    public boolean Is_Empty(){
        if (this.head == null) return true;
        return false;
    }
}
