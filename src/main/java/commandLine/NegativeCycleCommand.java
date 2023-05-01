package commandLine;

import org.example.Graph;

import java.util.Arrays;
import java.util.Scanner;

public class NegativeCycleCommand implements Command {

    Graph graph;
    Scanner sc;
    double[] costs;
    int[] parents;
    double[][] costsFloyd;
    int[][] predecessors;
    int sz;

    public NegativeCycleCommand (Graph graph) {
        this.graph = graph;
        sz = graph.size();
        costs = new double[sz];
        parents = new int[sz];
        for (int i = 0; i < sz; i++) {
            parents[i] = i;
        }
        costsFloyd = graph.getGraphM().clone();
        predecessors = new int[sz][sz];
        Arrays.fill (costs, Double.POSITIVE_INFINITY);
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (costsFloyd[i][j] == Double.POSITIVE_INFINITY) {
                    predecessors[i][j] = -1;
                } else {
                    predecessors[i][j] = j;
                }
            }
        }
        Arrays.fill (costs, Double.POSITIVE_INFINITY);
        sc = new Scanner(System.in);
    }

    private void printAlgorithmsList () {
        System.out.println("1- Bellman-Ford algorithm");
        System.out.println("2- Floyd-Warshall algorithm");
        System.out.println("3- Back to main menu");
        System.out.println("Enter your choice:");
    }

    private int getChoice () {
        int x;
        while (true) {
            x = Integer.parseInt(sc.nextLine ());
            if (x < 1 || x > 3) {
                System.out.println("Enter a valid choice:");
            } else break;
        }
        CLI.clearScreen();
        return x;
    }

    @Override
    public void execute() {
        while (true) {
            printAlgorithmsList();
            int c = getChoice();
            if (c == 3) {
                return;
            } else if (c == 1) {
                boolean neg = graph.bellmanFord(0, costs, parents);
                if (!neg) {
                    System.out.println("The graph has negative cycle");
                } else {
                    System.out.println("The graph has no negative cycle");
                }
            } else if (c == 2) {
                boolean neg = graph.floydWarshall(costsFloyd, predecessors);
                if (!neg) {
                    System.out.println("The graph has negative cycle");
                } else {
                    System.out.println("The graph has no negative cycle");
                }
            }
        }
    }
}
