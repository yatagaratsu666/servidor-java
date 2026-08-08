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
import java.util.ArrayList;
import java.util.List;

public class UserListMessage extends Message {

    private List<String> users;

    public UserListMessage() {
        super(MessageType.USER_LIST);
        this.users = new ArrayList<>();
    }

    public UserListMessage(List<String> users) {
        super(MessageType.USER_LIST);
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

}
