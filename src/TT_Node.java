// T must extend Comparable<T> so you can compare keys (e.g., key1.compareTo(key2))
public class TT_Node<T extends Comparable<T>> {

    // All node references must now include the generic type <T>
    private TT_Node<T> left;
    private TT_Node<T> middle;
    private TT_Node<T> right;
    private TT_Node<T> p;

    // The key is now of type T
    private T key;

    private int size; // For Sum of patients
    private int count;
    private Object pointer;

    // Full Constructor
    public TT_Node(TT_Node<T> left, TT_Node<T> middle, TT_Node<T> right, TT_Node<T> p, T key, int size, int count) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.p = p;
        this.key = key;
        this.size = size;
        this.count = count;
    }

    // Partial Constructor
    public TT_Node(TT_Node<T> left, TT_Node<T> middle, TT_Node<T> right, TT_Node<T> p, T key, Object pointer) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.p = p;
        this.key = key;
        // Default initialization
        this.size = 1;
        this.count = 1;
        this.pointer = pointer;
    }

    // --- Getters ---

    public TT_Node<T> getLeft() {
        return left;
    }

    public TT_Node<T> getMiddle() {
        return middle;
    }

    public TT_Node<T> getRight() {
        return right;
    }

    public TT_Node<T> getP() {
        return p;
    }

    public T getKey() {
        return key;
    }

    public int getSize() {
        return size;
    }

    public int getCount() {
        return count;
    }

    public Object getPointer() {
        return pointer;
    }

    // --- Setters ---

    public void setLeft(TT_Node<T> left) {
        this.left = left;
    }

    public void setMiddle(TT_Node<T> middle) {
        this.middle = middle;
    }

    public void setRight(TT_Node<T> right) {
        this.right = right;
    }

    public void setP(TT_Node<T> p) {
        this.p = p;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // Check if leaf
    public boolean isLeaf() {
        return (left == null) && (middle == null) && (right == null);
    }
}