package ca.uwo.eng.se2205.lab7.graphs;

import javax.annotation.Nullable;
import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Stu on 2017-04-06.
 */
public class DirectedGraph<V,E> implements Graph<V,E> {
    private Collection<DirectedVertex> vertices;
    private Collection<DirectedEdge> edges;

    public DirectedGraph(){
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    private class DirectedVertex implements Vertex<V,E>{
        V element;
        Collection<DirectedEdge> incomingEdges;
        Collection<DirectedEdge> outgoingEdges;

        private DirectedVertex(V element){
            if(element == null){
                throw new NullPointerException();
            }
            this.element = element;
            incomingEdges = new ArrayList<>();
            outgoingEdges = new ArrayList<>();
        }

        @Override
        public Graph<V, E> graph() {
            return DirectedGraph.this;
        }

        @Override
        public V getElement() {
            return element;
        }

        @Override
        public V setElement(V element) {
            if(element == null){
                throw new NullPointerException();
            }
            V returnVal = this.element;
            this.element = element;
            return returnVal;
        }

        @Override
        public Collection<? extends Edge<E, V>> incomingEdges() {
            return incomingEdges;
        }

        @Override
        public Collection<? extends Edge<E, V>> outgoingEdges() {
            return outgoingEdges;
        }

        @Override
        public String toString() {
            return element.toString();
        }

        @Override
        public boolean equals(Object o) {
            return this==o || this.hashCode() == o.hashCode();
        }

        @Override
        public int hashCode() {
            return element.hashCode();
        }
    }

    private class DirectedEdge implements Edge<E,V>{
        E weight;
        DirectedVertex u, v;

        private DirectedEdge(Vertex<V,E> u, Vertex<V,E> v, E weight){
            this.weight = weight;
            this.u = (DirectedVertex)u;
            this.v = (DirectedVertex)v;
        }

        @Override
        public Graph<V, E> graph() {
            return DirectedGraph.this;
        }

        @Override
        public E getWeight() {
            return weight;
        }

        @Override
        public void setWeight(@Nullable E weight) {
            this.weight = weight;
        }

        @Override
        public Vertex<V, E> u() {
            return u;
        }

        @Override
        public Vertex<V, E> v() {
            return v;
        }

        @Override
        public Vertex<V, E> opposite(Vertex<V, E> vertex) {
            if(vertex == u){return v;}
            else if(vertex == v){return u;}
            return null;
        }

        @Override
        public boolean contains(Vertex<V, E> vertex) {
            return u==vertex || v==vertex;
        }

        private boolean isBetween(Vertex<V,E> v1, Vertex<V,E> v2){
            return u == v1 && v == v2;
        }

        @Override
        public String toString() {
            return u.toString() + " to " + v.toString() + " : " + weight.toString();
        }

        @Override
        public int hashCode() {
            return weight.hashCode() + 13*u.hashCode() + 31*v.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this==o || this.hashCode() == o.hashCode();
        }
    }


    @Override
    public Collection<? extends Vertex<V, E>> vertices() {
        return new VertexCollection();
    }

    private class VertexCollection extends AbstractSet<DirectedVertex>{

        Collection<DirectedVertex> vertices;

        private VertexCollection(){
            this.vertices = DirectedGraph.this.vertices;
        }

        @Override
        public Iterator<DirectedVertex> iterator() {
            return new Iterator<DirectedVertex>() {
                Iterator<DirectedVertex> it = vertices.iterator();
                DirectedVertex vertex;
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public DirectedVertex next() {
                    return vertex = it.next();
                }

                @Override
                public void remove(){
                    for(DirectedEdge edge : vertex.incomingEdges){
                        edge.opposite(vertex).outgoingEdges().remove(edge);
                    }
                    for(DirectedEdge edge : vertex.outgoingEdges){
                        edge.opposite(vertex).incomingEdges().remove(edge);
                    }
                    it.remove();

                }

            };
        }

        @Override
        public int size() {
            return DirectedGraph.this.vertices.size();
        }
    }

    @Override
    public Vertex<V, E> newVertex(V element) {

        //make a new vertex
        DirectedVertex newVertex = new DirectedVertex(element);

        //if it already exists then return the previously existing reference, otherwise add it and return it
        if(!vertices().contains(newVertex)) {
            vertices.add(newVertex);
            return newVertex;
        }else{
            for(DirectedVertex vertex : vertices){
                if(newVertex.equals(vertex)){
                    return vertex;
                }
            }
        }
        return null;
    }

    @Override
    public Collection<? extends Edge<E, V>> edges() {
        return new EdgeCollection();
    }

    private class EdgeCollection extends AbstractSet<DirectedEdge>{

        Collection<DirectedEdge> edges;

        private EdgeCollection(){
            this.edges = DirectedGraph.this.edges;
        }

        @Override
        public Iterator<DirectedEdge> iterator() {
            return new Iterator<DirectedEdge>() {
                Iterator<DirectedEdge> it = edges.iterator();
                DirectedEdge directedEdge;
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public DirectedEdge next() {
                    return directedEdge = it.next();
                }

                @Override
                public void remove(){
                    directedEdge.u().outgoingEdges().remove(directedEdge);
                    directedEdge.v().incomingEdges().remove(directedEdge);
                    it.remove();
                }
            };
        }

        @Override
        public int size() {
            return DirectedGraph.this.edges.size();
        }
    }

    @Override
    public Edge<E, V> newEdge(Vertex<V, E> u, Vertex<V, E> v, E weight) {

        //call the new vertex function to get any references to existing vertices if needed
        Vertex<V,E> _u = newVertex(u.getElement());
        Vertex<V,E> _v = newVertex(v.getElement());

        //make the new edge
        DirectedEdge newEdge = new DirectedEdge(u, v, weight);

        if(!edges.contains(newEdge)){
            //add the new edge to the edges list if it isnt already there
            edges.add(newEdge);
        }

        //add the new edge at the vertices if they arent already there
        if(!((DirectedVertex)_u).outgoingEdges.contains(newEdge)){
            ((DirectedVertex)_u).outgoingEdges.add(newEdge);
        }
        if(!((DirectedVertex)_v).incomingEdges.contains(newEdge)){
            ((DirectedVertex)_v).incomingEdges.add(newEdge);
        }

        return newEdge;//return the newly created edge
    }

    @Nullable
    @Override
    public Edge<E, V> getEdge(Vertex<V, E> u, Vertex<V, E> v) {
        for(DirectedEdge edge: edges){
            if(edge.isBetween(u, v)){return edge;}
        }
        return null;
    }

    @Override
    public Collection<? extends Edge<E, V>> incomingEdges(Vertex<V, E> v) {
        DirectedVertex u = (DirectedVertex)v;
        return u.incomingEdges();
    }

    @Override
    public Collection<? extends Edge<E, V>> outgoingEdges(Vertex<V, E> v) {
        DirectedVertex u = (DirectedVertex)v;
        return u.outgoingEdges();
    }

    public Vertex<V,E> getVertex(V element){
        for(Vertex<V,E> vertex : vertices){
            if(vertex.getElement().equals(element)){
                return vertex;
            }
        }
        return null;
    }
}
