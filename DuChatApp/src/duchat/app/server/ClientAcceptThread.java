/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.app.server;

import duchat.entity.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author dursun
 */
public class ClientAcceptThread implements Runnable {

    private Thread thread;
    private final String threadName;
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private final ChatServer chatServer;
    User clientPacket;

    public ClientAcceptThread(ChatServer duChatServer) throws IOException {
        this.chatServer = duChatServer;
        this.serverSocket = duChatServer.getServerSocket();
        this.threadName = duChatServer.getServerName();
    }

    // implement a run() method provided by a Runnable interface
    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = this.serverSocket.accept();
                InputStream inputStream = this.clientSocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                OutputStream outputStream = this.clientSocket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                // TO-DO make client side send the user object or transport packet include user object to stode user names
                
                clientPacket = (User) objectInputStream.readObject();
                ClientHandlerThread newUserThread = new ClientHandlerThread(this.clientSocket, this.chatServer);
                chatServer.addUserThread(newUserThread);
                newUserThread.start();
                //chatServer.addUserName("incoming username from clientside");

            } catch (IOException | ClassNotFoundException ex) {

            }
        }
    }

    public void start() {
        System.out.println("Server Thread Started to Listen Socket for New Users.");
        if (thread == null) {
            // instantiate a Thread object
            thread = new Thread(this, threadName);
            // start it by calling start() method, which executes a call to run() method.
            thread.start();
        }

    }
}
