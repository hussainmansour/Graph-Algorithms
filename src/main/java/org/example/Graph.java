package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph implements IGraph{
    private double[][] graphM;
    private int V, E;
    private ArrayList<ArrayList<Integer>> graphL;
    private ArrayList<Pair> edges = new ArrayList<>();

    private void setGraphM () {
        for (int i = 0; i < V; i++) {
            graphM[i][i] = 0;
            for (int j = 0; j < V; j++) {
                if (i != j) {
                    graphM[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    private void setGraphL () {
        for (int i = 0; i < V; i++) {
            graphL.add(new ArrayList<>());
        }
    }

    private void readGraph (String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            if (line == null) throw new IOException();
            String[] VE = line.split(" ");
            V = Integer.parseInt(VE[0]);
            E = Integer.parseInt(VE[1]);
            graphM = new double[V][V];
            setGraphM();
            setGraphL();
            for (int i = 0; i < E; i++) {
                line = br.readLine();
                String[] edge = line.split(" ");
                int src = Integer.parseInt(edge[0]);
                int dist = Integer.parseInt(edge[1]);
                double w = Double.parseDouble(edge[2]);
                graphM[src][dist] = w;
                graphL.get(src).add(dist);
                edges.add(new Pair(src, dist, w));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Graph (String path) {
        readGraph(path);
    }

    public void printGraph() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(graphM[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int[] dijkstra(int source) {
        return new int[0];
    }

    @Override
    public int[] bellmanFord(int source) {
        return new int[0];
    }

    @Override
    public int[][] floydWarshall() {
        return new int[0][];
    }
}
