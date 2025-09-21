package org.example;

public class Program {
    public static final int threadCount_ = 10;
    public static final int iterationCount_ = 1000000;
    public static void main (String[] args)  {
        final StatsCounter c = new StatsCounter();
        Thread[] threads = new Thread[threadCount_];
        for(int tnum = 0; tnum < threadCount_; ++tnum) {
            threads[tnum] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int d = 0; d < iterationCount_; ++d) {
                        c.increaseSuccessCount(d);
                        c.increaseSuccessCount(-d);
                    }
                }
            });
        }
        long startTime = System.currentTimeMillis();
        for(Thread t: threads) {
            t.start();
        }
        for(Thread t: threads) {
            try {
                t.join();
            } catch(InterruptedException ignore) {

            }
        }
        long endTime = System.currentTimeMillis();
        System.out.printf("Result was: %d and it completed in %f seconds%n", c.getSuccessCount(), (endTime - startTime) / 1000.0);
    }



}
