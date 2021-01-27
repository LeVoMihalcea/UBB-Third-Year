package operations;

import domain.MultiplicationTask;
import domain.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static helpers.Util.*;

public class Operations {
    public static Polynomial multiplicationSequentialForm(Polynomial p1, Polynomial p2) {
        int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < sizeOfResultCoefficientList; i++) {
            coefficients.add(0);
        }
        for (int i = 0; i < p1.getCoefficients().size(); i++) {
            for (int j = 0; j < p2.getCoefficients().size(); j++) {
                int index = i + j;
                int value = p1.getCoefficients().get(i) * p2.getCoefficients().get(j);
                coefficients.set(index, coefficients.get(index) + value);
            }
        }
        return new Polynomial(coefficients);
    }

    public static Polynomial multiplicationParallelizedForm(Polynomial p1, Polynomial p2, int nrOfThreads) throws
            InterruptedException {
        //initialize result polynomial with coefficients = 0
        int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
        List<Integer> coefficients = IntStream.of(new int[sizeOfResultCoefficientList]).boxed().collect(Collectors
                .toList());
        Polynomial result = new Polynomial(coefficients);

        //calculate the coefficients

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nrOfThreads);
        int step = result.getLength() / nrOfThreads;
        if (step == 0) {
            step = 1;
        }
        int end;
        for (int i = 0; i < result.getLength(); i += step) {
            end = i + step;
            MultiplicationTask task = new MultiplicationTask(i, end, p1, p2, result);
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(50, TimeUnit.SECONDS);

        return result;
    }

    public static Polynomial multiplicationKaratsubaSequentialForm(Polynomial p1, Polynomial p2) {
        if (p1.getDegree() < 2 || p2.getDegree() < 2) {
            return multiplicationSequentialForm(p1, p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getLength()));
        Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getLength()));

        Polynomial z1 = multiplicationKaratsubaSequentialForm(lowP1, lowP2);
        Polynomial z2 = multiplicationKaratsubaSequentialForm(add(lowP1, highP1), add(lowP2, highP2));
        Polynomial z3 = multiplicationKaratsubaSequentialForm(highP1, highP2);

        //calculate the final result
        Polynomial r1 = shift(z3, 2 * len);
        Polynomial r2 = shift(subtract(subtract(z2, z3), z1), len);
        return add(add(r1, r2), z1);
    }

    public static Polynomial multiplicationKaratsubaParallelizedForm(Polynomial p1, Polynomial p2, int currentDepth)
            throws ExecutionException, InterruptedException {
        //E impartit deja de 4 ori si pentru ca e recursiv, nu mai împarțim in mai mult pt ca nu încape pe stack-ul
        // intern
        if (currentDepth > 4) {
            return multiplicationKaratsubaSequentialForm(p1, p2);
        }

        if (p1.getDegree() < 2 || p2.getDegree() < 2) {
            return multiplicationKaratsubaSequentialForm(p1, p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getLength()));
        Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getLength()));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Callable<Polynomial> task1 = () -> multiplicationKaratsubaParallelizedForm(lowP1, lowP2, currentDepth + 1);
        Callable<Polynomial> task2 = () -> multiplicationKaratsubaParallelizedForm(add(lowP1, highP1), add(lowP2, highP2), currentDepth + 1);
        Callable<Polynomial> task3 = () -> multiplicationKaratsubaParallelizedForm(highP1, highP2, currentDepth);

        Future<Polynomial> f1 = executor.submit(task1);
        Future<Polynomial> f2 = executor.submit(task2);
        Future<Polynomial> f3 = executor.submit(task3);

        executor.shutdown();

        Polynomial z1 = f1.get();
        Polynomial z2 = f2.get();
        Polynomial z3 = f3.get();

        executor.awaitTermination(60, TimeUnit.SECONDS);

        //calculate the final result
        Polynomial r1 = shift(z3, 2 * len);
        Polynomial r2 = shift(subtract(subtract(z2, z3), z1), len);
        Polynomial result = add(add(r1, r2), z1);
        return result;
    }
}
