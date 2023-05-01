package commandLine;

import org.example.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SingleSourceFloydWarshallCommand implements Command {

    Graph graph;
    int src, sz;
    double[][] costs;
    int[][] predecessors;
    Scanner sc;

    public SingleSourceFloydWarshallCommand (Graph graph, int src) {
        this.graph = graph;
        this.src = src;
        sz = graph.size();
        sc = new Scanner(System.in);
        costs = graph.getGraphM().clone();
        predecessors = new int[sz][sz];
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (costs[i][j] == Double.POSITIVE_INFINITY) {
                    predecessors[i][j] = -1;
                } else {
                    predecessors[i][j] = j;
                }
            }
        }
    }

    @Override
    public void execute() {
        long st = System.nanoTime();
        boolean neg = graph.floydWarshall(costs, predecessors);
        long end = System.nanoTime();
        System.out.println("It takes: " + ((end-st)/1000) + " micro seconds");
        if (!neg) {
            System.out.println("NOTE: The graph has negative cycle!!");
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

    private void printCost (int src, int dest) {
        System.out.println("The cost of the shortest path from node " + src + " to node " + dest + " is " + costs[src][dest]);
    }

    private void printShortestPath (int src, int dest) {
        List<Integer> path = graph.getShortestPath(src, dest, predecessors);
        if (path.isEmpty()) {
            System.out.println("No path exists!");
            return;
        }
        System.out.print("The shortest path: ");
        int s = path.size();
        for (int i = 0; i < s-1; i++) {
            System.out.print(path.get(i) + " -> ");
        }
        System.out.println(path.get(s-1));
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
}
