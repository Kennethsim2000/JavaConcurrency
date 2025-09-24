package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class StatsCounterLockFree {
    private final AtomicInteger successCount_ = new AtomicInteger(0);
    private final AtomicInteger failCount_ = new AtomicInteger(0);

    public int getSuccessCount() {
        return successCount_.get();
    }

    public void increaseSuccessCount(int delta) {
        successCount_.addAndGet(delta);
    }

    public final int getFailCount() {
        return failCount_.get();
    }

    public final void increaseFailCount(int delta) {
        failCount_.addAndGet(delta);
    }

    public final void makeFailsSuccess(int delta) {
        successCount_.addAndGet(delta);
        failCount_.addAndGet(-delta);
    }
}
