/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.network;

/**
 *
 * @author BRENDA
 */
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSession {

    private final SocketChannel channel;
    
    private final StringBuilder inputBuffer;

    private final ByteBuffer readBuffer;

    private String username;

    private boolean authenticated;

    public ClientSession(SocketChannel channel) {

        this.channel = channel;
        this.inputBuffer = new StringBuilder();
        this.readBuffer = ByteBuffer.allocate(8192);
        this.authenticated = false;

    }

    public SocketChannel getChannel() {
        return channel;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public StringBuilder getInputBuffer() {
        return inputBuffer;
    }

}
