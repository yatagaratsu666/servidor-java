/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.bussiness;

/**
 *
 * @author BRENDA
 */

import com.mycompany.app.chat.model.ConversacionPrivada;
import com.mycompany.app.chat.model.Mensaje;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PrivateChatService {

    private final Map<String, ConversacionPrivada> conversations;

    private final ReentrantLock lock;

    public PrivateChatService() {

        conversations = new ConcurrentHashMap<>();
        lock = new ReentrantLock(true);

    }

    private String generateKey(String user1, String user2) {

        if (user1.compareToIgnoreCase(user2) < 0) {
            return user1 + "|" + user2;
        }

        return user2 + "|" + user1;

    }

    public void addMessage(String sender, String receiver, String content) {

        lock.lock();

        try {

            String key = generateKey(sender, receiver);
            ConversacionPrivada conversation = conversations.computeIfAbsent(key, k -> new ConversacionPrivada(sender, receiver));
            conversation.getMensajes().add(new Mensaje(sender, content));
        } finally {

            lock.unlock();
        }

    }

    public List<Mensaje> getConversation(String user1, String user2) {

        lock.lock();

        try {

            String key = generateKey(user1, user2);
            ConversacionPrivada conversation = conversations.get(key);

            if (conversation == null) {
                return new ArrayList<>();
            }

            return new ArrayList<>(conversation.getMensajes());

        } finally {
            lock.unlock();

        }

    }

}
