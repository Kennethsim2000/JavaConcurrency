package org.example;

import java.util.concurrent.CountDownLatch;

public class SynchExecution implements Runnable {

    int sleepTime;
    CountDownLatch latch;

    public SynchExecution(int sleepTime, CountDownLatch latch) {
        this.sleepTime = sleepTime;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sleepTime * 1000);
            System.out.println("Thread has finished sleeping");
            latch.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
