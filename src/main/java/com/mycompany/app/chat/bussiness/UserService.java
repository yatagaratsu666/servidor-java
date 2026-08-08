/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.bussiness;

/**
 *
 * @author BRENDA
 */
import com.mycompany.app.chat.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class UserService {

    private final ConcurrentHashMap<String, Usuario> users;

    private final ReentrantLock lock;

    public UserService() {

        users = new ConcurrentHashMap<>();
        lock = new ReentrantLock(true);

    }

    public boolean login(String username) {

        lock.lock();

        try {
            if (users.containsKey(username)) {
                return false;
            }

            users.put(username, new Usuario(username));
            return true;

        } finally {
            lock.unlock();

        }

    }

    public void logout(String username) {

        lock.lock();

        try {
            users.remove(username);

        } finally {
            lock.unlock();

        }

    }

    public Usuario find(String username) {
        return users.get(username);
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }

    public List<String> getConnectedUsers() {
        return new ArrayList<>(users.keySet());
    }

}