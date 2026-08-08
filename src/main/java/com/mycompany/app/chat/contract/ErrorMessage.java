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

public class ErrorMessage extends Message {

    private String message;

    public ErrorMessage() {
        super(MessageType.ERROR);
    }

    public ErrorMessage(String message) {
        super(MessageType.ERROR);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
