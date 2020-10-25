/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.app.server;

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

    private String serverIp;
    private int portNumber;
    private String serverName;
    private ServerSocket serverSocket = null;
    private String serverCode;
    private Set<ClientHandlerThread> clientThreads = new HashSet<ClientHandlerThread>();
    private Set<String> userNames = new HashSet<>();

    private static int userCount = 0;

    public ChatServer(User user, Server server) {
        try {
            this.serverName = server.getName();
            this.portNumber = server.getPort();
            this.serverCode = server.getCode();
            this.serverSocket = new ServerSocket(this.portNumber);
            this.serverIp = server.getIp();

        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        try {
            new ClientAcceptThread(this).start();

        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<ClientHandlerThread> getClientThreads() {
        return clientThreads;
    }

    public int getUserCount() {
        return userCount;
    }

    public Set<String> getUserNames() {
        return this.userNames;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public String getServerName() {
        return serverName;
    }

    public String getHost() {
        return serverIp;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void addUserThread(ClientHandlerThread chatClientThread) {
        this.clientThreads.add(chatClientThread);
    }

    public String getServerCode() {
        return serverCode;
    }

    public void increaseUserCount() {
        userCount += 1;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }

    void broadcast(String message, ClientHandlerThread excludeUser) {
        for (ClientHandlerThread aUser : clientThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    void removeUser(String userName, ClientHandlerThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            clientThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }
}
