package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CompletableFutureExample {

    public int getInt(int scale) {
        int slpTime = (int) (Math.random() * 5);
        try {
            Thread.sleep(slpTime);
        } catch(InterruptedException e) {
            System.err.println("Thread is interrupted while sleeping");
        }
        double res = Math.random() * scale;
        return (int) res;
    }

    public static void main(String[] args) {
        CompletableFutureExample futureExample = new CompletableFutureExample();
        List<Integer> evenList = new CopyOnWriteArrayList<>();
        List<Integer> oddList = new CopyOnWriteArrayList<>();
        final Lock lock = new ReentrantLock();
        int scale = 1000;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                return futureExample.getInt(scale);
            }).thenAccept(num-> {
                if(num % 2 == 0) {
                    evenList.add(num);
                } else {
                    oddList.add(num);
                }
                lock.lock();
                try {
                    System.out.println("Future has completed execution");
                    System.out.println("EvenList is " + evenList);
                    System.out.println("OddList is " + oddList);
                } finally {
                    lock.unlock();
                }
            });
            futureList.add(future);
        }
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        try {
            allOfFuture.join();
        } catch (CompletionException e) {
            Throwable cause = e.getCause();
            System.err.println("An exception occurred in one of the futures: " + cause.getMessage());
        }
    }
}
