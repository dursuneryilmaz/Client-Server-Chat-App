/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.app.server;

import duchat.entity.Message;
import duchat.entity.Server;
import duchat.entity.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class ChatServer {

    private Server server;
    private ServerSocket serverSocket = null;
    private Set<ClientHandlerThread> clientThreads = new HashSet<ClientHandlerThread>();
    private Set<String> userNames = new HashSet<>();
    private ClientAcceptThread clientAcceptThread;

    public ChatServer(User user, Server server) {
        try {
            this.server = server;
            this.serverSocket = new ServerSocket(this.server.getPort());

        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server getServer() {
        return server;
    }

    public int getServerId() {
        return server.getId();
    }

    public String getServerName() {
        return server.getName();
    }

    public String getServerIp() {
        return server.getIp();
    }

    public int getServerPort() {
        return server.getPort();
    }

    public int getServerOwner() {
        return server.getOwner();
    }

    public String getServerCode() {
        return server.getCode();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Set<ClientHandlerThread> getClientThreads() {
        return clientThreads;
    }

    public Set<String> getUserNames() {
        return this.userNames;
    }

    public int getUserCount() {
        return clientThreads.size();
    }

    public void addUserThread(ClientHandlerThread chatClientThread) {
        this.clientThreads.add(chatClientThread);
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }

    void removeUser(ClientHandlerThread aUser) {
        clientThreads.remove(aUser);
    }

    public void start() {
        try {
            System.out.println(server.getName() + "-" + server.getId() + ": Server Started.");
            this.clientAcceptThread = new ClientAcceptThread(this);
            clientAcceptThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void broadcast(Message message, ClientHandlerThread excludeUser) {
        for (ClientHandlerThread aUser : clientThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    public void stop() {
        try {
            clientAcceptThread.stop();

            for (ClientHandlerThread clientHandlerThread : clientThreads) {
                clientHandlerThread.stop();
            }
            clientThreads.clear();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
