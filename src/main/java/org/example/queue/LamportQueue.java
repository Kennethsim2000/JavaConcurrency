package org.example.queue;

public class LamportQueue<T> {
    private final Object[] buffer;
    private final int capacity;

    private volatile long head; // index of next element to consume
    private volatile long tail; // index of next element to produce


    public LamportQueue(int capacity) {
        this.buffer = new Object[capacity];
        this.capacity = capacity;
        this.head = 0;
        this.tail = 0;
    }

    public boolean offer(T elem) {
        long currTail = tail;
        if(isFull()) {
            return false;
        }
        int index = (int) (currTail % capacity);
        buffer[index] = elem;
        tail = currTail + 1;
        return true;
    }

    @SuppressWarnings("unchecked") // use to suppress warnings for casting object to T
    public T poll() {
        long currHead = head; // You read head once at the start of the method, atomicity
        if(isEmpty()) {
            return null;
        }
        int index = (int) (currHead % capacity);
        T elem = (T) buffer[index];
        buffer[index] = null; // help gc
        head = currHead + 1; // do not do head++, we want atomicity
        return elem;
    }

    public boolean isFull() {
        return (tail - head) >= capacity;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        return (int) (tail - head);
    }
}

//1. Study the description of the Lamport Queue in the chapter (and note it is intended for use in single producer/single consumer scenarios).
// The idea is a non-blocking alternative implementation of a queue. It uses an array to store the data and maintains two long values to represent the head (
// from where elements are taken) and tail (where elements are added). These values will use mod (%) to ensure that they always point to a value in the range of
// the array.

//An empty queue is indicated when the two indices point to the same element (at initialization they will both have value e).
// A full queue is indicated when the distance between them is greater than or equal to the array length.
//By making the values volatile, we ensure that changes made by one thread are visible in the other.
//Implement and test a version of the Lamport Queue. Make sure you provide a test harness. Compare the performance of the
// queue against some of the standard, Java