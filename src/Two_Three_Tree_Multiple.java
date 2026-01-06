public class Two_Three_Tree_Multiple<T extends Comparable<T>> {

    private TT_Node<T> root;

    // INIT tree with nothing
    public Two_Three_Tree_Multiple(T minKey, T maxKey) {
        TT_Node<T> left_node = new TT_Node<T>(null, null, null, null, minKey, null);
        TT_Node<T> middle_node = new TT_Node<T>(null, null, null, null, maxKey, null);
        this.root = new TT_Node<T>(left_node, middle_node, null, null, maxKey, null);
        left_node.setP(this.root);
        middle_node.setP(this.root);
    }

    public TT_Node<T> getRoot(){
        return root;
    }

    public void setRoot(TT_Node<T> root){
        this.root = root;
    }

    public TT_Node<T> Search(TT_Node<T> x, T k){
        if (x.isLeaf()) {
           if (x.getKey().compareTo(k)== 0){
               return x;
           }
           else return null;
        }
        if (k.compareTo(x.getLeft().getKey()) <= 0) {
            return Search(x.getLeft(), k);
        } else if (k.compareTo(x.getMiddle().getKey()) <= 0){
            return Search(x.getMiddle(), k);
        } else {
            return Search(x.getRight(), k);
        }
    }

    public int Size_Search(TT_Node<T> x, T k, int sum){ // remember to send with 0
        if (x.isLeaf()) {
            if (x.getKey().compareTo(k) <= 0){
                return sum + x.getSize();
            }
            else return sum;
        }
        if (k.compareTo(x.getLeft().getKey()) <= 0) {
            return Size_Search(x.getLeft(), k, sum);
        } else if (k.compareTo(x.getMiddle().getKey()) <= 0){
            return Size_Search(x.getMiddle(), k, sum + x.getLeft().getSize());
        } else {
            return Size_Search(x.getRight(), k, sum + x.getLeft().getSize() + x.getMiddle().getSize());
        }
    }

    public int Count_Search(TT_Node<T> x, T k, int cnt){ // remember to send with 0
        if (x.isLeaf()) {
            if (x.getKey().compareTo(k) <= 0){
                return cnt + x.getCount();
            }
            else return cnt;
        }
        if (k.compareTo(x.getLeft().getKey()) <= 0) {
            return Count_Search(x.getLeft(), k, cnt);
        } else if (k.compareTo(x.getMiddle().getKey()) <= 0){
            return Count_Search(x.getMiddle(), k, cnt + x.getLeft().getCount());
        } else {
            return Count_Search(x.getRight(), k, cnt + x.getLeft().getCount() + x.getMiddle().getCount());
        }
    }

    public void Update_Key(TT_Node<T> x) {
        x.setKey(x.getLeft().getKey());
        if (x.getMiddle() != null) {
            x.setKey(x.getMiddle().getKey());
        }
        if (x.getRight() != null) {
            x.setKey(x.getRight().getKey());
        }
    }

    public void Update_Size(TT_Node<T> x){
        int newSize = x.getLeft().getSize();
        if (x.getMiddle() != null){
            newSize += x.getMiddle().getSize();
        }
        if (x.getRight() != null){
            newSize += x.getRight().getSize();
        }
        x.setSize(newSize);
    }
    public void Update_Count(TT_Node<T> x){
        int new_count = x.getLeft().getCount();
        if (x.getMiddle() != null){
            new_count += (int)x.getMiddle().getCount();
        }
        if (x.getRight() != null){
            new_count += (int)x.getRight().getCount();
        }
        x.setCount(new_count);
    }

    public void Set_Children(TT_Node<T> x, TT_Node<T> l, TT_Node<T> m, TT_Node<T> r){
        x.setLeft(l);
        x.setMiddle(m);
        x.setRight(r);
        l.setP(x);
        if (m != null) m.setP(x);
        if (r != null) r.setP(x);
        Update_Key(x);
        Update_Size(x);
        Update_Count(x);
    }

    public TT_Node<T> Insert_And_Split(TT_Node<T> x, TT_Node<T> z){
        TT_Node<T> l = x.getLeft();
        TT_Node<T> m = x.getMiddle();
        TT_Node<T> r = x.getRight();
        if (r == null){
            if (z.getKey().compareTo(l.getKey()) < 0){
                Set_Children(x, z, l, m);
            } else if (z.getKey().compareTo(m.getKey()) < 0){
                Set_Children(x, l, z, m);
            } else {
                Set_Children(x, l, m, z);
            }
            return null;
        }

        TT_Node<T> y = new TT_Node<T>(null, null, null, null, null, null);
        if (z.getKey().compareTo(l.getKey()) < 0){
            Set_Children(x, z, l, null);
            Set_Children(y, m, r, null);
        } else if (z.getKey().compareTo(m.getKey()) < 0){
            Set_Children(x, l, z, null);
            Set_Children(y, m, r, null);
        } else if(z.getKey().compareTo(r.getKey()) < 0){
            Set_Children(x, l, m, null);
            Set_Children(y, z, r, null);
        } else {
            Set_Children(x, l, m, null);
            Set_Children(y, r, z, null);
        }
        return y;
    }

    public void Insert(Two_Three_Tree_Multiple<T> t, TT_Node<T> z){
        TT_Node<T> exists = Search(t.getRoot(), z.getKey());
        if(exists != null){ // To handle multiple keys
            exists.setCount(exists.getCount() + 1);
            exists.setSize(exists.getSize() + (Integer)((Object)exists.getKey()));
            while (exists.getP() != null){
                exists = exists.getP();
                Update_Size(exists);
                Update_Count(exists);
            }
        } else { // This is the code from the Lecture
            TT_Node<T> y = t.getRoot();
            while(!y.isLeaf()){
                if(z.getKey().compareTo(y.getLeft().getKey()) < 0){
                    y = y.getLeft();
                } else if(z.getKey().compareTo(y.getMiddle().getKey()) < 0){
                    y = y.getMiddle();
                } else{
                    y = y.getRight();
                }
            }

            TT_Node<T> x = y.getP();
            z = Insert_And_Split(x, z);
            while(x != t.getRoot()){
                x = x.getP();
                if(z != null){
                    z = Insert_And_Split(x, z);
                } else{
                    Update_Key(x);
                    Update_Size(x);
                    Update_Count(x);
                }
            }

            if(z != null){
                TT_Node<T> w = new TT_Node<T>(null, null, null, null, null, null);
                Set_Children(w, x, z, null);
                t.setRoot(w);
            }
        }
    }

    public TT_Node<T> Borrow_Or_Merge(TT_Node<T> y){
        TT_Node<T> z = y.getP();
        if(y == z.getLeft()){
            TT_Node<T> x = z.getMiddle();
            if(x.getRight() != null){
                Set_Children(y, y.getLeft(), x.getLeft(), null);
                Set_Children(x, x.getMiddle(), x.getRight(), null);
            } else{
                Set_Children(x, y.getLeft(), x.getLeft(), x.getMiddle());
                Set_Children(z, x, z.getRight(), null);
            }
            return z;
        }

        if(y == z.getMiddle()){
            TT_Node<T> x = z.getLeft();
            if(x.getRight() != null){
                Set_Children(y, x.getRight(), y.getLeft(), null);
                Set_Children(x, x.getLeft(), x.getMiddle(), null);
            } else{
                Set_Children(x, x.getLeft(), x.getMiddle(), y.getLeft());
                Set_Children(z, x, z.getRight(), null);
            }

            return z;
        }

        TT_Node<T> x = z.getMiddle();
        if(x.getRight() != null){
            Set_Children(y, x.getRight(), y.getLeft(), null);
            Set_Children(x, x.getLeft(), x.getMiddle(), null);
        } else{
            Set_Children(x, x.getLeft(), x.getMiddle(), y.getLeft());
            Set_Children(z, z.getLeft(), x, null);
        }

        return z;
    }

    public void Delete(Two_Three_Tree_Multiple<T> t, TT_Node<T> x){
        if(x.getCount() > 1) { // To handle multiple keys
            x.setCount(x.getCount() - 1);
            x.setSize(x.getSize() - (Integer)((Object)x.getKey()));
            while (x.getP() != null){
                x = x.getP();
                Update_Size(x);
                Update_Count(x);
            }
        } else {
            TT_Node<T> y = x.getP();
            if(x == y.getLeft()){
                Set_Children(y, y.getMiddle(), y.getRight(), null);
            } else if(x == y.getMiddle()){
                Set_Children(y, y.getLeft(), y.getRight(), null);
            } else{
                Set_Children(y, y.getLeft(), y.getMiddle(), null);
            }

            while(y != null){
                if (y.getMiddle() != null){
                    Update_Key(y);
                    Update_Size(y);
                    Update_Count(y);
                    y = y.getP();
                } else {
                    if(y != t.getRoot()){
                        y = Borrow_Or_Merge(y);
                    } else {
                        t.setRoot(y.getLeft());
                        y.getLeft().setP(null);
                        return;
                    }
                }
            }
        }
    }



}