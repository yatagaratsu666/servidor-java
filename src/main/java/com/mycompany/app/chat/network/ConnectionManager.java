/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.network;

/**
 *
 * @author BRENDA
 */
import com.mycompany.app.chat.contract.Message;
import com.mycompany.app.chat.serialization.JsonSerializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private final Map<SocketChannel, ClientSession> sessions;

    private final JsonSerializer serializer;

    public ConnectionManager() {

        sessions = new ConcurrentHashMap<>();
        serializer = new JsonSerializer();

    }

    public void add(ClientSession session) {
        sessions.put(session.getChannel(), session);
    }

    public void remove(SocketChannel channel) {

        sessions.remove(channel);

        try {
            channel.close();

        } catch (IOException ignored) {
        }

    }

    public ClientSession get(SocketChannel channel) {
        return sessions.get(channel);
    }

    public Collection<ClientSession> getSessions() {
        return sessions.values();
    }

    public ClientSession findByUsername(String username) {

        for (ClientSession session : sessions.values()) {
            if (!session.isAuthenticated()) {
                continue;
            }

            if (session.getUsername().equals(username)) {
                return session;
            }
        }

        return null;

    }

    public void send(ClientSession session, Message message) {

        try {

            String json = serializer.serialize(message);
            json += "\n";
            ByteBuffer buffer = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
            session.getChannel().write(buffer);

        } catch (IOException e) {
            remove(session.getChannel());

        }

    }

    public void broadcast(Message message) {

        for (ClientSession session : sessions.values()) {
            if (!session.isAuthenticated()) {
                continue;
            }

            send(session, message);
        }

    }

    public boolean sendTo(String username, Message message) {

        ClientSession destination = findByUsername(username);

        if (destination == null) {
            return false;
        }

        send(destination, message);

        return true;

    }

    public void broadcastExcept(ClientSession excluded, Message message) {

        for (ClientSession session : sessions.values()) {
            if (!session.isAuthenticated()) {
                continue;
            }

            if (session == excluded) {
                continue;
            }

            send(session, message);

        }

    }

}
