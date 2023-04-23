package org.example;

public class PairOfThree extends Pair{
    int src;

    PairOfThree (int src, int dist, double weight) {
        super(dist, weight);
        this.src = src;
    }
}
