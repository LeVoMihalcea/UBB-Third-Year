package Program5;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // For simplicity, we will work solely on square matrices
        int size = 5;
        int tasks = 3;

        List<List<Integer>> firstMatrix = MatrixHelper.generateRandomMatrix(size);
        List<List<Integer>> secondMatrix = MatrixHelper.generateRandomMatrix(size);

        List<List<Integer>> resultMatrix = MatrixHelper.generateEmptyMatrix(size);

        // Individual thread start
        List<Thread> threads = new ArrayList<>();

        long start = System.currentTimeMillis();

        for(int i = 0; i < tasks; i++){
            int startIndex = i;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run()
                {
                    MatrixHelper.computeMultiplicationResultElements(startIndex, tasks, firstMatrix, secondMatrix, resultMatrix);
                }
            });

            threads.add(t);
            t.start();
        }

        for(Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        System.out.println(firstMatrix.toString());
        System.out.println(secondMatrix.toString());
        System.out.println(resultMatrix.toString());
        System.out.println("Time Elapsed:" + timeElapsed);
    }
}
