package org.example;

public final class StatsCounter {
    private int successCount_;
    private static final Object lock = new Object();

    public int getSuccessCount() {
        synchronized (lock) {
            return successCount_;
        }

    }

    public void increaseSuccessCount(int delta) {
        synchronized (lock) {
            successCount_ += delta;
        }

    }
}
