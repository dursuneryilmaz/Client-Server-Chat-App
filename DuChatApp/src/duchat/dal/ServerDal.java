/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.dal;

import duchat.entity.Server;
import duchat.entity.User;
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

    public boolean createServer(Server server) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "INSERT INTO server (name,ip,port,owner) values(?,?,?,?)";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setString(1, server.getName());
            statement.setString(2, server.getIp());
            statement.setInt(3, server.getPort());
            statement.setInt(4, server.getOwner());

            statement.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        return success;
    }

    public boolean deleteServer(Server server) {
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
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        return success;
    }

    public ArrayList<Server> getConnectedServerList(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Server> servers = new ArrayList<Server>();

        String query = "SELECT * FROM server WHERE id IN(SELECT server_id FROM serveruser WHERE user_id=?)";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                servers.add(new Server(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("ip"),
                        rs.getInt("port"),
                        rs.getInt("owner"),
                        rs.getString("code"))
                );
            }

            return servers;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return servers;
    }

    public ArrayList<Server> getOwnedServerList(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Server> servers = new ArrayList<Server>();

        String query = "SELECT * FROM server WHERE owner=?";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                servers.add(new Server(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("ip"),
                        rs.getInt("port"),
                        rs.getInt("owner"),
                        rs.getString("code"))
                );
            }

            return servers;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return servers;
    }

    public Server getServerById(int id) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        Server server = null;

        String query = "SELECT * FROM server WHERE id=?";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                server = new Server(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("ip"),
                        rs.getInt("port"),
                        rs.getInt("owner"),
                        rs.getString("code"));
                return server;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return server;
    }

    public Server getServerByCode(String code) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        Server server = null;

        String query = "SELECT * FROM server WHERE code=?";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setString(1, code);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                server = new Server(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("ip"),
                        rs.getInt("port"),
                        rs.getInt("owner"),
                        rs.getString("code"));
            }
            return server;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return server;
    }

    public boolean connectServer(User user, Server server) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "INSERT INTO serveruser (server_id,user_id) values(?,?)";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setInt(1, server.getId());
            statement.setInt(2, user.getId());

            statement.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return success;
    }

    public Server updateServerIp(Server server) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "UPDATE server SET ip=? WHERE id=?";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setString(1, server.getIp());
            statement.setInt(2, server.getId());

            statement.executeUpdate();
            return getServerById(server.getId());

        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return server;
    }
}
