package org.example;

/**
 * This interface represents a graph data structure and provides methods for various graph algorithms.
 */
public interface IGraph {

    /**
     * Finds the shortest path from a source node to all other nodes in the graph using Dijkstra's algorithm.
     *
     * @param source the source node
     * @return an array of distances from the source node to all other nodes in the graph
     */
    int[] dijkstra(int source);

    /**
     * Finds the shortest path from a source node to all other nodes in the graph using the Bellman-Ford algorithm.
     *
     * @param source the source node
     * @return an array of distances from the source node to all other nodes in the graph, or null if a negative cycle exists
     */
    int[] bellmanFord(int source);

    /**
     * Finds the shortest path between two nodes in the graph using the Floyd-Warshall algorithm.
     *
     * @return a 2D array of shortest path distances between all pairs of nodes in the graph
     */
    int[][] floydWarshall();
}
