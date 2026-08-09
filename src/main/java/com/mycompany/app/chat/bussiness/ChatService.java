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
import com.mycompany.app.chat.synchronization.mutex.Mutex;
import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private final List<Mensaje> messages;
    private final Mutex lock;

    public ChatService() {
        
        messages = new ArrayList<>();
        lock = new Mutex();
        
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
