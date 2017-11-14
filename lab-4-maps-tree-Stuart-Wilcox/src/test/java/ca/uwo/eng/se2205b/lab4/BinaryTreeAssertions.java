package ca.uwo.eng.se2205b.lab4;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Useful Test methods for checking the structure of a BinaryTree
 */
public abstract class BinaryTreeAssertions {
    private BinaryTreeAssertions() {
        // no construct
    }

    /**
     * Verifies that a Tree's Pre-order structure matches the values in the {@code Iterator}.
     * @param expectedIter Values that should be found during a pre-order iteration
     * @param tree The tree to check
     * @param n The current node
     * @param <E> Type of the elements
     * @param <N> Node type
     */
    private static
    <E, N extends BinaryNode<E, N>>
    void checkPreOrderRec(Iterator<E> expectedIter, Tree<E, N> tree, N n) {

        assertTrue("Tree is too large, no more elements", expectedIter.hasNext());
        E expected = expectedIter.next();

        if (expected == null) {
            assertNull("Node was not null\n" + tree, n);
        } else {
            assertNotNull("Node is null, supposed to have element\n" + tree, n);
            assertEquals("Node element is incorrect\n" + tree, expected, n.getElement());

            checkPreOrderRec(expectedIter, tree, n.getLeft());
            checkPreOrderRec(expectedIter, tree, n.getRight());
        }
    }

    /**
     * Check the content of a Tree based on the pre-order iteration of the Tree.
     * @param expected Values that should be found during a pre-order iteration. Insert {@code null} entries for the
     *                 termination of a sub-tree.
     * @param tree The tree to check
     * @param <E> Type of the elements
     * @param <N> Node type
     */
    public static
    <E, N extends BinaryNode<E, N>>
    void checkStructure(List<E> expected, Tree<E, N> tree) {
        assertEquals("Tree is incorrect size",
                expected.stream().filter(Objects::nonNull).count(), tree.size());
        checkPreOrderRec(expected.iterator(), tree, tree.getRoot());
    }
}
