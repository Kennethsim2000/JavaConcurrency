package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeThree {
    static AtomicInteger i = new AtomicInteger();

    public static void main(String[] args) {
        Thread t1 = new Thread(new IncrementerThree(i, 500000));
        Thread t2 = new Thread(new IncrementerThree(i,500000));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final result -> " + i.get());
    }
}

class IncrementerThree implements Runnable {

    int times;
    AtomicInteger num;

    public IncrementerThree(AtomicInteger num, int n) {
        this.times = n;
        this.num = num;
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++) {
            int res = num.incrementAndGet();
            System.out.println(res);
        }
    }
}

