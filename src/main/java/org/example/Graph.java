package org.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph implements IGraph {
    private double[][] graphM; // adjacency matrix for floyd-warshall
    private int V, E;
    private ArrayList<ArrayList<Pair>> graphL; // adjacency list for dijkstra
    private ArrayList<Triple> edges; // edge list for bellman-ford


    private void setGraphM() {
        for (int i = 0; i < V; i++) {
            graphM[i][i] = 0;
            for (int j = 0; j < V; j++) {
                if (i != j) {
                    graphM[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    private void setGraphL() {
        for (int i = 0; i < V; i++) {
            graphL.add(new ArrayList<>());
        }
    }

    public void setGraphForTEST(double[][] graph) {
        this.graphM = graph;
    }

    private void readGraph(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            if (line == null) throw new IOException();
            String[] VE = line.split(" ");
            V = Integer.parseInt(VE[0]);
            E = Integer.parseInt(VE[1]);
            graphM = new double[V][V];
            graphL = new ArrayList<>();
            edges = new ArrayList<>();
            setGraphM();
            setGraphL();
            for (int i = 0; i < E; i++) {
                line = br.readLine();
                String[] edge = line.split(" ");
                int src = Integer.parseInt(edge[0]);
                int dist = Integer.parseInt(edge[1]);
                double w = Double.parseDouble(edge[2]);
                graphM[src][dist] = w;
                graphL.get(src).add(new Pair(dist, w));
                edges.add(new Triple(src, dist, w));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Graph(String path) {
        readGraph(path);
    }

    public Graph() {
    }


    public void printGraph() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(graphM[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int size() {
        return this.V;
    }

    @Override
    public void dijkstra(int source, double[] costs, int[] parents) {

        boolean[] visited = new boolean[V];
        PriorityQueue<Pair> pq = new PriorityQueue<>(V);
        Arrays.fill(costs, Double.POSITIVE_INFINITY);
        costs[source] = 0;
        parents[source] = -1;
        visited[source] = true;
        pq.offer(new Pair(source, 0));
        while (!pq.isEmpty()) {
            Pair node = pq.poll();
            int u = node.dist;
            visited[u] = true;
            if (costs[u] < node.weight) {
                continue;
            }
            for (Pair neighbor : graphL.get(u)) {
                int v = neighbor.dist;
                double w = neighbor.weight;

                if (costs[u] != Double.POSITIVE_INFINITY && costs[v] > costs[u] + w) {
                    costs[v] = costs[u] + w;
                    parents[v] = u;
                    pq.offer(new Pair(v, w));
                }
            }
        }
    }


    public boolean bellmanFord(int source, double[] costs, int[] parents) {

        costs[source] = 0.0;

        for (int i = 1; i <= V - 1; i++) {

            boolean changed = false;

            for (int j = 0; j < E; j++) {
                int u = edges.get(j).src;
                int v = edges.get(j).dist;
                double weight = edges.get(j).weight;

                if (costs[u] != Double.POSITIVE_INFINITY &&
                        costs[u] + weight < costs[v]) {
                    costs[v] = costs[u] + weight;
                    parents[v] = u;
                    changed = true;
                }
            }

            if (!changed) break;
        }


        for (int j = 0; j < E; j++) {
            int u = edges.get(j).src;
            int v = edges.get(j).dist;
            double weight = edges.get(j).weight;

            if (costs[u] != Double.POSITIVE_INFINITY &&
                    costs[u] + weight < costs[v]) {
                return false;
            }
        }

        return true;
    }

    public List<Integer> getShortestPath(int source, int destination, int[] parents) {
        ArrayList<Integer> shortestPath = new ArrayList<>();
        int current = destination;

        while (current != source) {
            shortestPath.add(current);
            current = parents[current];
        }

        shortestPath.add(source);
        Collections.reverse(shortestPath);
        return shortestPath;
    }


    @Override
    public boolean floydWarshall(double[][] costs, int[][] predecessors) {
        int n = costs.length;

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (costs[i][k] == Double.POSITIVE_INFINITY
                            || costs[k][j] == Double.POSITIVE_INFINITY) {
                        continue;
                    }
                    double newCost = costs[i][k] + costs[k][j];
                    if (newCost < costs[i][j]) {
                        costs[i][j] = newCost;
                        predecessors[i][j] = k;
                    }
                }
            }
        }

        // Check for negative cycles
        for (int i = 0; i < n; i++) {
            if (costs[i][i] < 0) {
                return false;
            }
        }

        return true;
    }

    public List<Integer> getShortestPath(int source, int destination, int[][] predecessors) {

        List<Integer> path;
        if (predecessors[source][destination] == -1) {
            // If there is no predecessor, the path is just the edge (source, destination)
            path = new ArrayList<>();
            path.add(source);
            path.add(destination);
        } else {
            // Recursively get the path from source to the predecessor, and from the predecessor to destination
            path = getShortestPath(source, predecessors[source][destination], predecessors);
            path.remove(path.size() - 1); // Remove the duplicate predecessor
            path.addAll(getShortestPath(predecessors[source][destination], destination, predecessors));
        }
        return path;
    }

}
