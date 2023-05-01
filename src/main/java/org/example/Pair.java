package org.example;

public class Pair implements Comparable<Pair>{
    int dist;
    double weight;

    Pair (int dist, double weight) {
        this.dist = dist;
        this.weight = weight;
    }

    @Override
    public int compareTo(Pair o) {
        return Double.compare(weight, o.weight);
    }

}
