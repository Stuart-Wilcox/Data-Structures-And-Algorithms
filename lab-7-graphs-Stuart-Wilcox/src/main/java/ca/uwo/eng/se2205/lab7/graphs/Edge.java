package ca.uwo.eng.se2205.lab7.graphs;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Describes an edge between two {@link Vertex} with a weight, {@code <E>}. To make an
 * unweighted edge, use the type {@link Void}.
 *
 * @param <E> type of the weight
 * @param <V> type of the {@link Vertex#getElement()}, should match {@link Vertex#<V>}
 */
@ParametersAreNonnullByDefault
public interface Edge<E, V> {

    /**
     * Get the owning {@link Graph} of this {@link Edge}.
     *
     * @return Non-{@code null} graph instance
     */
    Graph<V, E> graph();

    /**
     * Get the weight
     * @return the weight
     */
    E getWeight();

    /**
     * Set the weight
     * @param weight New weight
     *
     * @throws UnsupportedOperationException if immutable
     */
    void setWeight(@Nullable E weight);

    /**
     * Get {@code u}, the first {@link Vertex}. If the edge is {@link Undirected} the order does
     * not matter.
     *
     * @return First {@code Vertex}
     */
    Vertex<V, E> u();

    /**
     * Get {@code v}, the second {@link Vertex}. If the edge is {@link Undirected} the order does
     * not matter.
     *
     * @return Second {@code Vertex}
     */
    Vertex<V, E> v();

    /**
     * Get the {@link Vertex} opposite the passed {@link Vertex}.
     *
     * @param vertex Vertex within the edge
     * @return Opposite vertex to the passed parameter
     *
     * @throws IllegalArgumentException if {@code v} is not attached to the {@code Edge}
     */
    Vertex<V, E> opposite(Vertex<V, E> vertex);

    /**
     * Returns {@code true} if the vertex is in this edge
     * @param vertex The vertex to check against
     * @return
     *
     * @throws NullPointerException if vertex is {@code null}
     */
    default boolean contains(Vertex<V, E> vertex) {
        checkNotNull(vertex, "vertex == null");

        return (u().equals(vertex) || v().equals(vertex));
    }


    /**
     * Defines a directed edge
     * @param <V>
     */
    interface Directed<E, V> extends Edge<E, V> {

    }

    /**
     * Defines an undirected edge
     * @param <V>
     */
    interface Undirected<E, V> extends Edge<E, V> {

    }

}
