/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.service;

import duchat.dal.MessageDal;
import duchat.dal.ServerDal;
import duchat.entity.Message;
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

    public static boolean create(Server server) {
        return ServerDal.create(server);
    }

    public static boolean delete(Server server) {
        return ServerDal.delete(server);
    }

    public ArrayList<Server> getServerList(User user) {
        ResultSet resultSet = ServerDal.getServerList(user);
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
}
