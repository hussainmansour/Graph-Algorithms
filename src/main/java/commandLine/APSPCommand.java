package commandLine;

import org.example.Graph;

import java.util.List;
import java.util.Scanner;

public class APSPCommand implements Command {

    Graph graph;
    Scanner sc;
    int sz, src, dest;
    double[][] result;
    int[][] parents;
    int[][] predecessors;
    boolean algo, neg;

    public APSPCommand (Graph graph) {
        this.graph = graph;
        sc = new Scanner(System.in);
        sz = graph.size();
        parents = new int[sz][sz];
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                parents[i][j] = j;
            }
        }
        predecessors = new int[sz][sz];
        neg = false;
    }

    private void setPredecessors () {
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (result[i][j] == Double.POSITIVE_INFINITY) {
                    predecessors[i][j] = -1;
                } else {
                    predecessors[i][j] = j;
                }
            }
        }
    }

    private void setResultForFloyd () {
        result = graph.getGraphM().clone();
    }

    private void setResult () {
        result = new double[sz][sz];
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                result[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    private void printAlgorithmsList () {
        System.out.println("1- Dijkstra algorithm");
        System.out.println("2- Bellman-Ford algorithm");
        System.out.println("3- Floyd-Warshall algorithm");
        System.out.println("4- Back to main menu");
        System.out.println("Enter your choice:");
    }

    private int getAlgorithm () {
        int x;
        while (true) {
            x = Integer.parseInt(sc.nextLine());
            if (x > 4 || x < 1) {
                System.out.println("Enter a valid choice:");
            } else break;
        }
        return x;
    }

    private void runDijkstra () {
        setResult();
        for (int i = 0; i < sz; i++) {
            graph.dijkstra(i, result[i], parents[i]);
        }
    }

    private void runBellmanFord () {
        setResult();
        for (int i = 0; i < sz; i++) {
            neg = !graph.bellmanFord(i, result[i], parents[i]);
        }
        if (neg) {
            System.out.println("NOTE: The graph has negative cycle!!");
        }
    }

    private void runFloydWarshall () {
        setResultForFloyd();
        setPredecessors();
        neg = !graph.floydWarshall(result, predecessors);
        if (neg) {
            System.out.println("NOTE: The graph has negative cycle!!");
        }
    }

    private int getChoice () {
        int x;
        System.out.println("1- Get the cost");
        System.out.println("2- Get the path");
        System.out.println("3- Back");
        while (true) {
            x = Integer.parseInt(sc.nextLine());
            if (x < 1 || x > 3) {
                System.out.println("Enter a valid choice:");
            } else break;
        }
        return x;
    }

    private void getPoint () {
        System.out.println("Enter the source node (or -1 to back):");
        while (true) {
            src = Integer.parseInt(sc.nextLine());
            if ((src < 0 || src >= sz) && src != -1) {
                System.out.println("Enter a valid node:");
            } else break;
        }
        if (src == -1) {
            return;
        }
        System.out.println("Enter the destination node (or -1 to back):");
        while (true) {
            dest = Integer.parseInt(sc.nextLine());
            if ((dest < 0 || dest >= sz) && dest != -1) {
                System.out.println("Enter a valid node:");
            } else break;
        }
    }

    @Override
    public void execute() {
        while (true) {
            printAlgorithmsList ();
            int c = getAlgorithm ();
            if (c == 1) {
                algo = false;
                runDijkstra ();
            } else if (c == 2) {
                algo = false;
                runBellmanFord ();
            } else if (c == 3) {
                algo = true;
                runFloydWarshall ();
            } else if (c == 4) {
                break;
            }
            getPoint ();
            if (src == -1 || dest == -1) {
                break;
            }
            while (true) {
                c = getChoice();
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
        System.out.println("The cost of the shortest path from node " + src + " to node " + dest + " is " + result[src][dest]);
    }

    private void printShortestPath (int src, int dest) {
        List<Integer> path;
        if (algo) {
            path = graph.getShortestPath(src, dest, predecessors);
        } else {
            if (neg) {
                System.out.println("No optimal solution!!");
                return;
            }
            path = graph.getShortestPath(src, dest, parents[src]);
        }
        System.out.print("The shortest path: ");
        int s = path.size();
        for (int i = 0; i < s-1; i++) {
            System.out.print(path.get(i) + " -> ");
        }
        System.out.println(path.get(s-1));
    }
}
