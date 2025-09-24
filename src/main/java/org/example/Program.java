package org.example;

public class Program {

    public static final int threadCount_ = 10;
    public static final int iterationCount_ = 1_000_000;

    public static void main(String[] args) {
        final StatsCounter counter = new StatsCounter();
//        final StatsCounterLockFree counter = new StatsCounterLockFree();

        Thread[] threads = new Thread[threadCount_];

        for (int tnum = 0; tnum < threadCount_; ++tnum) {
            threads[tnum] = new Thread(() -> {
                for (int d = 0; d < iterationCount_; ++d) {
                    // increment success count
                    counter.increaseSuccessCount(1);

                    // increment fail count
                    counter.increaseFailCount(1);

                    // occasionally convert fails into successes
                    if (d % 1000 == 0) {
                        counter.makeFailsSuccess(1);
                    }
                }
            });
        }

        long startTime = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignore) {
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.printf(
                "Final Success Count: %d%nFinal Fail Count: %d%nCompleted in %.3f seconds%n",
                counter.getSuccessCount(),
                counter.getFailCount(),
                (endTime - startTime) / 1000.0
        );
    }
}

// Expected output:
// Final Success Count: 10010000
// Final Fail Count: 9990000