public class Queue<T> {
    D_Linked_List<T> LL;
    int size;

    public Queue() {
        this.LL = new D_Linked_List<T>(null, null);
        this.size = 0;
    }

    public void Enqueue(T product){
        this.LL.Add_Tail(product);
        size++;
    }

    public T Dequeue(){
        T first = this.LL.Remove(this.LL.getHead());
        size--;
        return first;
    }

    public boolean Check_Empty(){
        if(this.size == 0) return true;
        return false;
    }

    public Node<T> Last_In_Line(){
        return this.LL.getTail();
    }

    public int getSize() {
        return this.size;
    }
}
