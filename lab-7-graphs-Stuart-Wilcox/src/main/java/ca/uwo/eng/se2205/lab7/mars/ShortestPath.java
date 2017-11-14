package ca.uwo.eng.se2205.lab7.mars;

import ca.uwo.eng.se2205.lab7.graphs.Edge;
import ca.uwo.eng.se2205.lab7.graphs.Graph;
import ca.uwo.eng.se2205.lab7.graphs.Vertex;

import java.util.*;

/**
 * Created by Stu on 2017-04-09.
 */

//class that conducts shortest path algorithm
class ShortestPath<V> {
    private final List<Vertex<V,Integer>> nodes;
    private final List<Edge<Integer,V>> edges;
    private Set<Vertex<V,Integer>> settledNodes;
    private Set<Vertex<V,Integer>> unSettledNodes;
    private Map<Vertex<V,Integer>, Vertex<V,Integer>> predecessors;
    private Map<Vertex<V,Integer>, Integer> distance;
    private Integer eMAX;

    public ShortestPath(Graph<V,Integer> graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex<V,Integer>>(graph.vertices());
        this.edges = new ArrayList<Edge<Integer,V>>(graph.edges());
    }

    public Integer findTotalDistanceTo(Vertex<V,Integer> vertex){
        for(Map.Entry<Vertex<V,Integer>, Integer> entry : distance.entrySet()){
            if(entry.getKey().equals(vertex)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void execute(Vertex<V,Integer> source) {
        this.eMAX = Integer.MAX_VALUE;
        settledNodes = new HashSet<Vertex<V,Integer>>();
        unSettledNodes = new HashSet<Vertex<V,Integer>>();
        distance = new HashMap<Vertex<V,Integer>, Integer>();
        predecessors = new HashMap<Vertex<V,Integer>, Vertex<V,Integer>>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex<V,Integer> node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex<V,Integer> node) {
        List<Vertex<V,Integer>> adjacentNodes = getNeighbors(node);
        for (Vertex<V,Integer> target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private Integer getDistance(Vertex<V,Integer> node, Vertex<V,Integer> target) {
        for (Edge<Integer,V> edge : edges) {
            if (edge.u().equals(node)
                    && edge.v().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex<V,Integer>> getNeighbors(Vertex<V,Integer> node) {
        List<Vertex<V,Integer>> neighbors = new ArrayList<Vertex<V,Integer>>();
        for (Edge<Integer,V> edge : edges) {
            if (edge.u().equals(node)
                    && !isSettled(edge.v())) {
                neighbors.add(edge.v());
            }
        }
        return neighbors;
    }

    private Vertex<V,Integer> getMinimum(Set<Vertex<V,Integer>> vertexes) {
        Vertex<V,Integer> minimum = null;
        for (Vertex<V,Integer> vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (Integer.compare(getShortestDistance(vertex), getShortestDistance(minimum)) < 0) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex<V,Integer> vertex) {
        return settledNodes.contains(vertex);
    }

    private Integer getShortestDistance(Vertex<V,Integer> destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return eMAX;
        } else {
            return d;
        }
    }

    public LinkedList<Vertex<V,Integer>> getPath(Vertex<V,Integer> target) {
        LinkedList<Vertex<V,Integer>> path = new LinkedList<Vertex<V,Integer>>();
        Vertex<V,Integer> step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }







}
