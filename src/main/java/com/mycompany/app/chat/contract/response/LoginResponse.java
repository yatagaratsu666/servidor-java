/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.contract.response;

import com.mycompany.app.chat.contract.Message;
import com.mycompany.app.chat.contract.enums.MessageType;

/**
 *
 * @author BRENDA
 */
public class LoginResponse extends Message {

    private boolean success;

    private String message;

    public LoginResponse() {
        super(MessageType.LOGIN_OK);
    }

    public LoginResponse(boolean success, String message) {

        super(success ? MessageType.LOGIN_OK : MessageType.LOGIN_ERROR);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
