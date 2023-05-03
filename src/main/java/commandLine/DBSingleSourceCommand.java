package commandLine;

import org.example.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DBSingleSourceCommand implements Command {
    Graph graph;
    int src, sz, algo;
    double[] costs;
    int[] parents;
    Scanner sc;
    boolean neg;

    public DBSingleSourceCommand (Graph graph, int src, int algo) {
        this.graph = graph;
        this.src = src;
        this.sz = graph.size();
        this.costs = new double[sz];
        this.parents = new int[sz];
        for (int i = 0; i < sz; i++) {
            parents[i] = i;
        }
        sc = new Scanner(System.in);
        this.algo = algo;
        neg = false;
    }

    private void printChoices () {
        System.out.println("1- Get the cost");
        System.out.println("2- Get the path");
        System.out.println("3- Back");
    }

    private int getChoice () {
        int c;
        while (true) {
            c = Integer.parseInt(sc.nextLine());
            if (c > 3 || c < 1) {
                System.out.println("Enter a valid choice:");
            } else break;
        }
        CLI.clearScreen();
        return c;
    }

    @Override
    public void execute() {

        if (algo == 0) {
            long st = System.nanoTime();
            graph.dijkstra(src, costs, parents);
            long end = System.nanoTime();
            System.out.println("It takes: " + ((end-st)/1000) + " micro seconds");
        } else {
            Arrays.fill(costs, Double.POSITIVE_INFINITY);
            long st = System.nanoTime();
            neg = !graph.bellmanFord(src, costs, parents);
            long end = System.nanoTime();
            System.out.println("It takes: " + ((end-st)/1000) + " micro seconds");
            if (neg) {
                System.out.println("NOTE: The graph has negative cycle!!");
            }
        }
        while (true) {
            System.out.println("Enter a destination node (or -1 to back):");
            int dest = getDest();
            if (dest == -1) {
                return;
            }
            while (true) {
                printChoices();
                int c = getChoice();
                if (c == 1) {
                    printCost(src, dest);
                } else if (c == 2) {
                    printShortestPath(src, dest);
                } else if (c == 3) {
                    break;
                }
            }
        }
    }

    private int getDest () {
        int x;
        while (true) {
            x = Integer.parseInt(sc.nextLine());
            if ((x < 0 || x >= sz) && x != -1) {
                System.out.println("Enter a valid node:");
            } else break;
        }
        CLI.clearScreen();
        return x;
    }

    private void printCost (int src, int dest) {
        System.out.println("The cost of the shortest path from node " + src + " to node " + dest + " is " + costs[dest]);
    }

    private void printShortestPath (int src, int dest) {
        if (neg) {
            System.out.println("No optimal solution!!");
            return;
        }
        List<Integer> path = graph.getShortestPath(src, dest, parents);
        System.out.print("The shortest path: ");
        int s = path.size();
        for (int i = 0; i < s-1; i++) {
            System.out.print(path.get(i) + " -> ");
        }
        System.out.println(path.get(s-1));
    }
}
