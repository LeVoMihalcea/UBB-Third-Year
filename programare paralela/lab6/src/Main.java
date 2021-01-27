import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int GRAPHS_COUNT = 101;
        int THREAD_COUNT = 4;

        List<DirectedGraph> graphs = new ArrayList<>();

        for (int i = 1; i <= GRAPHS_COUNT; i++) {
            graphs.add(generateRandomGraph(i * 10));
        }

        System.out.println("Sequential - only 1 thread");
        search(graphs, 1);

        System.out.println("Parallel");
        search(graphs, THREAD_COUNT);

    }

    private static void search(List<DirectedGraph> graphs, int threadCount) throws InterruptedException {
        for (int i = 0; i < graphs.size(); i++) {
            mainDriver(i, graphs.get(i), threadCount);
        }
    }

    private static void mainDriver(int level, DirectedGraph graph, int threadCount) throws InterruptedException {
        long startTime = System.nanoTime();
        findHamiltonian(graph, threadCount);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        if (level % 50 == 0)
            System.out.println("Level " + level + ": " + duration + " ms");
    }

    private static void findHamiltonian(DirectedGraph graph, int threadCount) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        Lock lock = new ReentrantLock();
        List<Integer> result = new ArrayList<>(graph.size());

        for (int i = 0; i < graph.size(); i++){
            pool.execute(new Task(graph, i, result, lock));
        }

        pool.shutdown();

        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static DirectedGraph generateRandomGraph(int size) {
        DirectedGraph graph = new DirectedGraph(size);

        List<Integer> nodes = graph.getNodes();

        java.util.Collections.shuffle(nodes);

        for (int i = 1; i < nodes.size(); i++){
            graph.addEdge(nodes.get(i - 1),  nodes.get(i));
        }

        graph.addEdge(nodes.get(nodes.size() -1), nodes.get(0));

        Random random = new Random();

        for (int i = 0; i < size / 2; i++){
            int nodeA = random.nextInt(size - 1);
            int nodeB = random.nextInt(size - 1);

            graph.addEdge(nodeA, nodeB);
        }

        return graph;
    }

}