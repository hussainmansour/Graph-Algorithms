import org.example.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGraphs {

    @Test
    void Test1() {
        Graph graph = new Graph();
        double[][] g = {{0, 1, 2}, {2, 0, 56}, {15, 153, 0}};
        graph.setGraphForTEST(g);

        double[][] result = graph.floydWarshall();
        boolean neg_cycle = graph.getHasNoNegativeCycle();
        Assertions.assertFalse(neg_cycle);
    }


    @Test
    void Test2() {
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

        double[][] result = graph.floydWarshall();
        boolean neg_cycle = graph.getHasNoNegativeCycle();
        Assertions.assertTrue(neg_cycle);
    }


    @Test
    void Test3() {
        Graph graph = new Graph();
        double[][] g = {{0, 1, 0, 0}, {0, 0, -1, 0}, {0, 0, 0, -1}, {-1, 0, 0, 0}};
        graph.setGraphForTEST(g);

        double[][] result = graph.floydWarshall();
        boolean neg_cycle = graph.getHasNoNegativeCycle();
        Assertions.assertTrue(neg_cycle);
    }


    @Test
    void Test4() {
        Graph graph = new Graph();
        double[][] g = {
                {0, 3, 8, 0, 5},
                {0, 0, 0, 1, 7},
                {0, 4, 0, -5, 0},
                {2, 0, 0, 0, 0},
                {0, 0, 0, 6, 0}};
        graph.setGraphForTEST(g);

        double[][] result = graph.floydWarshall();
        boolean neg_cycle = graph.getHasNoNegativeCycle();
        Assertions.assertFalse(neg_cycle);
    }
}
