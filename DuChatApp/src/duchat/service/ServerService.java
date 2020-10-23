/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.service;

import duchat.dal.ServerDal;
import duchat.entity.Server;
import duchat.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class ServerService {

    final ServerDal serverDal = new ServerDal();

    public boolean create(Server server) {
        return serverDal.create(server);
    }

    public boolean delete(Server server) {
        return serverDal.delete(server);
    }

    public ArrayList<Server> getServerList(User user) {
        ResultSet resultSet = serverDal.getServerList(user);
        ArrayList<Server> servers = new ArrayList<Server>();
        try {
            // transfer datas from resultset to java objects and store them in a datastructure
            while (resultSet.next()) {
                servers.add(new Server(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("ip"),
                        resultSet.getInt("port"),
                        resultSet.getInt("owner"),
                        resultSet.getString("code")
                )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servers;
    }

    public Server getServerByCode(String code) {
        return serverDal.getServerByCode(code);

    }

    public boolean login(User user, Server server) {
        return serverDal.login(user, server);
    }
}
