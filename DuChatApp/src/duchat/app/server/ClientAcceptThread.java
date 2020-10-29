/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.app.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author dursun
 */
public class ClientAcceptThread implements Runnable {

    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final String threadName;
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private final ChatServer chatServer;

    public ClientAcceptThread(ChatServer duChatServer) throws IOException {
        this.chatServer = duChatServer;
        this.serverSocket = duChatServer.getServerSocket();
        this.threadName = duChatServer.getServerName();
    }

    // implement a run() method provided by a Runnable interface
    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                clientSocket = this.serverSocket.accept();

                ClientHandlerThread newUserThread = new ClientHandlerThread(this.clientSocket, this.chatServer);
                this.chatServer.addUserThread(newUserThread);
                newUserThread.start();
                this.chatServer.increaseUserCount();

                //chatServer.addUserName("incoming username from clientside");
            } catch (IOException  ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void start() {
        System.out.println(threadName + "-" + chatServer.getServerId() + ": Client Accept Thread Started.");
        if (thread == null) {
            // instantiate a Thread object
            thread = new Thread(this, threadName);
            // start it by calling start() method, which executes a call to run() method.
            thread.start();
        }
    }

    public void stop() {
        running.set(false);
    }
}
