package org.example;

public class LockFreeTwo {
    static MyIntegerTwo i = new MyIntegerTwo();

    public static void main(String[] args) {
        Thread t1 = new Thread(new IncrementerTwo(i, 500000));
        Thread t2 = new Thread(new IncrementerTwo(i, 500000));
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

class IncrementerTwo implements Runnable {

    MyIntegerTwo toIncrement;
    int times;

    public IncrementerTwo(MyIntegerTwo toIncrement, int n) {
        this.toIncrement = toIncrement;
        this.times = n;
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++) {
            toIncrement.increment();
            System.out.println(toIncrement.get());
        }
    }
}


class MyIntegerTwo {
    private int n = 0;

    public synchronized void increment() {
        n = n + 1;
    }

    public synchronized int get() {
        return n;
    }
}
