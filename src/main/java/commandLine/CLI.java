package commandLine;

import org.example.Graph;
import java.util.Scanner;
import static java.lang.System.in;

public class CLI {
    Graph graph;
    Scanner sc;

    public CLI() {
        sc = new Scanner(in);
    }

    private void readPath () {
        while (true) {
            System.out.print("Enter the path: ");
            graph = new Graph(sc.nextLine());
            if (graph == null) {
                System.out.println("Enter a valid path");
            } else break;
        }
    }

    public void start () {
        readPath();
        startOperation ();
    }

    private void startOperation() {
        while (true) {
            firstMenu();
            String op = sc.nextLine();
            if ("1".equalsIgnoreCase(op)) {
                SSSP();
            } else if ("2".equalsIgnoreCase(op)) {
                APSP();
            } else if ("3".equalsIgnoreCase(op)) {
                negativeCycle();
            } else if ("4".equalsIgnoreCase(op)) {
                System.exit(0);
            } else {
                System.out.println("Enter a valid option");
            }
        }
    }

    private void SSSP() {

        int algorithm, src;
        double[] result;

        while (true) {
            System.out.print("Choose the source node: ");
            src = Integer.parseInt(sc.nextLine());
            if (!isValidNode(src)) {
                System.out.println("Enter a valid node");
            } else break;
        }

        while (true) {
            secondMenu(true);
            algorithm = Integer.parseInt(sc.nextLine());
            if (algorithm < 1 || algorithm > 3) {
                System.out.println("Enter a valid option");
            } else break;
        }

        switch (algorithm) {
            case 1 -> result = graph.dijkstra(src);
            case 2 -> result = graph.bellmanFord(src);
            case 3 -> result = graph.floydWarshall()[src];
            default -> result = new double[0];
        }

        while (true) {
            System.out.print("Choose a node (-1 to back to main menu): ");
            int dist = Integer.parseInt(sc.nextLine());
            if (dist == -1) {
                return;
            } else if (!isValidNode(dist)) {
                System.out.println("Enter a valid node");
            } else {
                System.out.println("The weight of the shortest path from node " + src + " to node " + dist + " = " + result[dist]);
            }
        }
    }

    private boolean isValidNode (int n) {
        return (n < graph.size() || n >= 0);
    }

    private void APSP() {
        int algorithm;
        double[][] result;

        while (true) {
            secondMenu(true);
            algorithm = Integer.parseInt(sc.nextLine());
            if (algorithm < 1 || algorithm > 3) {
                System.out.println("Enter a valid option");
            } else break;
        }

        switch (algorithm) {
            case 1 -> result = getAPSP(false);
            case 2 -> result = getAPSP(true);
            case 3 -> result = graph.floydWarshall();
            default -> result = new double[0][];
        }

        while (true) {
            System.out.print("Choose a pair (-1 to back to main menu): ");
            int src, dist;
            String[] SD = sc.nextLine().split(" ");
            src = Integer.parseInt(SD[0]);
            dist = Integer.parseInt(SD[1]);
            if (dist == -1) {
                return;
            } else if (!isValidNode(src) || !isValidNode(dist)) {
                System.out.println("Enter a valid node");
            } else {
                System.out.println("The weight of the shortest path from node " + src + " to node " + dist + " = " + result[src][dist]);
            }
        }
    }

    private void negativeCycle() {

    }

    public static void clearScreen () {
        for (int i = 0; i < 100; i++) System.out.println();
    }

    private void firstMenu () {
        System.out.println("1- SSSP");
        System.out.println("2- APSP");
        System.out.println("3- Check negative cycles");
        System.out.println("4- Exit");
    }

    private void secondMenu (boolean x) {
        System.out.println("choose an algorithm to run:");
        if (x) {
            System.out.println("1- Dijkstra algorithm");
            System.out.println("2- Bellman-Ford algorithm");
            System.out.println("3- Floyd-Warshall algorithm");
        } else {
            System.out.println("1- Bellman-Ford algorithm");
            System.out.println("2- Floyd-Warshall algorithm");
        }
    }

    private double[][] getAPSP (boolean x) {
        double[][] apsp = new double[graph.size()][graph.size()];
        if (x) {
            for (int i = 0; i < graph.size(); i++) {
                apsp[i] = graph.bellmanFord(i);
            }
        } else {
            for (int i = 0; i < graph.size(); i++) {
                apsp[i] = graph.dijkstra(i);
            }
        }
        return apsp;
    }
}

