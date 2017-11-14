package ca.uwo.eng.se2205.lab7.mars;

import ca.uwo.eng.se2205.lab7.graphs.DirectedGraph;
import ca.uwo.eng.se2205.lab7.graphs.Edge;
import ca.uwo.eng.se2205.lab7.graphs.Graph;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Calculates the best location to land a rover.
 */
@ParametersAreNonnullByDefault
public class MarsPlanner {
    private int[][] topology;
    private List<Coordinates> sites;
    Graph<Coordinates, Integer> graph;

    Map<Coordinates, Map<Coordinates, Integer>> mapMap;

    /**
     * Initializes the planner with the topology of the land and the landing sites.
     *
     * @param topology Two dimensional set of heights
     * @param sites {@code List} of coordinates that must be visited
     */
    public MarsPlanner(int[][] topology, List<int[]> sites) {
        // DO NOT CHANGE THE METHOD SIGNATURE

        //topology is a 2d int array, and the abs difference between two adjacent entries is the cost to traverse
        this.topology = topology;

        //sites is a list of coordinates that MUST be visited
        this.sites = new ArrayList<>();
        for(int[] site : sites){
            this.sites.add(Coordinates.flip(Coordinates.toCoordinates(site)));//change from int[] to coordinates
        }

        //initialize some stuff
        graph = new DirectedGraph<>();
        mapMap = new HashMap<>();

        //turn the topology into a DirectedGraph
        for(int i = 0; i < topology.length; i++){
            for(int j = 0; j < topology[0].length; j++){
                Coordinates cell = new Coordinates(i,j);
                Coordinates above = new Coordinates(i-1, j);
                Coordinates below = new Coordinates(i+1, j);
                Coordinates left = new Coordinates(i, j-1);
                Coordinates right = new Coordinates(i, j+1);

                if(i != 0){
                    //add above
                    graph.newEdge(graph.newVertex(cell), graph.newVertex(above), Math.abs(topology[i][j]-topology[i-1][j]));
                }
                if(i != topology.length-1){
                    //add below
                    graph.newEdge(graph.newVertex(cell), graph.newVertex(below), Math.abs(topology[i][j]-topology[i+1][j]));
                }
                if(j != 0){
                    //add left
                    graph.newEdge(graph.newVertex(cell), graph.newVertex(left), Math.abs(topology[i][j]-topology[i][j-1]));
                }
                if(j != topology[0].length-1){
                    //add right
                    graph.newEdge(graph.newVertex(cell), graph.newVertex(right), Math.abs(topology[i][j]-topology[i][j+1]));
                }
            }
        }
        //graph has been built properly and has been verified

        //now we need to build mapMap
        ShortestPath<Coordinates> shortestPath = new ShortestPath<>(graph);

        for(int i = 0; i < topology.length; i++){
            for(int j = 0; j < topology[0].length; j++){

                shortestPath.execute(graph.newVertex(new Coordinates(i,j)));//execute dijkstra's on every coordinate
                Map<Coordinates, Integer> tempMap = new HashMap<>();//initialize the map we will put in mapMap

                for(int k = 0; k < topology.length; k++){
                    for(int l = 0; l < topology[0].length; l++){
                        if(this.sites.contains(new Coordinates(k,l))){
                            tempMap.put(new Coordinates(k,l), shortestPath.findTotalDistanceTo(graph.newVertex(new Coordinates(k,l))));//put the coordinate - distance pair in the map
                        }
                    }
                }

                mapMap.put(new Coordinates(i,j), tempMap);//fill the outer map

            }
        }
        //try every spot
        //calculate the amount of fuel it takes to get to each of the 'sites'
        //store this somewhere => in mapMap

    }

    /**
     * Calculates the best landing spot in the topology.
     *
     * @param fuelAvailable How much fuel is available daily when travelling
     * @return Coordinates for the best landing spot
     */
    public int[] bestLandingSpot(int fuelAvailable) {
        // DO NOT CHANGE THE METHOD SIGNATURE

        //read from mapMap to get best solution

        Coordinates bestStartingPoint = null;
        boolean isGoodLandingSite = false;
        int i = topology.length, j = topology[0].length;

        for(Map.Entry<Coordinates, Map<Coordinates, Integer>> __entry : mapMap.entrySet()){
            Coordinates temp = null;
            for(Map.Entry<Coordinates, Integer> entry : __entry.getValue().entrySet()){
                if(entry.getValue() <= fuelAvailable/2){
                    isGoodLandingSite = true;
                    temp = __entry.getKey();
                }
                else{
                    isGoodLandingSite = false;
                    break;
                }
            }
            if(isGoodLandingSite){
                if(__entry.getKey().i() < i || (__entry.getKey().i() == i && __entry.getKey().j() < j)) {
                    i = __entry.getKey().i();
                    j = __entry.getKey().j();
                    bestStartingPoint = temp;
                }
            }
        }
        if(bestStartingPoint == null){
            return null;
        }

        return Coordinates.flip(bestStartingPoint).toArray();
    }


    public static MarsPlanner buildModel(List<int[]> sites){
        int[][] newTopology = new int[10][];

        int[] row = new int[]{8,10,16,14,10,6,3,0,1,1};
        newTopology[0] = row;
        row = new int[]{12,14,20,16,12,8,4,0,2,1};
        newTopology[1] = row;
        row = new int[]{14,18,24,18,16,10,0,1,3,1};
        newTopology[2] = row;
        row = new int[]{14,26,26,23,20,8,0,3,2,0};
        newTopology[3] = row;
        row = new int[]{18,25,28,25,20,8,0,2,0,3};
        newTopology[4] = row;
        row = new int[]{13,24,26,26,14,0,0,0,2,6};
        newTopology[5] = row;
        row = new int[]{8,16,24,13,3,1,1,2,6,8};
        newTopology[6] = row;
        row = new int[]{4,10,3,3,0,0,5,6,8,13};
        newTopology[7] = row;
        row = new int[]{4,4,2,0,0,4,9,15,16,20};
        newTopology[8] = row;
        row = new int[]{2,2,2,1,0,10,14,18,23,24};
        newTopology[9] = row;

        return new MarsPlanner(newTopology, sites);
    }
}
