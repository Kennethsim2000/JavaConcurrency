package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class PerformanceTest {
    private static final int THREADS = 4;
    private static final int ITERATIONS = 5_000_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Warming up JVM...");
        for (int i = 0; i < 3; i++) {
            runAtomicTest(false);
            runSynchronizedTest(false);
        }

        System.out.println("=== Measuring Performance ===");
        long atomicTime = runAtomicTest(true);
        long syncTime   = runSynchronizedTest(true);

        System.out.printf("AtomicInteger time: %d ms%n", atomicTime);
        System.out.printf("synchronized time: %d ms%n", syncTime);
    }

    private static long runAtomicTest(boolean report) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);

        Thread[] threads = new Thread[THREADS];
        for (int t = 0; t < THREADS; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < ITERATIONS; i++) {
                    counter.incrementAndGet();
                }
            });
        }

        long start = System.currentTimeMillis();
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        long end = System.currentTimeMillis();

        if (report) {
            System.out.printf("Atomic final result: %d%n", counter.get());
        }
        return end - start;
    }

    private static long runSynchronizedTest(boolean report) throws InterruptedException {
        MyIntegerTwo counter = new MyIntegerTwo();

        Thread[] threads = new Thread[THREADS];
        for (int t = 0; t < THREADS; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < ITERATIONS; i++) {
                    counter.increment();
                }
            });
        }

        long start = System.currentTimeMillis();
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        long end = System.currentTimeMillis();

        if (report) {
            System.out.printf("Synchronized final result: %d%n", counter.get());
        }
        return end - start;
    }
}
