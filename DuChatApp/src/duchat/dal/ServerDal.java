/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.dal;

import static duchat.dal.MessageDal.connectionString;
import duchat.entity.Message;
import duchat.entity.Server;
import duchat.entity.User;
import duchat.service.MessageService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class ServerDal {

    public static final DbHelper DB_HELPER = new DbHelper();
    static Connection connectionString;

    public static boolean create(Server server) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "INSET INTO server (name,ip,port,owner) values(?,?,?)";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setString(1, server.getName());
            statement.setString(2, server.getIp());
            statement.setInt(3, server.getPort());
            statement.setInt(3, server.getOwner());

            statement.executeUpdate();
            success = true;
        } catch (SQLException ex) {
        }

        return success;
    }

    public static boolean delete(Server server) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "DELETE FROM server WHERE id=?";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setInt(1, server.getId());

            statement.executeUpdate();
            success = true;
        } catch (SQLException ex) {
        }

        return success;
    }

    public static ResultSet getServerList(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rsx = null;
        String query = "SELECT * FROM server WHERE id IN(SELECT server_id FROM serveruser WHERE user_id=?)";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();

            return rs;
        } catch (SQLException ex) {
        }
        return rsx;
    }
}
