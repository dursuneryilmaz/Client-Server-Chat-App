/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.app.server;

import duchat.entity.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class ClientHandlerThread implements Runnable {

    Thread thread;
    private final ChatServer chatServer;
    private Socket clientSocket = null;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    Message clientPacket;
    Message serverPacket;

    public ClientHandlerThread(Socket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.chatServer = server;
    }

    // implement a run() method provided by a Runnable interface
    @Override
    public void run() {
        try {
            InputStream inputStream = this.clientSocket.getInputStream();
            OutputStream outputStream = this.clientSocket.getOutputStream();

            objectInputStream = new ObjectInputStream(inputStream);
            objectOutputStream = new ObjectOutputStream(outputStream);
           /*  TO-DO List
            * Get the incoming transport object(message) from objectInpputStream
            * Store the message to database 
            * Send the message to all clients using servers broadcast() method
            * Determine the user logout status 
            * if client leave remove username and clientThread from server using servers removeUser() method
            * Inform all clients about leave
            * close socket
            *
            */
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        System.out.println("Messaging Thread Start to Run for Particular User.");
        if (thread == null) {
            // instantiate a Thread object
            thread = new Thread(this);
            // start it by calling start() method, which executes a call to run() method.
            thread.start();
        }
    }

    void sendMessage(String message) {

    }

    void printUsers() {
        if (chatServer.hasUsers()) {
            // writer.println("Connected users: " + chatServer.getUserNames());
        } else {
            // writer.println("No other users connected");
        }
    }

}
