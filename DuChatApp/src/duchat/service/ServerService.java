/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.service;

import duchat.dal.ServerDal;
import duchat.entity.Server;
import duchat.entity.User;
import java.util.ArrayList;

/**
 *
 * @author dursun
 */
public class ServerService {

    final ServerDal serverDal = new ServerDal();

    public boolean createServer(Server server) {
        return serverDal.createServer(server);
    }

    public boolean deleteServer(Server server) {
        return serverDal.deleteServer(server);
    }

    public ArrayList<Server> getConnectedServerList(User user) {
        return serverDal.getConnectedServerList(user);
    }

    public Server getServerByCode(String code) {
        return serverDal.getServerByCode(code);
    }

    public boolean connectServer(User user, Server server) {
        return serverDal.connectServer(user, server);
    }

    public ArrayList<Server> getOwnedServerList(User user) {
       return serverDal.getOwnedServerList(user);
    }
}
