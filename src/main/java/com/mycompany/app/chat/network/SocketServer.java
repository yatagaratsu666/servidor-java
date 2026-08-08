/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.network;

/**
 *
 * @author BRENDA
 */
import com.mycompany.app.chat.bussiness.ChatService;
import com.mycompany.app.chat.bussiness.MessageDispatcher;
import com.mycompany.app.chat.bussiness.PrivateChatService;
import com.mycompany.app.chat.bussiness.UserService;
import com.mycompany.app.chat.contract.Message;
import com.mycompany.app.chat.synchronization.SynchronizationManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    private final int port;

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private final ConnectionManager connectionManager;

    private final Protocol protocol;

    private final MessageDispatcher dispatcher;

    private final ExecutorService executor;

    private final SynchronizationManager synchronizationManager;

    public SocketServer(int port) {

        this.port = port;
        this.connectionManager = new ConnectionManager();
        this.protocol = new Protocol();

        UserService userService = new UserService();
        ChatService chatService = new ChatService();
        PrivateChatService privateChatService = new PrivateChatService();
        this.synchronizationManager = new SynchronizationManager();

        this.dispatcher = new MessageDispatcher(userService, chatService, privateChatService, connectionManager, synchronizationManager);

        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    public void start() throws IOException {

        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT
        );

        System.out.println("Servidor iniciado en el puerto " + port);
        eventLoop();

    }

    private void eventLoop() throws IOException {

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (!key.isValid()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ClientSession session = connectionManager.get(channel);

                    if (session != null) {
                        disconnect(session);
                    }

                    continue;
                }

                try {
                    if (key.isAcceptable()) {
                        acceptClient();

                    } else if (key.isReadable()) {
                        readClient(key);

                    }

                } catch (Exception e) {

                    key.cancel();

                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ClientSession session = connectionManager.get(channel);

                        if (session != null) {
                            disconnect(session);

                        } else {
                            channel.close();
                        }

                    } catch (IOException ignored) {
                    }

                }

            }

        }

    }

    private void acceptClient() throws IOException {

        SocketChannel channel = serverChannel.accept();

        if (channel == null) {
            return;
        }

        channel.configureBlocking(false);

        channel.register(selector, SelectionKey.OP_READ
        );

        ClientSession session = new ClientSession(channel);
        connectionManager.add(session);

        System.out.println("Cliente conectado");

    }

    private void readClient(SelectionKey key) {

        SocketChannel channel = (SocketChannel) key.channel();
        ClientSession session = connectionManager.get(channel);

        if (session == null) {
            return;
        }

        try {
            int bytesRead = channel.read(session.getReadBuffer());

            if (bytesRead == -1) {
                disconnect(session);
                return;
            }

            if (bytesRead == 0) {
                return;
            }

            session.getReadBuffer().flip();
            String received = java.nio.charset.StandardCharsets.UTF_8.decode(session.getReadBuffer()).toString();
            session.getReadBuffer().clear();
            session.getInputBuffer().append(received);
            processMessages(session);

        } catch (Exception e) {
            disconnect(session);

        }

    }

    private void processMessages(ClientSession session) {

        StringBuilder buffer = session.getInputBuffer();
        int index;

        while ((index = buffer.indexOf("\n")) != -1) {
            String json = buffer.substring(0, index).trim();
            buffer.delete(0, index + 1);

            if (json.isEmpty()) {
                continue;
            }

            executor.submit(() -> {
                try {
                    Message message = protocol.deserialize(json);
                    dispatcher.dispatch(session, message);

                    if (message.getType().name().equals("LOGOUT")) {
                        disconnect(session);
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            });

        }

    }

    private void disconnect(ClientSession session) {

        System.out.println(
                "Cliente desconectado"
        );

        if (session.isAuthenticated()) {
            dispatcher.disconnect(session);
        }

        connectionManager.remove(session.getChannel());

        try {
            session.getChannel().close();

        } catch (IOException ignored) {
        }

    }

}
