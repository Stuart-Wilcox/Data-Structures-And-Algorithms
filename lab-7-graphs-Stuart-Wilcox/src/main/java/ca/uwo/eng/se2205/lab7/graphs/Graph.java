package ca.uwo.eng.se2205.lab7.graphs;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;

/**
 * Describes a graph with {@link Vertex} and {@link Edge}s.
 */
@ParametersAreNonnullByDefault
public interface Graph<V, E> {

    /**
     * Returns a collection of {@link Vertex} that works with the
     * {@code Graph}. Any change to the {@link Collection} will affect
     * the {@code Graph} itself.
     *
     * @return {@code Graph}-backed {@code Collection}
     */
    Collection<? extends Vertex<V, E>> vertices();

    /**
     * Creates a new {@link Vertex} instance adding it to the {@code Graph}
     * @param element The stored element
     * @return New non-{@code null} {@code Vertex}
     */
    Vertex<V, E> newVertex(V element);


    /**
     * Gets a collection of {@link Edge}s that is backed by the
     * {@code Graph}. Any change to the {@code Collection} will affect the
     * {@code Graph} itself.
     *
     * @return {@code Graph}-backed {@code Collection}
     */
    Collection<? extends Edge<E, V>> edges();

    /**
     * Create a new {@link Edge} instance or returns the existing {@code Edge}
     * @param u
     * @param v
     * @param weight
     * @return New non-{@code null} {@code Edge.Weighted}
     *
     * @throws ClassCastException if the {@link Vertex} implementation is not correct
     */
    Edge<E, V> newEdge(Vertex<V, E> u, Vertex<V, E> v, E weight);

    /**
     * Returns an edge between {@param u} and {@param v}. If the {@code Graph}
     * is Directed the order matters.
     * @param u First {@code Vertex}
     * @param v Second {@code Vertex}
     * @return Edge between two {@link Vertex}, {@code null} if it doesn't exist
     *
     * @throws ClassCastException if the {@link Vertex} implementation is not correct
     */
    @Nullable Edge<E, V> getEdge(Vertex<V, E> u, Vertex<V, E> v);

    
    /**
     * Gets the edges entering a {@link Vertex}
     * @param v
     * @return Non-{@code null} collection of {@link Edge}s, but possibly empty
     *
     * @throws ClassCastException if the {@link Vertex} implementation is not correct
     */
    Collection<? extends Edge<E, V>> incomingEdges(Vertex<V, E> v);

    /**
     * Gets the edges exiting a {@link Vertex}
     * @param v
     * @return Non-{@code null} collection of {@link Edge}s, but possibly empty
     *
     * @throws ClassCastException if the {@link Vertex} implementation is not correct
     */
    Collection<? extends Edge<E, V>> outgoingEdges(Vertex<V, E> v);

}
