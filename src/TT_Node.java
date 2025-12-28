public class TT_Node {
    private TT_Node left;
    private TT_Node middle;
    private TT_Node right;
    private TT_Node p;
    private String key;
    private int size;
    private int count;

    // בנאי מלא
    public TT_Node(TT_Node left, TT_Node middle, TT_Node right, TT_Node p, String key, int size, int count) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.p = p;
        this.key = key;
        this.size = size;
        this.count = count;
    }

    // בנאי חלקי
    public TT_Node(TT_Node left, TT_Node middle, TT_Node right, TT_Node p, String key) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.p = p;
        this.key = key;
        // כדאי לאתחל ערכי ברירת מחדל אם לא מתקבלים בבנאי
        this.size = 1;
        this.count = 1;
    }

    // --- Getters (לקבלת ערכים) ---

    public TT_Node getLeft() {
        return left;
    }

    public TT_Node getMiddle() {
        return middle;
    }

    public TT_Node getRight() {
        return right;
    }

    public TT_Node getP() {
        return p;
    }

    public String getKey() {
        return key;
    }

    public int getSize() {
        return size;
    }

    public int getCount() {
        return count;
    }

    // --- Setters (לעדכון/השמת ערכים) ---

    public void setLeft(TT_Node left) {
        this.left = left;
    }

    public void setMiddle(TT_Node middle) {
        this.middle = middle;
    }

    public void setRight(TT_Node right) {
        this.right = right;
    }

    public void setP(TT_Node p) {
        this.p = p;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // בדיקה האם הצומת הוא עלה (אין לו ילדים)
    public boolean isLeaf() {
        return (left == null) && (middle == null) && (right == null);
    }
}