package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        ListSummer task = new ListSummer(lst);
        Thread t1 = new Thread(task);
        t1.start();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter a number to add to the list");
            int num = Integer.parseInt(scanner.nextLine());
            task.addNum(num);
        }
    }
}