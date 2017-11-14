package ca.uwo.eng.se2205.lab7.graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link UndirectedGraph} implementation
 */
//@Testable
//@DisplayName("An Undirected Graph")
//public class UndirectedGraphTest {
//
//    private Graph<String, Integer> graph;
//
//    @BeforeEach
//    void init() {
//        graph = new UndirectedGraph<>();
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
//                vertex = UndirectedGraphTest.this.graph.newVertex("bar");
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
//    class EdgesMethod {
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
//    class OutgoingEdges {
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
//    class EdgeQuery {
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
//    @Test
//    public void myTest(){
//        graph.newVertex("Oshawa");
//        graph.newVertex("London");
//        graph.newVertex("Toronto");
//        graph.newVertex("Ottawa");
//        graph.newVertex("Windsor");
//
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("London"), 239);
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("Toronto"), 61);
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("Ottawa"), 393);
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("Windsor"), 416);
//        graph.newEdge(graph.newVertex("London"), graph.newVertex("Toronto"), 195);
//        graph.newEdge(graph.newVertex("London"), graph.newVertex("Ottawa"), 628);
//        graph.newEdge(graph.newVertex("London"), graph.newVertex("Windsor"), 191);
//        graph.newEdge(graph.newVertex("Toronto"), graph.newVertex("Ottawa"), 450);
//        graph.newEdge(graph.newVertex("Toronto"), graph.newVertex("Windsor"), 369);
//        Edge<Integer, String> myEdge = graph.newEdge(graph.newVertex("Ottawa"), graph.newVertex("Windsor"), 804);
//        Edge<Integer,String> myOtherEdge = graph.getEdge(graph.newVertex("Ottawa"), graph.newVertex("Windsor"));
//
//        assertEquals(5, graph.vertices().size());
//        assertEquals(10, graph.edges().size());
//        assertEquals(myEdge, myOtherEdge);
//        if(myEdge != myOtherEdge){
//            throw new RuntimeException();
//        }
//    }
//
//    @Test
//    public void DFStest(){
//
//        UndirectedGraph<String, Integer> graph = new UndirectedGraph<>();
//
//        graph.newVertex("Oshawa");
//        graph.newVertex("London");
//        graph.newVertex("Toronto");
//        graph.newVertex("Ottawa");
//        graph.newVertex("Windsor");
//
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("London"), 239);
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("Toronto"), 61);
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("Ottawa"), 393);
//        graph.newEdge(graph.newVertex("Oshawa"), graph.newVertex("Windsor"), 416);
//        graph.newEdge(graph.newVertex("London"), graph.newVertex("Toronto"), 195);
//        graph.newEdge(graph.newVertex("London"), graph.newVertex("Ottawa"), 628);
//        graph.newEdge(graph.newVertex("London"), graph.newVertex("Windsor"), 191);
//        graph.newEdge(graph.newVertex("Toronto"), graph.newVertex("Ottawa"), 450);
//        graph.newEdge(graph.newVertex("Toronto"), graph.newVertex("Windsor"), 369);
//        graph.newEdge(graph.newVertex("Ottawa"), graph.newVertex("Windsor"), 804);
//
//        Map<Vertex<String,Integer>, Edge<Integer,String>> testMap = new HashMap<>();
//
//        testMap = graph.DFS(graph.newVertex("Oshawa"));
//
//        System.out.println();
//    }
//
//    @Test
//    public void BFStest(){
//
//        UndirectedGraph<String, Integer> graph = new UndirectedGraph<>();
//
//        Vertex<String,Integer> a = graph.newVertex("A");
//        Vertex<String,Integer> b = graph.newVertex("B");
//        Vertex<String,Integer> c = graph.newVertex("C");
//        Vertex<String,Integer> d = graph.newVertex("D");
//        Vertex<String,Integer> e = graph.newVertex("E");
//        Vertex<String,Integer> f = graph.newVertex("F");
//        Vertex<String,Integer> g = graph.newVertex("G");
//        Vertex<String,Integer> h = graph.newVertex("H");
//        Vertex<String,Integer> i = graph.newVertex("I");
//        Vertex<String,Integer> j = graph.newVertex("J");
//        Vertex<String,Integer> k = graph.newVertex("K");
//        Vertex<String,Integer> l = graph.newVertex("L");
//        Vertex<String,Integer> m = graph.newVertex("M");
//        Vertex<String,Integer> n = graph.newVertex("N");
//        Vertex<String,Integer> o = graph.newVertex("O");
//        Vertex<String,Integer> p = graph.newVertex("P");
//        Vertex<String,Integer> q = graph.newVertex("Q");
//        Vertex<String,Integer> r = graph.newVertex("R");
//        Vertex<String,Integer> s = graph.newVertex("S");
//        Vertex<String,Integer> t = graph.newVertex("T");
//
//        graph.newEdge(a, f, 1);
//        graph.newEdge(a, k, 1);
//        graph.newEdge(a, l, 1);
//        graph.newEdge(b, g, 1);
//        graph.newEdge(b, h, 1);
//        graph.newEdge(c, e, 1);
//        graph.newEdge(c, h, 1);
//        graph.newEdge(c, e, 1);
//        graph.newEdge(d, h, 1);
//        graph.newEdge(d, i, 1);
//        graph.newEdge(e, i, 1);
//        graph.newEdge(e, j, 1);
//        graph.newEdge(f, l, 1);
//        graph.newEdge(g, p, 1);
//        graph.newEdge(g, r, 1);
//        graph.newEdge(h, l, 1);
//        graph.newEdge(h, q, 1);
//        graph.newEdge(h, n, 1);
//        graph.newEdge(j, n, 1);
//        graph.newEdge(l, p, 1);
//        graph.newEdge(m, o, 1);
//        graph.newEdge(m, q, 1);
//        graph.newEdge(n, r, 1);
//        graph.newEdge(o, s, 1);
//        graph.newEdge(m, t, 1);
//        graph.newEdge(p, r, 1);
//        graph.newEdge(r, s, 1);
//
//        Map<Vertex<String, Integer>, Edge<Integer,String>> testMap = new HashMap<>();
//
//        testMap = graph.BFS(a);
//
//        System.out.println();
//
//    }
//
//}


