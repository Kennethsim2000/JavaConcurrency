package org.example;

public final class StatsCounter {
    private int successCount_;

    public int getSuccessCount() {
        return successCount_;
    }

    public void increaseSuccessCount(int delta) {
        successCount_ += delta;
    }
}
