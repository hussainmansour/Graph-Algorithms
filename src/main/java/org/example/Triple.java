package org.example;

public class Triple extends Pair{
    int src;

    public Triple(int src, int dist, double weight) {
        super(dist, weight);
        this.src = src;
    }
}
