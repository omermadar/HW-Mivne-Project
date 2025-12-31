public class Two_Three_Tree<T extends Comparable<T>> extends Two_Three_Tree_Multiple<T> {

    public Two_Three_Tree(T minKey, T maxKey) {
        super(minKey, maxKey);
    }


    @Override
    public void Insert(Two_Three_Tree_Multiple<T> t, TT_Node<T> z){
         // This is the code from the Lecture
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
                }
            }

            if(z != null){
                TT_Node<T> w = new TT_Node<T>(null, null, null, null, null, null);
                Set_Children(w, x, z, null);
                t.setRoot(w);
            }
    }
    @Override
    public void Delete(Two_Three_Tree_Multiple<T> t, TT_Node<T> x){
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