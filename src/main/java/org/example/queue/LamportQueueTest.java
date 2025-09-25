package org.example.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LamportQueueTest {
    private static final int ITERATIONS = 10_000_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Testing Lamport Queue ===");
        long lamportTime = runLamportTest();
        System.out.printf("LamportQueue time: %d ms%n", lamportTime);

        System.out.println("\n=== Testing ArrayBlockingQueue ===");
        long blockingTime = runBlockingQueueTest();
        System.out.printf("ArrayBlockingQueue time: %d ms%n", blockingTime);
    }

    private static long runLamportTest() throws InterruptedException {
        LamportQueue<Integer> queue = new LamportQueue<>(1024);
        Thread producer = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                while (!queue.offer(i)) {
                    // busy spin until space available
                }
            }
        });

        Thread consumer = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < ITERATIONS; i++) {
                Integer v;
                while ((v = queue.poll()) == null) {
                    // busy spin until data available
                }
                sum += v;
            }
            System.out.println("Lamport sum: " + sum);
        });

        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        long end = System.currentTimeMillis();
        return end - start;
    }

    private static long runBlockingQueueTest() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1024);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < ITERATIONS; i++) {
                    queue.put(i);
                }
            } catch (InterruptedException ignored) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                int sum = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    sum += queue.take();
                }
                System.out.println("Blocking sum: " + sum);
            } catch (InterruptedException ignored) {}
        });

        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
