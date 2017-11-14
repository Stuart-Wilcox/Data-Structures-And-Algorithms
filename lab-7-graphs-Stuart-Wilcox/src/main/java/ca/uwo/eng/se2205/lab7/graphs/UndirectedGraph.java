package ca.uwo.eng.se2205.lab7.graphs;

import ca.uwo.eng.se2205.lab7.travel.Itinerary;
import com.sun.deploy.util.OrderedHashSet;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Stu on 2017-04-06.
 */
public class UndirectedGraph<V, E> implements Graph<V, E> {

    Collection<UndirectedVertex> vertices;
    Collection<UndirectedEdge> edges;

    public UndirectedGraph(){
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    private class UndirectedVertex implements Vertex<V,E>{
        private V element;
        private Collection<UndirectedEdge> incidentEdges;


        private UndirectedVertex(V element){
            incidentEdges = new HashSet<>();
            this.element = element;
        }

        @Override
        public Graph<V, E> graph() {
            return UndirectedGraph.this;
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
        public int hashCode() {
            return element.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this==o || this.hashCode()==o.hashCode();
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    private class UndirectedEdge implements Edge<E,V>{
        E weight;
        UndirectedVertex u, v;

        private UndirectedEdge(Vertex<V,E> u, Vertex<V,E> v, E weight){
            this.weight = weight;
            this.u = (UndirectedVertex)u;
            this.v = (UndirectedVertex)v;
        }

        @Override
        public Graph<V, E> graph() {
            return UndirectedGraph.this;
        }

        @Override
        public E getWeight() {
            return this.weight;
        }

        @Override
        public void setWeight(@Nullable E weight) {
            this.weight = weight;
        }

        @Override
        public Vertex<V, E> u() {
            return this.u;
        }

        @Override
        public Vertex<V, E> v() {
            return this.v;
        }

        @Override
        public Vertex<V, E> opposite(Vertex<V, E> vertex) {
            if(vertex == v){
                return u;
            }else if(vertex == u){return v;}
            return null;
        }

        @Override
        public boolean contains(Vertex<V, E> vertex) {
            return u == vertex || v == vertex;
        }

        private boolean isBetween(Vertex<V,E> v, Vertex<V,E> u){
            return (this.u == u && this.v == v) || (this.u == v && this.v == u);
        }

        @Override
        public int hashCode() {
            return weight.hashCode() + 13*u.hashCode() + 31*v.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this==o || this.hashCode()==o.hashCode();
        }

        @Override
        public String toString() {
            return u.toString() + " and " + v.toString() + ": " + weight.toString();
        }
    }

    @Override
    public Collection<? extends Vertex<V, E>> vertices() {
        return new VertexCollection();
    }

    private class VertexCollection extends AbstractSet<UndirectedVertex>{
        Collection<UndirectedVertex> vertices = UndirectedGraph.this.vertices;

        @Override
        public Iterator<UndirectedVertex> iterator() {
            return new Iterator<UndirectedVertex>() {
                UndirectedVertex vertex;
                Iterator<UndirectedVertex> it = vertices.iterator();
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public UndirectedVertex next() {
                    return vertex = it.next();
                }

                @Override
                public void remove(){
                    for(UndirectedEdge edge : vertex.incidentEdges){
                        ((UndirectedVertex)edge.opposite(vertex)).incidentEdges.remove(edge);
                    }
                    it.remove();
                }
            };
        }

        @Override
        public int size() {

            return vertices.size();
        }
    }

    @Override
    public Vertex<V, E> newVertex(V element) {
        UndirectedVertex newVertex = new UndirectedVertex(element);

        if(!vertices.contains(newVertex)) {
            vertices.add(newVertex);
            return newVertex;
        }else{
            for(Vertex<V,E> vertex : vertices){
                if(newVertex.equals(vertex)){
                    return vertex;
                }
            }
        }
        return null;//should never happen
    }

    @Override
    public Collection<? extends Edge<E, V>> edges() {
        return new EdgeCollection();
    }

    private class EdgeCollection extends AbstractSet<UndirectedEdge> {
        private Collection<UndirectedEdge> edges = UndirectedGraph.this.edges;
        @Override
        public Iterator<UndirectedEdge> iterator() {
            return new Iterator<UndirectedEdge>() {
                Iterator<UndirectedEdge> it = edges.iterator();
                UndirectedEdge edge;
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public UndirectedEdge next() {
                    return edge = it.next();
                }

                @Override
                public void remove(){
                    edge.u().incomingEdges().remove(edge);
                    edge.v().incomingEdges().remove(edge);
                    it.remove();
                }
            };
        }

        @Override
        public int size() {
            return edges.size();
        }
    }

    @Override
    public Edge<E, V> newEdge(Vertex<V, E> u, Vertex<V, E> v, E weight) {
        UndirectedEdge newEdge = new UndirectedEdge(u,v, weight);

        Vertex<V,E> _u = newVertex(u.getElement());
        Vertex<V,E> _v = newVertex(v.getElement());

        if(!edges.contains(newEdge)){
            edges.add(newEdge);
        }

        //add the new edge at the vertices if they arent already there
        if(!((UndirectedVertex)_u).incidentEdges.contains(newEdge)){
            ((UndirectedVertex)_u).incidentEdges.add(newEdge);
        }
        if(!((UndirectedVertex)_v).incidentEdges.contains(newEdge)){
            ((UndirectedVertex)_v).incidentEdges.add(newEdge);
        }

        return newEdge;//return the newly created edge
    }

    @Nullable
    @Override
    public Edge<E, V> getEdge(Vertex<V, E> u, Vertex<V, E> v) {
        for(UndirectedEdge edge : edges){
            if(edge.isBetween(u, v)){
                return edge;
            }
        }
        return null;
    }

    @Override
    public Collection<? extends Edge<E, V>> incomingEdges(Vertex<V, E> v) {
        UndirectedVertex v1 = (UndirectedVertex)v;
        return v1.incidentEdges;
    }

    @Override
    public Collection<? extends Edge<E, V>> outgoingEdges(Vertex<V, E> v) {
        UndirectedVertex v1 = (UndirectedVertex)v;
        return v1.incidentEdges;
    }

    public Map<Vertex<V,E>, Edge<E,V>> DFS(Vertex<V,E> u){
        Map<Vertex<V,E>, Edge<E,V>> map = new HashMap<>();
        List<Vertex<V,E>> visited = new ArrayList<>();

        return _DFS(u, map, visited, null);
    }

    private Map<Vertex<V,E>, Edge<E,V>> _DFS(Vertex<V,E> v, Map<Vertex<V,E>, Edge<E,V>> map, List<Vertex<V,E>> visited, Edge<E,V> path){
        if(visited.contains(v)){
            return new HashMap<>();
        }
        visited.add(v);
        map.put(v, path);

        for(Edge<E,V> edge : v.incomingEdges()){
            map.putAll(_DFS(edge.opposite(v), map, visited, edge));
        }

        return map;
    }

    public Map<Vertex<V,E>, Edge<E,V>> BFS(Vertex<V,E> u){
        Map<Vertex<V,E>, Edge<E,V>> map = new HashMap<>();
        List<Vertex<V,E>> discovered = new ArrayList<>();
        List<Vertex<V,E>> explored = new ArrayList<>();

        discovered.add(u);
        map.put(u, null);

        return _BFS(u, map, discovered, explored, null);
    }

    private Map<Vertex<V,E>, Edge<E,V>> _BFS(Vertex<V,E> v, Map<Vertex<V,E>, Edge<E,V>> map, List<Vertex<V,E>> discovered, List<Vertex<V,E>> explored, Edge<E,V> path){

        explored.add(v);
        //map.put(v, path);

        for(Edge<E,V> edge : v.incomingEdges()){
            if(!discovered.contains(edge.opposite(v))) {
                map.put(edge.opposite(v), edge);
                discovered.add(edge.opposite(v));
            }
        }
        for(Edge<E,V> edge : v.incomingEdges()){
            if(explored.contains(edge.opposite(v))){
                continue;
            }
            map.putAll(_BFS(edge.opposite(v), map, discovered, explored, edge));

        }
        return map;
    }




}
