/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.contract.request;

import com.mycompany.app.chat.contract.enums.MessageType;
import com.mycompany.app.chat.contract.Message;

/**
 *
 * @author BRENDA
 */

public class LoginRequest extends Message {
    
    private String username;

    public LoginRequest() {
        super(MessageType.LOGIN);
    }

    public LoginRequest(String username) {
        super(MessageType.LOGIN);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}