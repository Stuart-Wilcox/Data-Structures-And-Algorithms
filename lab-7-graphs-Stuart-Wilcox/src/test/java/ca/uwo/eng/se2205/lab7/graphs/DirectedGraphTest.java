package ca.uwo.eng.se2205.lab7.graphs;

import ca.uwo.eng.se2205.lab7.graphs.DirectedGraph;
import ca.uwo.eng.se2205.lab7.travel.Airport;
import ca.uwo.eng.se2205.lab7.travel.Flight;
import ca.uwo.eng.se2205.lab7.travel.FlightShopper;
import ca.uwo.eng.se2205.lab7.travel.Itinerary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link DirectedGraph} implementation
 */
//@Testable
//@DisplayName("A Directed Graph")
//public class DirectedGraphTest {
//
//    private DirectedGraph<String, Integer> graph;
//
//    @BeforeEach
//    void init() {
//        graph = new DirectedGraph<>();
//    }
//
//    @Nested
//    @DisplayName("creates vertices")
//    public class NewVertex {
//
//        @Test
//        public void multi() {
//
//            List<String> values = Arrays.asList("A", "B", "C", "D");
//            List<Vertex<String, Integer>> verts = new ArrayList<>(values.size());
//
//            for (String v : values) {
//                Vertex<String, Integer> vert = graph.newVertex(v);
//                assertNotNull(vert, () -> "Could not create vertex: " + v);
//                assertTrue(graph.vertices().contains(vert), () -> "Created Vertex not found: " + vert);
//                verts.add(vert);
//            }
//
//            // check they're still found
//            for (Vertex<String, Integer> vert : verts) {
//                assertTrue(graph.vertices().contains(vert), () -> "Created Vertex not found: " + vert);
//            }
//        }
//
//        @Test
//        public void duplicateElementAllowed() {
//            Vertex<String, Integer> vA = graph.newVertex("A");
//            assertNotNull(vA, "Could not create vertex: A");
//            assertNotNull(graph.vertices().contains(vA), "Could not find vertex");
//
//            Vertex<String, Integer> vA2 = graph.newVertex("A");
//            assertNotNull(vA2, "Could not create duplicate vertex: A");
//            assertNotNull(graph.vertices().contains(vA), () -> "Could not find original vertex: " + vA);
//            assertNotNull(graph.vertices().contains(vA2), () -> "Could not find duplicate vertex: " + vA2);
//        }
//
//        @Nested
//        @DisplayName("with Vertex properties")
//        class VertexOperations {
//
//            Vertex<String, Integer> vertex;
//
//            @BeforeEach
//            void createVertex() {
//                vertex = DirectedGraphTest.this.graph.newVertex("bar");
//            }
//
//            @Test
//            void canNotSetNullElement() {
//                assertThrows(NullPointerException.class, () -> vertex.setElement(null));
//            }
//
//            @Test
//            void elementBehaves() {
//                String old = vertex.getElement();
//                assertEquals(old, vertex.setElement("foo"), "Could not change element");
//                assertEquals("foo", vertex.getElement());
//            }
//        }
//    }
//
//    @Nested
//    @DisplayName("modified via edges()")
//    public class EdgesMethod {
//
//        private Collection<Edge<Integer, String>> edges;
//
//        Vertex<String, Integer> vA, vB;
//        Edge<Integer, String> eAB;
//
//        @BeforeEach
//        void init() {
//            vA = graph.newVertex("A");
//            vB = graph.newVertex("B");
//
//            assertNotNull(vA, "Could not create vertex: A");
//            assertNotNull(vB, "Could not create vertex: B");
//            this.edges = (Collection<Edge<Integer, String>>) graph.edges();
//
//            eAB = graph.newEdge(vA, vB, 0);
//            assertNotNull(eAB, () -> "Could not create edge between " + vA + " and " + vB);
//        }
//
//        private void trySuccessfulRemove(Edge<Integer, String> e) {
//            assertTrue(edges.remove(e), () -> "Could not remove: " + e);
//            assertFalse(edges.contains(e), () -> "Contained " + e + " after removal");
//        }
//
//        @Test
//        public void removeExisting() {
//            trySuccessfulRemove(eAB);
//        }
//
//        @Test
//        public void addUnsupported() {
//            assertThrows(UnsupportedOperationException.class, () -> edges.add(eAB));
//        }
//    }
//
//    @DisplayName("supports outgoingEdges()")
//    @Nested
//    public class OutgoingEdges {
//
//        Vertex<String, Integer> foo, bar;
//
//        @BeforeEach
//        void init() {
//            foo = graph.newVertex("foo");
//            assertNotNull(foo, "Could not create vertex: foo");
//            bar = graph.newVertex("bar");
//            assertNotNull(bar, "Could not create vertex: bar");
//        }
//
//        @Test
//        void singleIncoming() {
//            Edge<Integer, String> e = graph.newEdge(bar, foo, 2);
//
//            Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
//            assertNotNull(edges, "Got null collection, should never be null");
//            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);
//
//            Edge<Integer, String> edge = edges.iterator().next();
//            assertEquals(e, edge, "Incorrect edge returned");
//        }
//
//        @Test
//        void afterRemoval() {
//            Edge<Integer, String> e1 = graph.newEdge(bar, foo, 2);
//
//            final Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
//            assertNotNull(edges, "Got null collection, should never be null");
//
//            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);
//            Edge<Integer, String> edge = edges.iterator().next();
//            assertEquals(e1, edge, "Incorrect edge returned");
//
//            assertTrue(graph.edges().remove(e1), () -> "Could not remove " + e1 + " from collection: " + graph.edges());
//
//            final Collection<? extends Edge<Integer, String>> edges2 = graph.outgoingEdges(bar);
//            assertNotNull(edges2, "Got null collection, should never be null");
//            assertTrue(edges2.isEmpty(), () -> "Found invalid edge collection after removal of " + e1 + ": " + edges2);
//        }
//
//    }
//
//    @DisplayName("can query edges")
//    @Nested
//    public class EdgeQuery {
//
//        Vertex<String, Integer> foo, bar;
//
//        @BeforeEach
//        void init() {
//            foo = graph.newVertex("foo");
//            assertNotNull(foo, "Could not create vertex: foo");
//            bar = graph.newVertex("bar");
//            assertNotNull(bar, "Could not create vertex: bar");
//        }
//
//        @Test
//        void existingEdge() {
//            Edge<Integer, String> e = graph.newEdge(foo, bar, 2);
//
//            Edge<Integer, String> opt = graph.getEdge(foo, bar);
//            assertEquals(e, opt, "Wrong edge returned");
//        }
//    }
//
//    public void fillGraph(){
//        ArrayList<Airport> airports = new ArrayList<>();
//        airports.add(new Airport("ATL", 33.6366997, -84.4281006 ));//0
//        airports.add(new Airport("HND", 35.5522995, 139.7799988 ));//1
//        airports.add(new Airport("LHR", 51.4706001, -0.461941 ));//2
//        airports.add(new Airport("ORD", 41.9785996, -87.9048004 ));//3
//        airports.add(new Airport("PEK", 40.080101, 116.5849991 ));//4
//        airports.add(new Airport("PVG", 31.1434002, 121.8050003 ));//5
//        airports.add(new Airport("YYZ", 43.6772003, -79.6306 ));//6
//
//        Collection<Flight> flights = new ArrayList<>();
//        flights.add(new Flight(airports.get(0), airports.get(2), 1029.00));
//        flights.add(new Flight(airports.get(0), airports.get(3), 94.00));
//        flights.add(new Flight(airports.get(0), airports.get(6), 263.00));
//        flights.add(new Flight(airports.get(1), airports.get(2), 635.00));
//        flights.add(new Flight(airports.get(1), airports.get(3), 935.00));
//        flights.add(new Flight(airports.get(1), airports.get(5), 443.00));
//        flights.add(new Flight(airports.get(2), airports.get(0), 796.00));
//        flights.add(new Flight(airports.get(2), airports.get(5), 530.00));
//        flights.add(new Flight(airports.get(2), airports.get(6), 599.00));
//
//        for(Airport airport : airports){
//            graph.newVertex(airport.getCode());
//        }
//        for(Flight flight : flights){
//            //build the graph
//            graph.newEdge(graph.newVertex(flight.getDeparture().getCode()), graph.newVertex(flight.getArrival().getCode()), ((Double)flight.getCost()).intValue());
//        }
//    }
//
//    @Test
//    public void DirectedGraphTesting(){
//        fillGraph();
//        assertEquals(7, graph.vertices().size() );
//        assertEquals(9, graph.edges().size());
//        for(Vertex<String, Integer> vertex : graph.vertices()){
//            if(vertex.equals(graph.newVertex("ATL"))){
//                assertEquals(1, vertex.incomingEdges().size());
//                assertEquals(3, vertex.outgoingEdges().size());
//            }else if(vertex.equals(graph.newVertex("HND"))) {
//                assertEquals(0, vertex.incomingEdges().size());
//                assertEquals(3, vertex.outgoingEdges().size());
//            }else if(vertex.equals(graph.newVertex("LHR"))){
//                assertEquals(2, vertex.incomingEdges().size());
//                assertEquals(3, vertex.outgoingEdges().size());
//            }else if(vertex.equals(graph.newVertex("ORD"))){
//                assertEquals(0, vertex.outgoingEdges().size());
//                assertEquals(2, vertex.incomingEdges().size());
//            }else if(vertex.equals(graph.newVertex("PEK"))){
//                assertEquals(0, vertex.incomingEdges().size());
//                assertEquals(0, vertex.outgoingEdges().size());
//            }else if(vertex.equals(graph.newVertex("PVG"))){
//                assertEquals(0, vertex.outgoingEdges().size());
//                assertEquals(2, vertex.incomingEdges().size());
//            }else if(vertex.equals(graph.newVertex("YYZ"))){
//                assertEquals(2, vertex.incomingEdges().size());
//                assertEquals(0, vertex.outgoingEdges().size());
//            }
//        }
//    }
//
//}


import ca.uwo.eng.se2205.lab7.graphs.DirectedGraph;
import ca.uwo.eng.se2205.lab7.graphs.Edge;
import ca.uwo.eng.se2205.lab7.graphs.Graph;
import ca.uwo.eng.se2205.lab7.graphs.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link DirectedGraph} implementation
 */
@Testable
@DisplayName("A Directed Graph")
public class DirectedGraphTest {

    private Graph<String, Integer> graph;

    @BeforeEach
    void init() {
        graph = new DirectedGraph<>();
    }

    @Nested
    @DisplayName("creates vertices")
    public class NewVertex {

        @Test
        public void single() {
            Vertex<String, Integer> vA = graph.newVertex("A");
            assertNotNull(vA, "Could not create vertex");
            assertNotNull(graph.vertices().contains(vA), "Could not create vertex");
        }

        @Test
        public void two() {
            Vertex<String, Integer> vA = graph.newVertex("A");
            assertNotNull(vA, "Could not create vertex");
            assertNotNull(graph.vertices().contains(vA), "Could not create vertex");

            Vertex<String, Integer> vB = graph.newVertex("B");
            assertNotNull(vB, "Could not create vertex");
            assertNotNull(graph.vertices().contains(vB), "Could not create vertex");
        }

        @Test
        public void multi() {

            List<String> values = Arrays.asList("A", "B", "C", "D");
            List<Vertex<String, Integer>> verts = new ArrayList<>(values.size());

            for (String v : values) {
                Vertex<String, Integer> vert = graph.newVertex(v);
                assertNotNull(vert, () -> "Could not create vertex: " + v);
                assertTrue(graph.vertices().contains(vert), () -> "Created Vertex not found: " + vert);
                verts.add(vert);
            }

            // check they're still found
            for (Vertex<String, Integer> vert : verts) {
                assertTrue(graph.vertices().contains(vert), () -> "Created Vertex not found: " + vert);
            }
        }

        @Test
        public void duplicateElementAllowed() {
            Vertex<String, Integer> vA = graph.newVertex("A");
            assertNotNull(vA, "Could not create vertex: A");
            assertNotNull(graph.vertices().contains(vA), "Could not find vertex");

            Vertex<String, Integer> vA2 = graph.newVertex("A");
            assertNotNull(vA2, "Could not create duplicate vertex: A");
            assertNotNull(graph.vertices().contains(vA), () -> "Could not find original vertex: " + vA);
            assertNotNull(graph.vertices().contains(vA2), () -> "Could not find duplicate vertex: " + vA2);
        }

        @Nested
        @DisplayName("with Vertex properties")
        class VertexOperations {

            Vertex<String, Integer> vertex;

            @BeforeEach
            void createVertex() {
                vertex = DirectedGraphTest.this.graph.newVertex("bar");
            }

            @Test
            void graph() {
                assertEquals(DirectedGraphTest.this.graph, vertex.graph(), "Invalid Graph stored in Vertex");
            }

            @Test
            void canNotSetNullElement() {
                assertThrows(NullPointerException.class, () -> vertex.setElement(null));
            }


            @Test
            void elementBehaves() {
                String old = vertex.getElement();
                assertEquals(old, vertex.setElement("foo"), "Could not change element");
                assertEquals("foo", vertex.getElement());
            }
        }
    }

    @Nested
    @DisplayName("modified via vertices()")
    class VerticesMethod {

        private Collection<Vertex<String, Integer>> vertices;

        Vertex<String, Integer> vA, vB;

        @BeforeEach
        void init() {
            vA = graph.newVertex("A");
            vB = graph.newVertex("B");

            assertNotNull(vA, "Could not create vertex: A");
            assertNotNull(vB, "Could not create vertex: B");
            this.vertices = (Collection<Vertex<String, Integer>>) graph.vertices();
        }

        private void trySuccessfulRemove(Vertex<String, Integer> v) {
            assertTrue(vertices.remove(v), () -> "Could not remove: " + v);
            assertFalse(vertices.contains(v), () -> "Contained " + v + " after removal");
        }

        @Test
        public void removeExisting() {
            trySuccessfulRemove(vB);
        }

        @Test
        public void removeMultiple() {
            trySuccessfulRemove(vA);
            trySuccessfulRemove(vB);

            // now empty
            assertFalse(vertices.remove(vA), () -> "Successfully removed non-existant vertex: " + vA);
        }

        @Test
        public void failToAdd() {
            assertThrows(UnsupportedOperationException.class, () -> vertices.add(vA));
        }
    }


    @Nested
    @DisplayName("modified via edges()")
    class EdgesMethod {

        private Collection<Edge<Integer, String>> edges;

        Vertex<String, Integer> vA, vB;
        Edge<Integer, String> eAB;

        @BeforeEach
        void init() {
            vA = graph.newVertex("A");
            vB = graph.newVertex("B");

            assertNotNull(vA, "Could not create vertex: A");
            assertNotNull(vB, "Could not create vertex: B");
            this.edges = (Collection<Edge<Integer, String>>) graph.edges();

            eAB = graph.newEdge(vA, vB, 0);
            assertNotNull(eAB, () -> "Could not create edge between " + vA + " and " + vB);
        }

        private void trySuccessfulRemove(Edge<Integer, String> e) {
            assertTrue(edges.remove(e), () -> "Could not remove: " + e);
            assertFalse(edges.contains(e), () -> "Contained " + e + " after removal");
        }

        @Test
        public void removeExisting() {
            trySuccessfulRemove(eAB);
        }

        @Test
        public void removeMultiple() {
            trySuccessfulRemove(eAB);

            // now empty
            assertFalse(edges.remove(eAB), () -> "Successfully removed non-existent edge: " + eAB);
        }

        @Test
        public void addUnsupported() {
            assertThrows(UnsupportedOperationException.class, () -> edges.add(eAB));
        }
    }

    @DisplayName("supports incomingEdges()")
    @Nested
    class IncomingEdges {

        Vertex<String, Integer> foo, bar;

        @BeforeEach
        void init() {
            foo = graph.newVertex("foo");
            assertNotNull(foo, "Could not create vertex: foo");
            bar = graph.newVertex("bar");
            assertNotNull(bar, "Could not create vertex: bar");
        }

        @Test
        void nullVertex() {
            assertThrows(NullPointerException.class, () -> graph.incomingEdges(null));
        }

        @Test
        void emptyIncoming() {
            Collection<? extends Edge<Integer, String>> edges = graph.incomingEdges(foo);
            assertNotNull(edges, "Got null collection, should never be null");
            assertTrue(edges.isEmpty(), "Found non-empty edge collection for unconnected vertex");
        }

        @Test
        void singleIncoming() {
            Edge<Integer, String> e = graph.newEdge(bar, foo, 2);

            Collection<? extends Edge<Integer, String>> edges = graph.incomingEdges(foo);
            assertNotNull(edges, "Got null collection, should never be null");
            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);

            Edge<Integer, String> edge = edges.iterator().next();
            assertEquals(e, edge, "Incorrect edge returned");
        }

        @Test
        void multiIncoming() {
            Vertex<String, Integer> baz = graph.newVertex("baz");
            assertNotNull(baz, "Could not create vertex: baz");

            Edge<Integer, String> e1 = graph.newEdge(bar, foo, 2);
            Edge<Integer, String> e2 = graph.newEdge(baz, foo, 3);

            Collection<? extends Edge<Integer, String>> edges = graph.incomingEdges(foo);
            assertNotNull(edges, "Got null collection, should never be null");

            assertEquals(2, edges.size(), () -> "Found invalid edge collection: " + edges);
            assertTrue(edges.contains(e1), () -> "Could not find edge: " + e1 + " in collection: " + edges);
            assertTrue(edges.contains(e2), () -> "Could not find edge: " + e2 + " in collection: " + edges);
        }

        @Test
        void afterRemoval() {
            Edge<Integer, String> e1 = graph.newEdge(bar, foo, 2);

            final Collection<? extends Edge<Integer, String>> edges = graph.incomingEdges(foo);
            assertNotNull(edges, "Got null collection, should never be null");

            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);
            Edge<Integer, String> edge = edges.iterator().next();
            assertEquals(e1, edge, "Incorrect edge returned");

            assertTrue(graph.edges().remove(e1), () -> "Could not remove " + e1 + " from collection: " + graph.edges());

            final Collection<? extends Edge<Integer, String>> edges2 = graph.incomingEdges(foo);
            assertNotNull(edges2, "Got null collection, should never be null");
            assertTrue(edges2.isEmpty(), () -> "Found invalid edge collection after removal of " + e1 + ": " + edges2);
        }

    }

    @DisplayName("supports outgoingEdges()")
    @Nested
    class OutgoingEdges {

        Vertex<String, Integer> foo, bar;

        @BeforeEach
        void init() {
            foo = graph.newVertex("foo");
            assertNotNull(foo, "Could not create vertex: foo");
            bar = graph.newVertex("bar");
            assertNotNull(bar, "Could not create vertex: bar");
        }

        @Test
        void nullVertex() {
            assertThrows(NullPointerException.class, () -> graph.outgoingEdges(null));
        }

        @Test
        void emptyIncoming() {
            Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
            assertNotNull(edges, "Got null collection, should never be null");
            assertTrue(edges.isEmpty(), "Found non-empty edge collection for unconnected vertex");
        }

        @Test
        void singleIncoming() {
            Edge<Integer, String> e = graph.newEdge(bar, foo, 2);

            Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
            assertNotNull(edges, "Got null collection, should never be null");
            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);

            Edge<Integer, String> edge = edges.iterator().next();
            assertEquals(e, edge, "Incorrect edge returned");
        }

        @Test
        void multiIncoming() {
            Vertex<String, Integer> baz = graph.newVertex("baz");
            assertNotNull(baz, "Could not create vertex: baz");

            Edge<Integer, String> e1 = graph.newEdge(bar, foo, 2);
            Edge<Integer, String> e2 = graph.newEdge(baz, foo, 3);

            final Collection<? extends Edge<Integer, String>> barOut = graph.outgoingEdges(bar);
            assertNotNull(barOut, "Got null collection, should never be null");

            assertEquals(1, barOut.size(), () -> "Found invalid edge collection: " + barOut);
            assertTrue(barOut.contains(e1), () -> "Could not find edge: " + e1 + " in collection: " + barOut);

            final Collection<? extends Edge<Integer, String>> bazOut = graph.outgoingEdges(baz);
            assertNotNull(bazOut, "Got null collection, should never be null");

            assertEquals(1, bazOut.size(), () -> "Found invalid edge collection: " + bazOut);
            assertTrue(bazOut.contains(e2), () -> "Could not find edge: " + e2 + " in collection: " + bazOut);
        }

        @Test
        void afterRemoval() {
            Edge<Integer, String> e1 = graph.newEdge(bar, foo, 2);

            final Collection<? extends Edge<Integer, String>> edges = graph.outgoingEdges(bar);
            assertNotNull(edges, "Got null collection, should never be null");

            assertEquals(1, edges.size(), () -> "Found invalid edge collection: " + edges);
            Edge<Integer, String> edge = edges.iterator().next();
            assertEquals(e1, edge, "Incorrect edge returned");

            assertTrue(graph.edges().remove(e1), () -> "Could not remove " + e1 + " from collection: " + graph.edges());

            final Collection<? extends Edge<Integer, String>> edges2 = graph.outgoingEdges(bar);
            assertNotNull(edges2, "Got null collection, should never be null");
            assertTrue(edges2.isEmpty(), () -> "Found invalid edge collection after removal of " + e1 + ": " + edges2);
        }

    }

    @DisplayName("can query edges")
    @Nested
    class EdgeQuery {

        Vertex<String, Integer> foo, bar;

        @BeforeEach
        void init() {
            foo = graph.newVertex("foo");
            assertNotNull(foo, "Could not create vertex: foo");
            bar = graph.newVertex("bar");
            assertNotNull(bar, "Could not create vertex: bar");
        }

        @Test
        void noEdges() {
            Edge<Integer, String> edge = graph.getEdge(foo, bar);

            assertNull(edge, "Edge should be null");
        }

        @Test
        void existingEdge() {
            Edge<Integer, String> e = graph.newEdge(foo, bar, 2);

            Edge<Integer, String> opt = graph.getEdge(foo, bar);
            assertEquals(e, opt, "Wrong edge returned");
        }
    }

}
