/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.service;

import duchat.dal.MessageDal;
import duchat.entity.Message;
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
public class MessageService {

    public static boolean sendMessage(Message message) {
        return MessageDal.sendMessage(message);
    }

    public static ArrayList<Message> getMessages(User user) {
        ResultSet resultSet = MessageDal.getMessages(user);
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            // transfer datas from resultset to java objects and store them in a datastructure
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
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messages;
    }
}
