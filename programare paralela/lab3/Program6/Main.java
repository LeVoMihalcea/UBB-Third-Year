package Program6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // For simplicity, we will work solely on square matrices
        int size = 5;
        int tasks = 3;
        int threads = 3;

        List<List<Integer>> firstMatrix = MatrixHelper.generateRandomMatrix(size);
        List<List<Integer>> secondMatrix = MatrixHelper.generateRandomMatrix(size);

        List<List<Integer>> resultMatrix = MatrixHelper.generateEmptyMatrix(size);

        // Thread pool
        List<Runnable> taskList = new ArrayList<>();

        ExecutorService pool = Executors.newFixedThreadPool(threads);

        long start = System.currentTimeMillis();

        for(int i = 0; i < tasks; i++){

            int finalI = i;
            Runnable r = new Runnable() {
                @Override
                public void run()
                {
                    MatrixHelper.computeMultiplicationResultElements(finalI, tasks, firstMatrix, secondMatrix, resultMatrix);
                }
            };

            taskList.add(r);
        }

        for (Runnable task: taskList){
            pool.execute(task);
        }

        awaitTerminationAfterShutdown(pool);

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        System.out.println(firstMatrix.toString());
        System.out.println(secondMatrix.toString());
        System.out.println(resultMatrix.toString());
        System.out.println("Time Elapsed:" + timeElapsed);
    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
