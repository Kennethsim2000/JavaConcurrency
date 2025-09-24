package org.example;

public class LockFree {
    static MyInteger i = new MyInteger();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Incrementer(i, 500000));
        Thread t2 = new Thread(new Incrementer(i, 500000));
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

class Incrementer implements Runnable {

    MyInteger toIncrement;
    int times;

    public Incrementer(MyInteger toIncrement, int n) {
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


class MyInteger {
    private int n = 0;

    public void increment() {
        n = n + 1;
    }

    public int get() {
        return n;
    }
}