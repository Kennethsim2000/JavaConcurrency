package org.example;

import java.util.concurrent.locks.ReentrantLock;

public final class StatsCounter {
    private int successCount_;
    private int failCount_;
    private static final ReentrantLock successLock = new ReentrantLock();
    private final ReentrantLock failLock = new ReentrantLock();

    public int getSuccessCount() {
        successLock.lock();
        try {
            return successCount_;
        } finally {
            successLock.unlock();
        }
    }

    public void increaseSuccessCount(int delta) {
        successLock.lock();
        try {
            successCount_ += delta;
        } finally {
            successLock.unlock();
        }
    }

    public final int getFailCount() {
        failLock.lock();
        try {
            return failCount_;
        } finally {
            failLock.unlock();
        }
    }

    public final void increaseFailCount(int delta) {
        failLock.lock();
        try {
            failCount_ += delta;
        } finally {
            failLock.unlock();
        }
    }

    public final void makeFailsSuccess(int delta) {
        failLock.lock();
        successLock.lock();
        try {
            failCount_ -= delta;
            successCount_ += delta;
        } finally {
            failLock.unlock();
            successLock.unlock();
        }
    }
}
