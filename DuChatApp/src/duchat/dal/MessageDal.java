/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.dal;

import duchat.entity.Message;
import duchat.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class MessageDal {

    public static final DbHelper DB_HELPER = new DbHelper();
    static Connection connectionString;

    public  boolean sendMessage(Message message) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "INSERT INTO message (server, sendername, sender, text) VALUES (?,?,?,?)";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setInt(1, message.getServer());
            statement.setString(2, message.getSendername());
            statement.setInt(3, message.getSender());
            statement.setString(4, message.getText());

            success = statement.execute();

        } catch (SQLException ex) {
        }

        return success;
    }

    public  ResultSet getMessages(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;
        ResultSet rsx = null;
        String query = "SELECT * FROM message WHERE server=? and timestamp>= DATE_SUB(NOW(), INTERVAL 1 HOUR)";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setString(1, "");
            statement.setInt(2, 2);

            ResultSet rs = statement.executeQuery();

            if (rs.first()) {

                success = true;
            }
            return rs;
        } catch (SQLException ex) {
        }
        return rsx;
    }
}
