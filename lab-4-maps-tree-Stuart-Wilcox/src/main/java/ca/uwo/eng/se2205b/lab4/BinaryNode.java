package ca.uwo.eng.se2205b.lab4;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a Binary Node
 */
@ParametersAreNonnullByDefault
public abstract class BinaryNode<E, N extends BinaryNode<E, N>> implements Tree.Node<E> {

    abstract @Nullable N getLeft();

    abstract N setLeft(@Nullable N left);

    abstract @Nullable N getRight();

    abstract N setRight(@Nullable N right);

    abstract @Nullable N getParent();

    abstract @Nullable N setParent(@Nullable N parent);

    abstract int getSize();

    @Override
    public @Nonnull List<? extends N> children() {
        List<N> childList = new ArrayList<>();

        addToList(this, childList);//go to recursive function
        return childList;
    }
    private void addToList(BinaryNode<E, N> n, List<N> l){
        //add this node as well as any children nodes to the list then returns it
        l.add((N)n);
        if(n.getLeft() != null){
            addToList(n.getLeft(),l);
        }
        if(n.getRight() != null){
            addToList(n.getRight(), l);
        }
    }



    @Override
    public int height() {
        if(isEmpty()){
            return 0;
        }
        int hL = 0, hR = 0;
        if(this.getLeft() != null){
            hL = this.getLeft().height();//recursive step
        }
        if(this.getRight() != null){
            hR = this.getRight().height();//recursive step
        }
        if(hL > hR){
            return hL + 1;
        }
        else{
            return hR + 1;
        }
    }

    @Override
    public int size() {
        return getSize();
    }

    @Override
    public boolean isBalanced() {
        if(this.getLeft()!=null ^ this.getRight()!=null){
            return false;
        }
        if(this.getLeft() != null){
            if(!this.getLeft().isBalanced()){//recursive step
                return false;
            }
        }
        if(this.getRight() != null){
            if(!this.getRight().isBalanced()){//recursive step
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isProper() {
        //Returns true if every leaf node has a height difference of less than or equal to 1 between itself and every other leaf node.
        List<Integer> list = new ArrayList<>();
        leafHeights(list);
        if(list.isEmpty()){
            return true;
        }

        int max = list.get(0);
        int min = list.get(0);
        for(int i = 0; i< list.size();i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
            if(list.get(i) < min){
                min = list.get(i);
            }
        }


        return Math.abs(max - min) < 2;
    }
    protected List<Integer> leafHeights(List<Integer> list){
        if(this.isLeaf()){
            list.add(this.height());
            return list;
        }
        if(this.getLeft() != null){
            this.getLeft().leafHeights(list);
        }
        if(this.getRight() != null){
            this.getRight().leafHeights(list);
        }
        return list;
    }

    @Override
    public boolean isInternal() {
        return getParent() != null && (getLeft() != null || getRight() != null);
    }

    @Override
    public boolean isLeaf() {
        return getLeft() == null && getRight() == null;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }
}
