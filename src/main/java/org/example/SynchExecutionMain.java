package org.example;

import java.util.concurrent.CountDownLatch;

public class SynchExecutionMain {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        for(int i = 0; i < 5; i++) {
            Thread t = new Thread(new SynchExecution(5 - i, latch));
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
