package com.example.stream;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamGatherer {

    // Fixed-size windows of size n
    public static void fixedSizeWindow(Stream<Integer> stream, int n) {
        stream.gather(Gatherers.windowFixed(n)).forEach(window -> System.out.println(window));
    }

    // Sliding windows of size n
    public static void slidingWindow(Stream<Integer> stream, int n) {
        stream.gather(Gatherers.windowSliding(n)).forEach(window -> System.out.println(window));
    }

    // Fold operation with StringBuilder
    public static void foldString(Stream<String> stream) {
        stream.gather(Gatherers.fold(StringBuilder::new,StringBuilder::append)).forEach(System.out::println);
    }

    // Cumulative sum using scan
    public static void cumSum(Stream<Integer> stream) {
        stream.gather(Gatherers.scan(() -> 0, (sum, next) -> sum + next)).forEach(System.out::println);
    }


    // Process numbers concurrently with n threads
    public static void processConcurrent(Stream<Integer> stream, int maxConcurrency) {
        long startTime = System.currentTimeMillis();
        stream.gather(Gatherers.mapConcurrent( maxConcurrency, n -> n * 2));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds");
    }

    public static void processConcurrentOld(Stream<Integer> stream, int threadCount) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        stream.map(n -> {
            Future<Integer> future = executorService.submit(() -> {
                return n * 2;
            });
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds");
    }

    // process fibonacci numbers
    public static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void processFibonacci(Stream<Integer> stream, int maxConcurrency) {
        long startTime = System.currentTimeMillis();
        stream.gather(Gatherers.mapConcurrent( maxConcurrency, n -> fibonacci(100 * n)));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds");
    }

    public static void processFibonacciOld(Stream<Integer> stream, int threadCount) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        stream.map(n -> {
            Future<Integer> future = executorService.submit(() -> {
                return fibonacci(100 * n);
            });
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds");
    }

    // process short tasks
    public static void processShortTasks(Stream<Integer> stream, int maxConcurrency) {
        long startTime = System.currentTimeMillis();
        stream.gather(Gatherers.mapConcurrent(maxConcurrency, n -> {
            try {
                Thread.sleep(150); // Simulating quick task that takes time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return n;
        }));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds");
    }

    public static void processShortTasksOld(Stream<Integer> stream, int maxConcurrency) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(maxConcurrency);
        stream.map(n -> {
            Future<Integer> future = executorService.submit(() -> {
                try {
                    Thread.sleep(150); // Simulating quick task that takes time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return n;
            });
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " milliseconds");
    }


    public static void main(String[] args) {
        Stream<Integer> numbers1 = Stream.iterate(1, n -> n + 1).limit(10);
        Stream<Integer> numbers2 = Stream.iterate(1, n -> n + 1).limit(5);
        Stream<Integer> numbers3 = Stream.iterate(1, n -> n + 1).limit(5);
        Stream<Integer> numbers4 = Stream.iterate(1, n -> n + 1).limit(1000);
        Stream<Integer> numbers5 = Stream.iterate(1, n -> n + 1).limit(1000);
        Stream<Integer> numbers6 = Stream.iterate(1, n -> n + 1).limit(1000);
        Stream<Integer> numbers7 = Stream.iterate(1, n -> n + 1).limit(1000);
        Stream<String> words = Stream.of("a", "b", "c", "d");

        /*
        System.out.println("\n1) Fixed-size windows of size 4");
        fixedSizeWindow(numbers1,4);

        System.out.println("\n2) Sliding windows of size 3");
        slidingWindow(numbers2,3);

        System.out.println("\n3) Fold operation with StringBuilder");
        foldString(words);

        System.out.println("\n4) Cumulative sum using scan");
        cumSum(numbers3);

        System.out.println("\n5) Process numbers concurrently with max concurrency 4");
        System.out.println("with gatherer");
        processConcurrent(numbers4,4);
        System.out.println("with executor service");
        processConcurrentOld(numbers5,4);

        System.out.println("\n6) Process fibonacci numbers concurrently with max concurrency 4");
        System.out.println("with gatherer");
        processFibonacci(numbers6,4);
        System.out.println("with executor service");
        processFibonacciOld(numbers7,4);

         */

        System.out.println("\n7) Process short tasks concurrently with max concurrency 4");
        System.out.println("with gatherer");
        processShortTasks(numbers6,4);
        System.out.println("with executor service");
        processShortTasksOld(numbers7,4);


    }
}



