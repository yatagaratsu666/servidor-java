package com.mycompany.app.chat.synchronization.mutex;

import java.util.ArrayDeque;
import java.util.Queue;


public class Mutex {

    private Thread ownerThread = null;

    private int holdCount = 0;

    private final Queue<Thread> waitingQueue = new ArrayDeque<>();

    public synchronized void lock() {

        Thread current = Thread.currentThread();

        if (ownerThread == current) {
            holdCount++;
            return;
        }

        waitingQueue.add(current);

        while (ownerThread != null || waitingQueue.peek() != current) {
            try {
                wait();
            } catch (InterruptedException e) {
                waitingQueue.remove(current);
                Thread.currentThread().interrupt();
                throw new RuntimeException("Hilo interrumpido esperando el mutex", e);
            }
        }

        waitingQueue.poll();
        ownerThread = current;
        holdCount = 1;

    }

    public synchronized void unlock() {

        Thread current = Thread.currentThread();

        if (ownerThread != current) {
            throw new IllegalMonitorStateException("El hilo " + current.getName() + " intentó liberar un mutex que no posee");
        }

        holdCount--;

        if (holdCount == 0) {
            ownerThread = null;
            notifyAll();
        }

    }


    public synchronized boolean isHeldByCurrentThread() {
        return ownerThread == Thread.currentThread();
    }

}
