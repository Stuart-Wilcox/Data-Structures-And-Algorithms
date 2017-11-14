package ca.uwo.eng.se2205b.lab03;

import javax.annotation.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * Base for both {@link Tree} and {@link Tree.Node}
 */
interface TreeBase {
    /**
     * Get the size of the tree
     * @return Number of nodes in the Tree
     */
    int size();

    /**
     * True if there is no elements in the tree.
     * @return {@code true} if there are no nodes
     */
    boolean isEmpty();

    /**
     * Get the height of the tree
     * @return height of this tree.
     */
    int height();


    /**
     * Checks if the current tree is proper. That is, every node has either 2 nodes or zero.
     * @return {@code true} if the tree is proper
     */
    boolean isProper();

    /**
     * Checks if the current tree is balanced. Every node's height is within at most 1 from it's sibling nodes.
     * @return {@code true} if the tree is balanced
     */
    boolean isBalanced();
}

/**
 * Describes a generic Tree interface
 */
@ParametersAreNonnullByDefault
public interface Tree<E extends Comparable<E>> extends TreeBase {

    /**
     * Describes a Node within the context of a Tree
     * @param <E>
     */
    interface Node<E extends Comparable<E>> extends TreeBase {

        /**
         * Get the element stored in the node.
         * @return Element stored, non-{@code null}
         */
        E getElement();

        /**
         * Get all of the children of the Node
         *
         * @return Non-{@code null}, possibly empty {@code Set} of children
         */
        @Nonnull
        Collection<? extends Node<E>> children();

        /**
         * True if the node is internal
         * @return {@code true} if the node is an internal node
         */
        boolean isInternal();

        /**
         * True if the node is a Leaf node
         * @return {@code true} if a leaf
         */
        boolean isLeaf();
    }

    /**
     * Describes how to traverse a {@code Tree}
     */
    enum Traversal {
        InOrder,
        PreOrder,
        PostOrder;
    }

    /**
     * Gets an iterator based on {@code how}
     * @param how What traversal method to use
     * @return Iterator that traverses by {@code how}
     *
     * @throws UnsupportedOperationException if unsupported traversal type
     */
    Iterator<E> iterator(Traversal how);

    /**
     * True if the element is in the tree.
     * @param element Element to find
     * @return {@code true} if within the tree
     */
    boolean contains(E element);

    /**
     * Add the element as a child of the current tree
     * @param element Element to add
     * @return {@code true} if inserted
     *
     * @throws UnsupportedOperationException if unsupported
     */
    boolean put(E element);

    /**
     * Removes the element from the Tree
     * @param element Element to remove
     * @return {@code true} if removed
     *
     * @throws UnsupportedOperationException if unsupported
     */
    boolean remove(E element);


    /**
     * Get the root node of the tree.
     * @return The root of the tree, possibly {@code null}.
     */
    @Nullable Node<E> getRoot();
}
