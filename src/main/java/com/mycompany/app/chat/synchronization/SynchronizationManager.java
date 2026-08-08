/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.synchronization;

import java.util.concurrent.locks.ReentrantLock;

// es exclusion mutua xq solo un hilo puede entrar a la sección crtica al mismo tiempo
// no lo olvides :3

public class SynchronizationManager {

    private final ReentrantLock mutex;

    public SynchronizationManager() {

        mutex = new ReentrantLock(true);

    }

    public void execute(Runnable action) {
        mutex.lock();

        try {
            action.run();

        } finally {
            mutex.unlock();

        }

    }

}