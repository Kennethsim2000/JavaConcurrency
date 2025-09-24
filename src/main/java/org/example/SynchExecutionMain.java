package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class SynchExecutionMain {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        CyclicBarrier barrier = new CyclicBarrier(5, ()-> System.out.println("All threads have reached barrier"));

        for(int i = 0; i < 5; i++) {
            Thread t = new Thread(new SynchExecution(5 - i, latch, barrier));
            t.start();
        }
        try {
            latch.await();
            System.out.println("All threads have started");
        } catch (InterruptedException e) {
            System.err.println("Main thread have been interrupted");
        }

    }

}
