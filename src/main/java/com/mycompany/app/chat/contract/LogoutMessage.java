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

public class LogoutMessage extends Message {

    private String username;

    public LogoutMessage() {
        super(MessageType.LOGOUT);
    }

    public LogoutMessage(String username) {
        super(MessageType.LOGOUT);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
