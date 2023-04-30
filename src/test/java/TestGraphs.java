import org.example.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestGraphs {
//    @Test
//    void Test1() {
//        Graph graph = new Graph();
//        double[][] g = {{0,1,2},{2,0,56},{15 ,153 , 0}};
//        graph.setGraphForTEST(g);
//
//        double[][] result = graph.floydWarshall();
//        boolean neg_cycle = graph.neg_cycle_floyed;
//        assertEquals(false , neg_cycle);
//    }
//
//
//    @Test
//    void Test2() {
//        Graph graph = new Graph();
//        double[][] g = {{0,1,1,0,0,0,0},{0,0,0,4,0,0,0},{0 ,1 , 0,0,0,0,0},{0,0,-6,0,1,1,0},{0,0,0,0,0,1,1},{0 ,0 , 0,0,0,0,1},{0,0,0,0,0,0,0}};
//        graph.setGraphForTEST(g);
//
//        double[][] result = graph.floydWarshall();
//        boolean neg_cycle = graph.neg_cycle_floyed;
//        assertEquals(true , neg_cycle);
//    }
//
//
//    @Test
//    void Test3() {
//        Graph graph = new Graph();
//        double[][] g = {{0,1,0 , 0},{0,0,-1,0},{0 ,0 , 0 , -1} , {-1 , 0,0,0}};
//        graph.setGraphForTEST(g);
//
//        double[][] result = graph.floydWarshall();
//        boolean neg_cycle = graph.neg_cycle_floyed;
//        assertEquals(true , neg_cycle);
//    }
//
//
//    @Test
//    void Test4() {
//        Graph graph = new Graph();
//        double[][] g = {{0,3,8 , 0,5},{0,0,0,1,7},{0 ,4 , 0,-5,0} ,{2,0,0 , 0,0},{0,0,0,6,0}};
//        graph.setGraphForTEST(g);
//
//        double[][] result = graph.floydWarshall();
//        boolean neg_cycle = graph.neg_cycle_floyed;
//        assertEquals(false , neg_cycle);
//    }
//    // Test for Dijkstra

    @Test
    // Test with a graph that has only one node
    public void testDijkstra1() {
        Graph graph = new Graph("src/test/testDijkstra1.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        graph.dijkstra(0, costs, parents);
        double[] expectedDist = {0};
        assertArrayEquals(expectedDist, costs);
    }

    @Test
    // Test with a graph that has only two nodes and a single edge
    public void testDijkstra2() {
        Graph graph = new Graph("src/test/testDijkstra2.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[][] g = {
                {0, 1},
                {1, 0}
        };
        graph.setGraphForTEST(g);
        double[] expectedDist = {0, 1};
        graph.dijkstra(0, costs, parents);
        assertArrayEquals(expectedDist, costs);
    }

    @Test
    // Test with a graph that has a cycle
    public void testDijkstra3() {
        Graph graph = new Graph("src/test/testDijkstra3.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 3};
        graph.dijkstra(0, costs, parents);
        assertArrayEquals(expectedDist, costs);
    }

    @Test
    // Test with a graph that has a disconnected node
    public void testDijkstra4() {
        Graph graph = new Graph("src/test/testDijkstra4.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 2, Double.POSITIVE_INFINITY};
        graph.dijkstra(0, costs, parents);
        graph.bellmanFord(0, costs2, parents);
        graph.printGraph();
        assertArrayEquals(expectedDist, costs);
        assertArrayEquals(expectedDist, costs2);

    }

    @Test
    // Test with a graph that has negative edges
    public void testDijkstra5() {
        Graph graph = new Graph("src/test/testDijkstra5.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 2};
        graph.dijkstra(0, costs, parents);
        assertArrayEquals(expectedDist, costs);
    }

    @Test
    // Test with a graph that has all non-negative edges
    public void testDijkstra6() {
        Graph graph = new Graph("src/test/testDijkstra6.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 2};
        graph.dijkstra(0, costs, parents);
        assertArrayEquals(expectedDist, costs);
    }

    @Test
    // Test with a graph that has a node with no outgoing edges
    public void testDijkstra7() {
        Graph graph = new Graph("src/test/testDijkstra7.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 2};
        graph.dijkstra(0, costs, parents);
        assertArrayEquals(expectedDist, costs);
    }

    @Test
    // Test with a graph that has a node with no incoming edges
    public void testDijkstra8() {
        Graph graph = new Graph("src/test/testDijkstra8.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {2, 3, 0};
        graph.dijkstra(2, costs, parents);
        graph.bellmanFord(2, costs, parents);
        assertArrayEquals(expectedDist, costs2);
        assertArrayEquals(expectedDist, costs);
    }

    @Test
// Test with a graph that has multiple shortest paths
    public void testDijkstra9() {
        Graph graph = new Graph("src/test/testDijkstra9.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 1, 2};
        graph.bellmanFord(0,costs, parents);
        graph.dijkstra(0,costs, parents);
        assertArrayEquals(expectedDist, costs);
        assertArrayEquals(expectedDist, costs2);
    }

    @Test
    // Test with a large graph
    public void testDijkstra10() {
        Graph graph = new Graph("src/test/testDijkstra10.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 2, 2, 3, 3, 4, 5, 8, 5};
        graph.bellmanFord(0,costs, parents);
        graph.dijkstra(0,costs2, parents);
        assertArrayEquals(expectedDist, costs);
        assertArrayEquals(expectedDist, costs2);
    }

    @Test
    // Test with a large graph
    public void testDijkstra11() {
        Graph graph = new Graph("src/test/testDijkstra11.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDistDijkstra = {0, 2, 7, 3, 9, 5, 2};
        double[] expectedDistBellman = {0, 2, 7, 3, 9, 5, 2};
        graph.bellmanFord(0,costs, parents);
        graph.dijkstra(0,costs2, parents);
        graph.printGraph();
        System.out.println(Arrays.toString(costs2));
        assertArrayEquals(expectedDistBellman, costs);
        assertArrayEquals(expectedDistDijkstra, costs2);
    }
}