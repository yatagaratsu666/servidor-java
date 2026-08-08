/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.contract;


import com.mycompany.app.chat.contract.enums.MessageType;

public class PrivateMessage extends Message {

    private String sender;

    private String receiver;

    private String content;

    public PrivateMessage() {
        super(MessageType.PRIVATE_MESSAGE);
    }

    public PrivateMessage(String sender, String receiver, String content) {

        super(MessageType.PRIVATE_MESSAGE);

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
