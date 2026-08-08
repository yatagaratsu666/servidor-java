/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.model;

/**
 *
 * @author BRENDA
 */

import java.time.LocalDateTime;

public class Mensaje {

    private final String sender;

    private final String content;

    private final LocalDateTime timestamp;

    public Mensaje(String sender, String content) {

        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();

    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
