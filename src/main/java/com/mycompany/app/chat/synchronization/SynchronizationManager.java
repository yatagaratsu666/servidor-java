/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.synchronization;

import com.mycompany.app.chat.synchronization.mutex.Mutex;

// es exclusion mutua xq solo un hilo puede entrar a la sección crtica al mismo tiempo
// no lo olvides :3
// mutex implementado a mano (sin java.util.concurrent.locks), ver paquete .mutex

public class SynchronizationManager {

    private final Mutex mutex;

    public SynchronizationManager() {

        mutex = new Mutex();

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