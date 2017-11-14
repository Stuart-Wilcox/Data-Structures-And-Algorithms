package ca.uwo.eng.se2205.lab7.graphs;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;

/**
 * Describes a vertex/node in a {@link Graph}
 *
 * @param <E> type of the weight of the Edge,
 * @param <V> type of the element
 */
@ParametersAreNonnullByDefault
public interface Vertex<V, E> {

    /**
     * Get the owning {@link Graph} of this {@link Edge}.
     *
     * @return Non-{@code null} graph instance
     */
    Graph<V, E> graph();

    /**
     * Returns the stored element
     * @return Non-{@code null} element stored
     */
    V getElement();

    /**
     * Set the element in the Vertex
     * @param element Non-{@code null} element
     * @return The previous element
     */
    V setElement(V element);

    /**
     * Gets the edges entering {@code this}
     * @return Non-{@code null} collection of {@link Edge}s, but possibly empty
     *
     * @throws ClassCastException if the {@link Vertex} implementation is not correct
     */
    default Collection<? extends Edge<E, V>> incomingEdges() {
            return graph().incomingEdges(this);
    }

    /**
     * Gets the edges exiting {@code this}
     * @return Non-{@code null} collection of {@link Edge}s, but possibly empty
     *
     * @throws ClassCastException if the {@link Vertex} implementation is not correct
     */
    default Collection<? extends Edge<E, V>> outgoingEdges() {
        return graph().incomingEdges(this);
    }

    /**
     * The {@code #hashCode()} and {@link #equals(Object)} methods of a {@code Vertex} are based off of identity of
     * the {@link Vertex}.
     *
     * @return Unique hash of the <em>identity</em> of the {@code Vertex}
     *
     * @see #equals(Object)
     * @see System#identityHashCode(Object)
     */
    int hashCode();

    /**
     * The {@code #equals(Object)} and {@link #hashCode()} methods of a {@code Vertex} must be based off of identity of
     * the {@code Vertex}, not state.
     * @param o Object used for comparison if equal
     * @return {@code true} if {@code this == o}
     *
     * @see #hashCode()
     */
    boolean equals(Object o);
}
