package commandLine;

import org.example.Graph;
import java.util.Scanner;
import static java.lang.System.in;
import static java.lang.System.out;

public class CLI {
    Graph graph;
    Scanner sc;

    public CLI () {
        sc = new Scanner(in);
    }

    private void setGraph () {
        System.out.println("Enter the path:");
        while (true) {
            graph = new Graph(sc.nextLine());
            if (graph == null) {
                System.out.println("Enter a valid path");
            } else break;
        }
    }

    private Command getCommand (String choice) {
        switch (choice) {
            case "1" -> {
                return new SSSPCommand (this.graph);
            }
            case "2" -> {
                return new APSPCommand (this.graph);
            }
            case "3" -> {
                return new NegativeCycleCommand (this.graph);
            }
            case "4" -> {
                return new ExitCommand ();
            }
            default -> {
                return null;
            }
        }
    }

    private void printCommandList () {
        out.println("1- Single source shortest path");
        out.println("2- All pairs shortest path");
        out.println("3- Check negative cycle");
        out.println("4- Exit");
        out.println("Enter yout choice:");
    }

    public void start () {
        setGraph ();
        while (true) {
            printCommandList();
            Command command;
            while (true) {
                command = getCommand(sc.nextLine());
                if (command == null) {
                    out.println("Enter a valid choice:");
                } else break;
            }
            command.execute();
        }
    }

    public static void clearScreen () {
        for (int i = 0; i < 5; i++) {
            out.println();
        }
    }
}

