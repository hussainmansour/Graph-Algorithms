import org.example.Graph;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestGraphs {

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
        graph.bellmanFord(2, costs2, parents);
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
        graph.bellmanFord(0, costs2, parents);
        graph.dijkstra(0, costs, parents);
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
        graph.bellmanFord(0, costs, parents);
        graph.dijkstra(0, costs2, parents);
        assertArrayEquals(expectedDist, costs);
        assertArrayEquals(expectedDist, costs2);
    }

    @Test
    // Test with a large graph
    public void testDijkstra11() {
        Graph graph = new Graph("testGraph.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDistDijkstra = {0, 5, 10, 10, 8};
        double[] expectedDistBellman = {0, 5, 10, 10, 8};
        graph.bellmanFord(0, costs, parents);
        graph.dijkstra(0, costs2, parents);
        assertArrayEquals(expectedDistBellman, costs);
        assertArrayEquals(expectedDistDijkstra, costs2);
    }

    // basic input
    @Test
    void TestBellmanFord1() {
        Graph graph = new Graph("src/test/testBellmanFord1.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 3, 6, 10, 15};
        int[] expectedPath = {-1, 0, 1, 2, 3, 4};
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertArrayEquals(costs, expectedDist);
        assertArrayEquals(parents, expectedPath);
    }

    // graph with a single node
    @Test
    void TestBellmanFord2() {
        Graph graph = new Graph("src/test/testBellmanFord2.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0};
        int[] expectedPath = {-1};
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertArrayEquals(costs, expectedDist);
        assertArrayEquals(parents, expectedPath);
    }


    // graph that has negative weights but no negative cycle
    @Test
    public void TestBellmanFord3() {
        Graph graph = new Graph("src/test/testBellmanFord3.txt");
        double[] costs = new double[graph.size()];
        double[] costs2 = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDistDijkstra = {0, 2, 7, 4, 9, 6, 8};
        double[] expectedDistBellman = {0, 2, 7, 3, 9, 5, 2};
        graph.bellmanFord(0, costs, parents);
        graph.dijkstra(0, costs2, parents);
        assertArrayEquals(expectedDistBellman, costs);
        assertArrayEquals(expectedDistDijkstra, costs2);
    }


    // graph that all edges has negative weight but no negative cycles
    @Test
    void TestBellmanFord4() {
        Graph graph = new Graph("src/test/testBellmanFord4.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, -5, -37, -15, -8};
        int[] expectedPath = {-1, 0, 3, 1, 1};
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertArrayEquals(costs, expectedDist);
        assertArrayEquals(parents, expectedPath);
    }

    // graph that has one negative cycle
    @Test
    void TestBellmanFord5() {
        Graph graph = new Graph("src/test/testBellmanFord5.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertFalse(graph.getHasNoNegativeCycle());
    }

    // graph that has two negative cycles
    @Test
    void TestBellmanFord6() {
        Graph graph = new Graph("src/test/testBellmanFord6.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertFalse(graph.getHasNoNegativeCycle());
    }

    // graph that is only a negative cycle
    @Test
    void TestBellmanFord7() {
        Graph graph = new Graph("src/test/testBellmanFord7.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertFalse(graph.getHasNoNegativeCycle());
    }

    // graph with negative cycle but source is first node in this cycle
    @Test
    void TestBellmanFord8() {
        Graph graph = new Graph("src/test/testBellmanFord8.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertFalse(graph.getHasNoNegativeCycle());
    }

    // disconnected graph
    @Test
    void TestBellmanFord9() {
        Graph graph = new Graph("src/test/testBellmanFord9.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1, 3, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        int[] expectedPath = {-1, 0, 1, -1, -1};
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertArrayEquals(costs, expectedDist);
        assertArrayEquals(parents, expectedPath);
    }

    // graph with self loops
    @Test
    void TestBellmanFord10() {
        Graph graph = new Graph("src/test/testBellmanFord10.txt");
        double[] costs = new double[graph.size()];
        int[] parents = new int[graph.size()];
        double[] expectedDist = {0, 1};
        int[] expectedPath = {-1, 0};
        graph.setHasNoNegativeCycle(graph.bellmanFord(0, costs, parents));
        assertArrayEquals(costs, expectedDist);
        assertArrayEquals(parents, expectedPath);
    }

    @Test
    void TestFloyd1() {
        Graph graph = new Graph();
        double[][] g = {{0, 1, 2}, {2, 0, 56}, {15, 153, 0}};
        graph.setGraphForTEST(g);
        double[][] costs = new double[g.length][g.length];
        int[][] predecessors = new int[g.length][g.length];

        graph.setHasNoNegativeCycle(graph.floydWarshall(costs, predecessors));
        assertTrue(graph.getHasNoNegativeCycle());
    }


    @Test
    void TestFloyd2() {
        Graph graph = new Graph();
        double[][] g = {
                {0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 4, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0},
                {0, 0, -6, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0}};
        graph.setGraphForTEST(g);
        double[][] costs = new double[g.length][g.length];
        int[][] predecessors = new int[g.length][g.length];

        System.out.println(g.length);
        graph.setHasNoNegativeCycle(graph.floydWarshall(costs, predecessors));
        assertFalse(graph.getHasNoNegativeCycle());
    }


    @Test
    void TestFloyd3() {
        Graph graph = new Graph();
        double[][] g = {{0, 1, 0, 0}, {0, 0, -1, 0}, {0, 0, 0, -1}, {-1, 0, 0, 0}};
        graph.setGraphForTEST(g);
        double[][] costs = new double[g.length][g.length];
        int[][] predecessors = new int[g.length][g.length];

        graph.setHasNoNegativeCycle(graph.floydWarshall(costs, predecessors));
        assertFalse(graph.getHasNoNegativeCycle());
    }


    @Test
    void TestFloyd4() {
        Graph graph = new Graph();
        double[][] g = {
                {0, 3, 8, 0, 5},
                {0, 0, 0, 1, 7},
                {0, 4, 0, -5, 0},
                {2, 0, 0, 0, 0},
                {0, 0, 0, 6, 0}};
        graph.setGraphForTEST(g);
        double[][] costs = new double[g.length][g.length];
        int[][] predecessors = new int[g.length][g.length];

        graph.setHasNoNegativeCycle(graph.floydWarshall(costs, predecessors));
        assertTrue(graph.getHasNoNegativeCycle());
    }


    @Test
    void TestFloyd5() {
        Graph graph = new Graph();
        double[][] g = {
                {0, 4, 0, 0, -2},
                {0, 0, -1, 4, 2},
                {0, 0, 0, 0,0},
                {0, 0, -6, 0, 1},
                {0, 0, 0, 0, 0}
        };
        graph.setGraphForTEST(g);
        double[][] costs = new double[g.length][g.length];
        int[][] predecessors = new int[g.length][g.length];

        graph.setHasNoNegativeCycle(graph.floydWarshall(costs, predecessors));
        assertTrue(graph.getHasNoNegativeCycle());
    }


    @Test
    void TestFloyd6() {
        Graph graph = new Graph();
        double[][] g = {
                {0, 2, 0, -1, 0, 0},
                {0, 0, 3, 2, 0, 0},
                {0, 0, 0, 0, 1, 0},
                {-4, 0, 0, 0, 4, 0},
                {0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0}
        };
        graph.setGraphForTEST(g);
        double[][] costs = new double[g.length][g.length];
        int[][] predecessors = new int[g.length][g.length];

        graph.setHasNoNegativeCycle(graph.floydWarshall(costs, predecessors));
        assertFalse(graph.getHasNoNegativeCycle());
    }

}