/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.bussiness;

/**
 *
 * @author BRENDA
 */
import com.mycompany.app.chat.model.Mensaje;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ChatService {

    private final List<Mensaje> messages;
    private final ReentrantLock lock;

    public ChatService() {
        
        messages = new ArrayList<>();
        lock = new ReentrantLock(true);
        
    }

    public void addMessage(String sender, String content) {
        
        lock.lock();
        
        try {
            messages.add(new Mensaje(sender, content));
            
        } finally {
            lock.unlock();
        }
        
    }

    public List<Mensaje> getMessages() {
        
        lock.lock();
        
        try {
            return new ArrayList<>(messages);
            
        } finally {
            lock.unlock();
        }
    }

}
