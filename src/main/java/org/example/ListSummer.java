package org.example;

import java.util.List;

public class ListSummer implements Runnable{

    private List<Integer> lst;

    public ListSummer(List<Integer> lst) {
        this.lst = lst;
    }

    public void addNum(int num) {
        lst.add(num);
    }

    @Override
    public void run() {
        while(true) {
            int sum = lst.stream().reduce(0 , Integer::sum);
            System.out.println(sum);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.println("Terminating Summer");
                throw new RuntimeException(e);
            }
        }
    }
}
