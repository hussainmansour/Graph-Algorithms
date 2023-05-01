package commandLine;

import org.example.Graph;

public class APSPCommand implements Command {

    Graph graph;

    public APSPCommand (Graph graph) {
        this.graph = graph;
    }

    @Override
    public void execute() {

    }
}
