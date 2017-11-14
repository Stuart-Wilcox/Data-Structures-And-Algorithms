package ca.uwo.eng.se2205b.lab4;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test an AVL tree
 */
public class AVLTreeTest {
    AVLTree<Integer> underTest = new AVLTree<>();
    @Test
    public void removes() throws Exception {
        // Check how the AVL tree works with the `remove()` method
        int j = 25;
        for(int i = j; i>0; i--){
            underTest.put(i);
        }
        for(int i = j;i >0;i--){
            underTest.remove(i);
        }
        underTest.remove(1);
        assertEquals(0, underTest.size());
    }

    @Test
    public void puts() throws Exception {
        // Check how the AVL tree works with `put()` values in the tree
        assertTrue(underTest.put(10));//putting stuff in
        assertTrue(underTest.contains(10));//make sure its there
        assertFalse(underTest.put(10));//can't put in a copy
        underTest.put(9);//put in more stuff
        assertTrue(underTest.contains(9));
        underTest.put(8);
        underTest.put(6);
        underTest.put(7);
        underTest.put(5);


        assertTrue(underTest.contains(8));
        assertTrue(underTest.contains(7));
        assertTrue(underTest.contains(6));
        assertTrue(underTest.contains(5));

        underTest.put(99);
        underTest.put(67);
        underTest.put(97);
        underTest.put(-1);

        assertTrue(underTest.contains(99));
        assertTrue(underTest.contains(67));
        assertTrue(underTest.contains(97));
        assertTrue(underTest.contains(-1));
    }

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        assertEquals(0, underTest.size());
        assertTrue(underTest.isEmpty());

        assertTrue(underTest.put(10));
        assertEquals(1, underTest.size());
        assertFalse(underTest.isEmpty());

        assertTrue(underTest.put(5));
        assertEquals(2, underTest.size());

        assertFalse(underTest.put(5));
        assertEquals(2, underTest.size());


        underTest.put(99);
        underTest.put(67);
        underTest.put(97);
        assertEquals(5, underTest.size());

        for(int i = 0; i<5;i++){
            underTest.put(i);
        }

        assertEquals(10, underTest.size());


    }

    @Test
    public void height() throws Exception {
        // check an empty tree and after adding/removing
        assertEquals(0, underTest.height());
        underTest.put(10);
        assertEquals(1, underTest.height());
        underTest.put(5);
        assertEquals(2, underTest.height());
        underTest.put(15);
        assertEquals(2, underTest.height());
    }


    @Test
    public void iterator() throws Exception {
        // Check iterators
        underTest.put(50);
        int prev = 50;
        int curr = 50;
        for(int i = 1;i<=8;i*=2){
            curr /= 2;
            for(int j = 0;j<i*2;j++){
                underTest.put(curr + prev*j);
            }
            prev /= 2;
        }
        Iterator<Integer> inOrder = underTest.iterator(Tree.Traversal.InOrder);
        Iterator<Integer> postOrder = underTest.iterator(Tree.Traversal.PostOrder);
        Iterator<Integer> preOrder = underTest.iterator(Tree.Traversal.PreOrder);

        Integer[] l1,l2,l3;
        l1 = new Integer[underTest.size()];
        l2 = new Integer[underTest.size()];
        l3 = new Integer[underTest.size()];

        Integer[] r1 = {3, 6, 9, 12, 15, 18, 21, 25, 27, 30, 33, 37, 39, 42, 45, 50, 51, 54, 57, 62, 63, 66, 69, 75, 78, 81, 87, 90, 93};
        Integer[] r2 = {50, 25, 12, 6, 3, 9, 18, 15, 21, 37, 30, 27, 33, 42, 39, 45, 75, 62, 54, 51, 57, 66, 63, 69, 87, 78, 81, 90, 93};
        Integer[] r3 = {3, 9, 6, 15, 21, 18, 12, 27, 33, 30, 39, 45, 42, 37, 25, 51, 57, 54, 63, 69, 66, 62, 81, 78, 93, 90, 87, 75, 50};
        int i = 0;
        while(inOrder.hasNext()){
            l1[i++] = inOrder.next();
        }
        i=0;
        while(preOrder.hasNext()){
            l2[i++] = preOrder.next();
        }
        i=0;
        while(postOrder.hasNext()){
            l3[i++] = postOrder.next();
        }
        assertTrue(Arrays.equals(l1, r1));
        assertTrue(Arrays.equals(l2, r2));
        assertTrue(Arrays.equals(l3, r3));
    }

    @Test
    public void contains() throws Exception {
        // Actually in the tree, not in..
        assertFalse(underTest.contains(10));
        underTest.put(10);
        assertTrue(underTest.contains(10));
        underTest.put(5);
        assertTrue(underTest.contains(5));
    }

    @Test
    public void isProper() throws Exception {
        // Check if proper
        assertTrue(underTest.isProper());
        underTest.put(50);
        assertTrue(underTest.isProper());
        underTest.put(25);
        assertTrue(underTest.isProper());
        underTest.put(10);
        assertTrue(underTest.isProper());
        underTest.put(60);
        assertTrue(underTest.isProper());
        underTest.put(70);
        assertTrue(underTest.isProper());
        underTest.put(5);
        assertTrue(underTest.isProper());
        underTest.put(-50);
        assertTrue(underTest.isProper());
    }

    @Test
    public void isBalanced() throws Exception {
        // Make sure it's /always/ balanced
        assertTrue(underTest.isBalanced());//empty tree is balanced
        underTest.put(50);
        assertTrue(underTest.isBalanced());//one element tree is balanced
        underTest.put(25);
        assertFalse(underTest.isBalanced());//two elements can never be balanced
        underTest.put(10);
        assertTrue(underTest.isBalanced());//notice we put 3 elements in descending order and it is balanced!
        underTest.put(9);
        assertFalse(underTest.isBalanced());//four elements not balanced
        underTest.put(8);
        assertTrue(underTest.isBalanced());//5 elements balanced
    }

}