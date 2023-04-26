package org.example;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph implements IGraph{
    private double[][] graphM; // adjacency matrix for floyd-warshall
    private int V, E;
    private ArrayList<ArrayList<Pair>> graphL; // adjacency list for dijkstra
    private ArrayList<Triple> edges; // edge list for bellman-ford

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

    public int size () {
        return this.V;
    }

    @Override
    public double[] dijkstra(int source) {
        return new double[0];
    }

    @Override
    public double[] bellmanFord(int source) {

        List<Double> distance = new ArrayList<>(Collections.nCopies(V,Double.POSITIVE_INFINITY));
        distance.set(source,0.0);

        List<Integer> parent = new ArrayList<>(Collections.nCopies(V,-1));

        for (int i = 1; i <= V - 1; i++) {

            boolean changed = false;

            for (int j = 0; j < E; j++) {
                int u = edges.get(j).src;
                int v = edges.get(j).dist;
                double weight = edges.get(j).weight;

                if ( distance.get(u) != Double.POSITIVE_INFINITY &&
                        distance.get(u) + weight < distance.get(v)){
                    distance.set(v,distance.get(u) + weight);
                    parent.set(v,u);
                    changed = true;
                }
            }

            if (!changed) break;
        }

        for (int j = 0; j < E; j++) {

            int u = edges.get(j).src;
            int v = edges.get(j).dist;
            double weight = edges.get(j).weight;

            if ( distance.get(u) != Double.POSITIVE_INFINITY &&
                    distance.get(u) + weight < distance.get(v) ){
                return null;
            }
        }

        Double[] distanceArray = new Double[distance.size()];
        distanceArray = distance.toArray(distanceArray);

        return ArrayUtils.toPrimitive(distanceArray);
    }

    @Override
    public double[][] floydWarshall() {
        return new double[0][];
    }
}
