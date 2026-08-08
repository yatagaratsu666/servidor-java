/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.contract;

/**
 *
 * @author BRENDA
 */

import com.mycompany.app.chat.contract.enums.MessageType;

public class GeneralMessage extends Message {

    private String sender;

    private String content;

    public GeneralMessage() {
        super(MessageType.GENERAL_MESSAGE);
    }

    public GeneralMessage(String sender, String content) {

        super(MessageType.GENERAL_MESSAGE);

        this.sender = sender;
        this.content = content;

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
