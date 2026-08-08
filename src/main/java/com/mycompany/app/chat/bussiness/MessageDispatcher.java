/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.bussiness;

/**
 *
 * @author BRENDA
 */
import com.mycompany.app.chat.contract.ErrorMessage;
import com.mycompany.app.chat.contract.GeneralMessage;
import com.mycompany.app.chat.contract.Message;
import com.mycompany.app.chat.contract.PrivateMessage;
import com.mycompany.app.chat.contract.UserJoinedMessage;
import com.mycompany.app.chat.contract.UserLeftMessage;
import com.mycompany.app.chat.contract.UserListMessage;
import com.mycompany.app.chat.contract.request.LoginRequest;
import com.mycompany.app.chat.contract.response.LoginResponse;
import com.mycompany.app.chat.network.ClientSession;
import com.mycompany.app.chat.network.ConnectionManager;
import com.mycompany.app.chat.synchronization.SynchronizationManager;

public class MessageDispatcher {

    private final UserService userService;
    private final ChatService chatService;
    private final PrivateChatService privateChatService;
    private final ConnectionManager connectionManager;
    private final SynchronizationManager synchronizationManager;

    public MessageDispatcher(UserService userService, ChatService chatService, PrivateChatService privateChatService, ConnectionManager connectionManager, SynchronizationManager synchronizationManager) {
        this.userService = userService;
        this.chatService = chatService;
        this.privateChatService = privateChatService;
        this.connectionManager = connectionManager;
        this.synchronizationManager = synchronizationManager;
    }

    public void dispatch(ClientSession session, Message message) {

        switch (message.getType()) {
            case LOGIN ->
                handleLogin(session, message);

            case GENERAL_MESSAGE ->
                handleGeneralMessage(session, message);

            case PRIVATE_MESSAGE ->
                handlePrivateMessage(session, message);

            case LOGOUT ->
                handleLogout(session);

            default -> {
            }
        }
    }

    private void handleLogin(ClientSession session, Message message) {

        LoginRequest request = (LoginRequest) message;

        boolean success = userService.login(request.getUsername());

        if (!success) {
            LoginResponse response = new LoginResponse(false, "El usuario ya está conectado");
            connectionManager.send(session, response);
            return;
        }

        session.setAuthenticated(true);
        session.setUsername(request.getUsername());

        LoginResponse response = new LoginResponse(true, "Bienvenido");

        synchronizationManager.execute(() -> {

            connectionManager.send(session, response);
            connectionManager.send(session, new UserListMessage(userService.getConnectedUsers()));
            connectionManager.broadcastExcept(session, new UserJoinedMessage(request.getUsername()));

        });

    }

    private void handleGeneralMessage(ClientSession session, Message message) {

        if (!session.isAuthenticated()) {
            return;
        }

        GeneralMessage general = (GeneralMessage) message;

        chatService.addMessage(session.getUsername(), general.getContent());

        synchronizationManager.execute(() -> {

            connectionManager.broadcast(new GeneralMessage(session.getUsername(), general.getContent()));

        });

    }

    private void handlePrivateMessage(ClientSession session, Message message) {

        if (!session.isAuthenticated()) {
            return;
        }

        PrivateMessage privateMessage = (PrivateMessage) message;

        privateChatService.addMessage(session.getUsername(), privateMessage.getReceiver(), privateMessage.getContent());

        synchronizationManager.execute(() -> {

            boolean sent = connectionManager.sendTo(privateMessage.getReceiver(), new PrivateMessage(session.getUsername(), privateMessage.getReceiver(), privateMessage.getContent()));

            if (!sent) {
                connectionManager.send(session, new ErrorMessage("El usuario no está conectado"));
            }

        });

    }

    private void handleLogout(ClientSession session) {

        if (!session.isAuthenticated()) {
            return;
        }

        userService.logout(session.getUsername());

    }

    public void disconnect(ClientSession session) {

        if (!session.isAuthenticated()) {
            return;
        }

        userService.logout(session.getUsername());

        synchronizationManager.execute(() -> {

            connectionManager.broadcast(new UserLeftMessage(session.getUsername()));

        });

    }

}
