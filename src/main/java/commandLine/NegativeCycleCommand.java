package commandLine;

import org.example.Graph;

public class NegativeCycleCommand implements Command {

    Graph graph;

    public NegativeCycleCommand (Graph graph) {
        this.graph = graph;
    }

    @Override
    public void execute() {

    }
}
