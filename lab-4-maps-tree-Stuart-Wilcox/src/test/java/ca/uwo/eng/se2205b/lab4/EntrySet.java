package ca.uwo.eng.se2205b.lab4;

import com.sun.istack.internal.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Stu on 2017-03-11.
 */
public class EntrySet<E> extends AbstractSet<E> {


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Iterator<E> iterator = new AVLTree<E>().iterator(Tree.Traversal.InOrder);
            int pos=0;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                pos++;
                return iterator.next();
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException("Didn't make it this far in the lab.");
            }
        };
    }

    @Override
    public int size() {
        return 0;
    }
}
