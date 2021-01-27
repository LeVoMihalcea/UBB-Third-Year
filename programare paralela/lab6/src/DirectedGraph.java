import java.util.ArrayList;
import java.util.List;

class DirectedGraph {
    private final List<List<Integer>> neighbours;
    private final List<Integer> nodes;

    DirectedGraph(int nodeCount) {
        this.neighbours = new ArrayList<>(nodeCount);
        this.nodes = new ArrayList<>();

        for (int i = 0; i < nodeCount; i++) {
            this.neighbours.add(new ArrayList<>());
            this.nodes.add(i);
        }
    }

    void addEdge(int nodeA, int nodeB) {
        this.neighbours.get(nodeA).add(nodeB);
    }

    List<Integer> neighboursOf(int node) {
        return this.neighbours.get(node);
    }

    List<Integer> getNodes(){
        return nodes;
    }

    int size() {
        return this.neighbours.size();
    }

}