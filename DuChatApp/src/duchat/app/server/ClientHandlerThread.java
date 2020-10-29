/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.app.server;

import duchat.entity.Message;
import duchat.entity.User;
import duchat.service.MessageService;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class ClientHandlerThread implements Runnable {

    Thread thread;
    User user;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ChatServer chatServer;
    private Socket clientSocket = null;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    MessageService messageService;
    Message clientPacket;

    public ClientHandlerThread(Socket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.chatServer = server;
        this.messageService = new MessageService();
    }

    // implement a run() method provided by a Runnable interface
    @Override
    public void run() {
        try {

            OutputStream outputStream = this.clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(new Message(0, chatServer.getServerId(), "ChatServer", 0, "Connection Successfull.", ""));

            InputStream inputStream = this.clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            user = (User) objectInputStream.readObject();

            ArrayList<Message> lastMessages = messageService.getMessages(user, chatServer.getServer());
            for (Message m : lastMessages) {
                objectOutputStream.writeObject(m);
            }

            running.set(true);
            while (running.get()) {
                clientPacket = (Message) objectInputStream.readObject();

                if (!clientPacket.getText().equals("/bye")) {
                    messageService.saveMessage(clientPacket);
                    chatServer.broadcast(clientPacket, this);
                } else {
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            System.out.println(chatServer.getServerName() + "-" + chatServer.getServerId() + ": Client Handler Thread Started.");
        }
    }

    public void stop() {
        running.set(false);
    }

    void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void printUsers() {
        if (chatServer.hasUsers()) {
            // writer.println("Connected users: " + chatServer.getUserNames());
        } else {
            // writer.println("No other users connected");
        }
    }

}
