package org.example;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph implements IGraph{
    private double[][] graphM; // adjacency matrix for floyd-warshall
    private int V, E;
    private ArrayList<ArrayList<Pair>> graphL; // adjacency list for dijkstra
    private ArrayList<Triple> edges; // edge list for bellman-ford
    public boolean neg_cycle_floyed = false;
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

    public void setGraphForTEST(double[][] graph){
        this.graphM = graph;
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
    public Graph () {

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

        PriorityQueue<Pair> pq = new PriorityQueue<>(V);
        double[] dist = new double[V];
        int[] parent = new int[V];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0;
        parent[source] = -1;
        pq.offer(new Pair(source, 0));
        while (!pq.isEmpty()) {
            Pair node = pq.poll();
            int u = node.dist;
            if (dist[u] < node.weight) {
                continue;
            }
            for (Pair neighbor: graphL.get(u)) {
                int v = neighbor.dist;
                double w = neighbor.weight;

                if(dist[u]!=Double.POSITIVE_INFINITY && dist[v] > dist[u] + w){
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.offer(new Pair(v, w));
                }
            }
        }
        return dist;
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
        double[][] tempGraph = this.graphM;
        int n = tempGraph.length;
        for(int i = 0 ; i < n ;i++){
            for(int j = 0; j< n;j++){
                if(i == j ){
                    tempGraph[i][j] = 0 ;
                }else if(tempGraph[i][j] == 0){
                    tempGraph[i][j] = 1e9;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tempGraph[i][k] == 1e9 || tempGraph[k][j] == 1e9)
                        continue;
                    tempGraph[i][j] = Math.min(tempGraph[i][j], tempGraph[i][k] + tempGraph[k][j]);
                }
            }
        }

        for(int i = 0; i <n ;i++){
            for(int j = 0; j < n ;j++){
                if(i == j && tempGraph[i][j] < 0){
                    neg_cycle_floyed = true;
                    return  tempGraph;
                }
            }
        }

        return tempGraph;
    }
}
