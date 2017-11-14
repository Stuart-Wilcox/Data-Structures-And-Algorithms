package ca.uwo.eng.se2205.lab7.travel;

import ca.uwo.eng.se2205.lab7.graphs.DirectedGraph;
import ca.uwo.eng.se2205.lab7.graphs.Edge;
import ca.uwo.eng.se2205.lab7.graphs.Graph;
import ca.uwo.eng.se2205.lab7.graphs.Vertex;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.CollationElementIterator;
import java.util.*;

/**
 * Optimized utility for shopping for flights.
 */
@ParametersAreNonnullByDefault
public final class FlightShopper {

    private DirectedGraph<Airport, Double> graph;
    private Set<Airport> airports;
    private List<Vertex<Airport, Double>> nodes;
    private List<Edge<Double,Airport>> edges;
    private Set<Vertex<Airport,Double>> settledNodes;
    private Set<Vertex<Airport,Double>> unSettledNodes;
    private Map<Vertex<Airport,Double>, Vertex<Airport,Double>> predecessors;
    private Map<Vertex<Airport, Double>, Double> distance;
    private Collection<Flight> flightList;
    private List<Itinerary> itineraryList;

    /**
     * Builds a {@code FlightShopper} via a {@link Set} of {@link Airport}s and {@link Flight}s.
     *
     * @param airports The available airports
     * @param flights All available flights
     */
    public FlightShopper(Collection<? extends Airport> airports, Collection<? extends Flight> flights) {
        // DO NOT CHANGE THE METHOD SIGNATURE
        // Initialize your shopper

        //initialize everything
        flightList = new ArrayList<>();
        graph = new DirectedGraph<>();
        this.airports = new HashSet<>();
        itineraryList = new ArrayList<>();


        for(Airport airport : airports){
            graph.newVertex(airport);//create a new node for every vertex
            this.airports.add(airport);//add the airports to the airport list
        }


        for(Flight flight : flights){
            graph.newEdge(graph.newVertex(flight.getDeparture()), graph.newVertex(flight.getArrival()), flight.getCost());//add the flights as edges to the graph
            flightList.add(flight);//add the flights to the flight list
        }

        for(Airport airport : this.airports){
            doDijkstraThings(graph, graph.getVertex(airport));//perform dijkstra's algorithm, with the airport node as a reference starting point

            List<Flight> temporaryFlightList = new LinkedList<>();//initialize

            for(Map.Entry<Vertex<Airport,Double>, Double> entry : distance.entrySet()){
                //iterate over the distance map entries
                //to build an itinerary for each flight from 'airport' to entry.getKey().getElement()


                temporaryFlightList = this.getFlight(entry.getKey().getElement(), airport);//get the flight list of the order of flights taken for the shortest path

                Itinerary tempItinerary;//the itinerary that we will add to the overall list, which we will query from in #price()

                if(temporaryFlightList.size() != 0) {
                    tempItinerary = new Itinerary(temporaryFlightList);//the current itinerary we will add to the list
                }
                else{
                    tempItinerary = null;//there are no flights out of this airport, but we cant make an itinerary out of 0 flights
                }
                itineraryList.add(tempItinerary);//add the current itinerary to the list
            }
        }
    }


    /**
     * Finds the cheapest flight from two {@link Airport}s.
     * @param from Starting airport
     * @param to Ending airport
     * @return Cheapest {@code Itinerary} to fly between {@code from} and {@code to}
     */
    public Itinerary price(Airport from, Airport to) {
        //query from a previously created list of itineraries

        if(itineraryList == null){
            System.out.println("Itinerary is broken. Please try again later.");return null;
        }

        for (Itinerary itinerary : itineraryList){
            if(itinerary == null){
                continue;//done to avoid NullPointerException below. Itineraries can be null if there is no such Itinerary from : 'from' to 'to', ie they arent connected
            }
            if(itinerary.getArrival().equals(to) && itinerary.getDeparture().equals(from)){
                return itinerary;
            }
        }
        System.out.println("No itinerary available from " + from.toString() + " to " + to.toString());return null;
    }

    public Collection<Airport> getAirports(){
        return airports;
    }

    public Collection<Flight> getFlights(){
        return flightList;
    }

    private List<Flight> getFlight(Airport a, Airport b){
        if(!predecessors.containsKey(graph.newVertex(a))){
            return new LinkedList<>();
        }
        List<Flight> temporaryFlightList = new LinkedList<>();
        temporaryFlightList=getFlightRecursive(a,b,temporaryFlightList);
        return temporaryFlightList;
    }

    private List<Flight> getFlightRecursive(Airport a, Airport b, List<Flight> list){
        if(predecessors.get(graph.newVertex(a)).equals(graph.newVertex(b))){
            list.add(getFlightBetween(a,b));
            return list;
        }else{
            list.add(getFlightBetween(a,b));
            if(predecessors.get(b) == null){
                return new LinkedList<>();
            }
            return getFlightRecursive(b, predecessors.get(b).getElement(), list);
        }
    }

    private Flight getFlightBetween(Airport a, Airport b){
        for(Flight flight : flightList){
            if(flight.getArrival().equals(a) && flight.getDeparture().equals(b)){
                return flight;
            }
        }
        return null;
    }

    private void doDijkstraThings(Graph<Airport,Double> Graph, Vertex<Airport, Double> vertex){
        //airports is our set of all airports
        this.nodes = new ArrayList<>(Graph.vertices());
        this.edges = new ArrayList<>(Graph.edges());

        //initialize everything
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(vertex, 0.0);
        unSettledNodes.add(vertex);
        while(unSettledNodes.size() > 0){
            Vertex<Airport,Double> node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex<Airport,Double> node){
        List<Vertex<Airport,Double>> adjacentNodes = getNeighbours(node);
        for(Vertex<Airport,Double> target : adjacentNodes){
            if(getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)){
                distance.put(target, getShortestDistance(node) + getDistance(node,target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private Double getDistance(Vertex<Airport,Double> node, Vertex<Airport,Double> target){
        for(Edge<Double,Airport> edge : edges){
            if(edge.u().equals(node) && edge.v().equals(target)){
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Not connected?");
    }

    private Vertex<Airport,Double> getMinimum(Set<Vertex<Airport,Double>> vertices){
        Vertex<Airport,Double> minimum = null;
        for(Vertex<Airport,Double> vertex : vertices){
            if(minimum == null){
                minimum = vertex;
            }else{
                if(getShortestDistance(vertex) < getShortestDistance(minimum)){
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private List<Vertex<Airport,Double>> getNeighbours(Vertex node){
        List<Vertex<Airport,Double>> neighbours = new ArrayList<>();
        for(Edge<Double,Airport> edge : edges){
            if(edge.u().equals(node) && !settledNodes.contains(edge.v())){
                neighbours.add(edge.v());
            }
        }
        return neighbours;
    }

    private Double getShortestDistance(Vertex<Airport,Double> destination){
        Double d = distance.get(destination);
        if(d == null){
            return new Double(Integer.MAX_VALUE);
        }
        else{
            return d;
        }
    }

    private LinkedList<Vertex<Airport,Double>> getPath(Vertex<Airport,Double> target){
        LinkedList<Vertex<Airport,Double>> path = new LinkedList<>();
        Vertex<Airport,Double> step = target;

        if(predecessors.get(step) == null){
            throw new RuntimeException("No path exists");
        }
        path.add(step);
        while(predecessors.get(step) != null){
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    public static FlightShopper airportModel(){
        Collection<Flight> csvFlights = new ArrayList<>();
        ArrayList<Airport> csvAirports = new ArrayList<>();

        csvAirports.add(new Airport("ATL", 33.6366997, -84.4281006 ));//0
        csvAirports.add(new Airport("HND", 35.5522995, 139.7799988 ));//1
        csvAirports.add(new Airport("LHR", 51.4706001, -0.461941 ));//2
        csvAirports.add(new Airport("ORD", 41.9785996, -87.9048004 ));//3
        csvAirports.add(new Airport("PEK", 40.080101, 116.5849991 ));//4
        csvAirports.add(new Airport("PVG", 31.1434002, 121.8050003 ));//5
        csvAirports.add(new Airport("YYZ", 43.6772003, -79.6306 ));//6


        csvFlights.add(new Flight(csvAirports.get(0), csvAirports.get(2), 1029.0));
        csvFlights.add(new Flight(csvAirports.get(0), csvAirports.get(3), 94.0));
        csvFlights.add(new Flight(csvAirports.get(0), csvAirports.get(6), 263.0));
        csvFlights.add(new Flight(csvAirports.get(1), csvAirports.get(2), 635.0));
        csvFlights.add(new Flight(csvAirports.get(1), csvAirports.get(5), 935.0));
        csvFlights.add(new Flight(csvAirports.get(1), csvAirports.get(6), 443.0));
        csvFlights.add(new Flight(csvAirports.get(2), csvAirports.get(0), 796.0));
        csvFlights.add(new Flight(csvAirports.get(2), csvAirports.get(5), 530.0));
        csvFlights.add(new Flight(csvAirports.get(2), csvAirports.get(6), 599.0));
        csvFlights.add(new Flight(csvAirports.get(3), csvAirports.get(0), 109.0));
        csvFlights.add(new Flight(csvAirports.get(3), csvAirports.get(1), 971.0));
        csvFlights.add(new Flight(csvAirports.get(3), csvAirports.get(4), 748.0));
        csvFlights.add(new Flight(csvAirports.get(4), csvAirports.get(1), 300.0));
        csvFlights.add(new Flight(csvAirports.get(4), csvAirports.get(2), 324.0));
        csvFlights.add(new Flight(csvAirports.get(4), csvAirports.get(5), 230.0));
        csvFlights.add(new Flight(csvAirports.get(4), csvAirports.get(6), 803.0));
        csvFlights.add(new Flight(csvAirports.get(5), csvAirports.get(4), 230.0));
        csvFlights.add(new Flight(csvAirports.get(6), csvAirports.get(0), 265.0));
        csvFlights.add(new Flight(csvAirports.get(6), csvAirports.get(2), 567.0));
        csvFlights.add(new Flight(csvAirports.get(6), csvAirports.get(3), 172.0));

        return new FlightShopper(csvAirports, csvFlights);
    }
}
