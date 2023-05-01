package commandLine;

import org.example.Graph;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class SSSPCommand implements Command{

    Graph graph;
    int src, sz;
    Scanner sc;

    public SSSPCommand (Graph graph) {
        this.graph = graph;
        this.sz = graph.size();
        sc = new Scanner(System.in);
    }

    private int getSrc () {
        int x;
        System.out.println("Choose the source node:");
        while (true) {
            x = Integer.parseInt(sc.nextLine());
            if (x >= sz || x < 0) {
                System.out.println("Enter a valid node:");
            } else break;
        }
        return x;
    }

    private void printAlgorithmsList () {
        System.out.println("1- Dijkstra algorithm");
        System.out.println("2- Bellman-Ford algorithm");
        System.out.println("3- Floyd-Warshall algorithm");
        System.out.println("4- Back to main menu");
        System.out.println("Enter your choice:");
    }

    private Command getCommand (String choice) {
        switch (choice) {
            case "1" -> {
                return new DBSingleSourceCommand (graph, src, 0);
            }
            case "2" -> {
                return new DBSingleSourceCommand (graph, src, 1);
            }
            case "3" -> {
                return new SingleSourceFloydWarshallCommand (graph, src);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void execute () {
        while (true) {
            this.src = getSrc();
            Command command;
            printAlgorithmsList();
            while (true) {
                String s = sc.nextLine();
                if ("4".equalsIgnoreCase(s)) {
                    return;
                }
                command = getCommand(s);
                if (command == null) {
                    System.out.println("Enter a valid choice:");
                } else break;
            }
            command.execute();
        }
    }
}
