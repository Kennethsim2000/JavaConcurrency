package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class SynchExecution implements Runnable {

    int sleepTime;
    CountDownLatch latch;
    CyclicBarrier barrier;

    public SynchExecution(int sleepTime, CountDownLatch latch, CyclicBarrier barrier) {
        this.sleepTime = sleepTime;
        this.latch = latch;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sleepTime * 1000);
            barrier.await();
            System.out.println("Thread has finished sleeping");
            latch.countDown();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }
}
