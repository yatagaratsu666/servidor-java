/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.app.chat.contract.ErrorMessage;
import com.mycompany.app.chat.contract.GeneralMessage;
import com.mycompany.app.chat.contract.LogoutMessage;
import com.mycompany.app.chat.contract.Message;
import com.mycompany.app.chat.contract.PrivateMessage;
import com.mycompany.app.chat.contract.UserJoinedMessage;
import com.mycompany.app.chat.contract.UserLeftMessage;
import com.mycompany.app.chat.contract.UserListMessage;
import com.mycompany.app.chat.contract.enums.MessageType;
import com.mycompany.app.chat.contract.request.LoginRequest;
import com.mycompany.app.chat.contract.response.LoginResponse;
import com.mycompany.app.chat.serialization.JacksonProvider;
import com.mycompany.app.chat.serialization.JsonSerializer;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class Protocol {

    private final JsonSerializer serializer;

    private final Map<MessageType, Class<? extends Message>> registry;

    public Protocol() {

        serializer = new JsonSerializer();
        registry = new EnumMap<>(MessageType.class);
        registerMessages();

    }

    private void registerMessages() {

        registry.put(MessageType.LOGIN, LoginRequest.class);
        registry.put(MessageType.LOGIN_OK, LoginResponse.class);
        registry.put(MessageType.LOGIN_ERROR, LoginResponse.class);
        registry.put(MessageType.GENERAL_MESSAGE, GeneralMessage.class);
        registry.put(MessageType.PRIVATE_MESSAGE, PrivateMessage.class);
        registry.put(MessageType.USER_LIST, UserListMessage.class);
        registry.put(MessageType.USER_JOINED, UserJoinedMessage.class);
        registry.put(MessageType.USER_LEFT, UserLeftMessage.class);
        registry.put(MessageType.LOGOUT, LogoutMessage.class);
        registry.put(MessageType.ERROR, ErrorMessage.class);

    }

    public Message decode(String json) throws IOException {

        JsonNode node = JacksonProvider.getMapper().readTree(json);

        MessageType type = MessageType.valueOf(node.get("type").asText());

        Class<? extends Message> clazz = registry.get(type);

        if (clazz == null) {
            throw new IllegalArgumentException("Tipo de mensaje no registrado: " + type);
        }

        return serializer.deserialize(json, clazz);

    }

    public Message deserialize(String json) throws IOException {
        return decode(json);
    }

    public String serialize(Message message) throws IOException {
        return encode(message);
    }

    public String encode(Message message) throws IOException {
        return serializer.serialize(message);
    }

}
