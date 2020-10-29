/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.dal;

import duchat.entity.Message;
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
public class MessageDal {

    public static final DbHelper DB_HELPER = new DbHelper();
    static Connection connectionString;

    public boolean createMessage(Message message) {
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
            System.out.print(ex.getMessage());
        }

        return success;
    }

    public ArrayList<Message> getMessages(User user, Server server) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MessageDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Message> messages = new ArrayList<>();
        
        String query = "SELECT * FROM message WHERE server=?"; // and timestamp>= DATE_SUB(NOW(), INTERVAL 1 DAY)

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setInt(1, server.getId());
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(new Message(
                        resultSet.getInt("id"),
                        resultSet.getInt("server"),
                        resultSet.getString("sendername"),
                        resultSet.getInt("sender"),
                        resultSet.getString("text"),
                        resultSet.getString("timestamp")
                )
                );
            }
            return messages;
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return messages;
    }
}
