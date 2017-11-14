package ca.uwo.eng.se2205b.lab03;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Binary Search Tree
 */
public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> underTest = new BinarySearchTree<>();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        underTest = new BinarySearchTree<>();
        assertEquals(underTest.size(), 0);
        assertTrue(underTest.isEmpty());
        underTest.put(50);
        assertEquals(underTest.size(), 1);
        assertFalse(underTest.isEmpty());
        generateTree();
        assertEquals(underTest.size(), 29);
        assertFalse(underTest.isEmpty());
    }

    @Test
    public void height() throws Exception {
        // check an empty tree and after adding/removing
        Tree<Integer> tree = new BinarySearchTree<>();
        assertEquals(tree.height(), 0);
        tree.put(50);
        assertEquals(tree.height(), 1);
        tree.put(75);
        assertEquals(tree.height(), 2);
        tree.put(25);
        assertEquals(tree.height(), 2);
        tree.put(80);
        assertEquals(tree.height(), 3);

        tree.remove(80);
        assertEquals(tree.height(), 2);
        tree.remove(25);
        tree.remove(75);
        assertEquals(tree.height(), 1);
        tree.remove(50);
        assertEquals(tree.height(), 0);
    }

    @Test
    public void put() throws Exception {
        // check the return result, adding/removing
        Tree<Integer> tree = new BinarySearchTree<>();
        assertEquals(tree.put(50), true);
        assertEquals(tree.put(75), true);
        assertEquals(tree.put(25), true);
        assertEquals(tree.put(50), false);

        Tree<Integer> t = new BinarySearchTree<>();
        t.put(50);
        for(int i = 0; i <10; i++){
            for(int j = 0;j<i*2;j++){
                t.put(j*50/i);
            }
        }
    }

    @Test
    public void remove() throws Exception {
        // Removing nodes, remember the cases
        underTest = new BinarySearchTree<>();
        generateTree();

        //case 1: Removing nodes that do not exist
        //assertEquals(tree.remove(-10), false);
        //assertEquals(tree.remove(120), false);

        //case 2: Removing leaves
        for(int i = 3; i <64; i+=6){
            assertEquals(underTest.remove(i), true);
        }

        //case 3: removing internal nodes
        for(int i =12;i<88;i+=25){
            assertEquals(underTest.remove(i), true);
        }
        //case 4: removing the root
        assertEquals(underTest.remove(50), true);
        assertEquals(underTest.remove(underTest.getRoot().getElement()), true);
    }


    @Test
    public void iterator() throws Exception {
        // Check the three different types of iteration
        underTest = new BinarySearchTree<>();
        generateTree();
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
        underTest = new BinarySearchTree<>();
        generateTree();

        //case 1: not in the tree
        assertFalse(underTest.contains(0));
        assertFalse(underTest.contains(10));
        assertFalse(underTest.contains(-10));

        //case 2: in the tree
        assertTrue(underTest.contains(3));
        assertTrue(underTest.contains(6));
        assertTrue(underTest.contains(12));
        assertTrue(underTest.contains(25));
        assertTrue(underTest.contains(50));
    }

    @Test
    public void isBalanced() throws Exception {
        // Check the null condition, complete, incomplete..
        generateTree();
        underTest.put(77);
        underTest.put(88);//these are done to completely fill the tree

        //case 1: balanced
        assertTrue((underTest.isBalanced()));

        underTest.remove(3);

        //case 2: non balanced
        assertFalse(underTest.isBalanced());

        //case 3: null
        underTest=new BinarySearchTree<>();
        underTest.isBalanced();
    }

    @Test
    public void isProper() throws Exception {
        // Check the null condition, complete, incomplete..
        underTest = new BinarySearchTree<>();

        //case 1: null
        assertTrue(underTest.isProper());


        generateTree();
        //case 2: proper
        assertTrue(underTest.isProper());

        underTest.remove(93);
        underTest.remove(90);
        underTest.remove(81);
        underTest.remove(78);
        //case 3: not proper
        assertFalse(underTest.isProper());
    }

    public void generateTree(){
        //makes a nicely balanced tree
//                                     50
//                    25   	                           75
//            12              37              62               87
//         6      18      30      42      54      66      78      90
//        3	9	15	21	27 33	39	45	51	57	63	69	NA	81	NA	93
//        size = 29

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

    }

}