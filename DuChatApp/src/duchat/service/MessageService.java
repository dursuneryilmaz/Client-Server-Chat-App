/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.service;

import duchat.dal.MessageDal;
import duchat.entity.Message;
import duchat.entity.Server;
import duchat.entity.User;
import java.util.ArrayList;

/**
 *
 * @author dursun
 */
public class MessageService {

    final MessageDal messageDal = new MessageDal();

    public boolean saveMessage(Message message) {
        return messageDal.createMessage(message);
    }

    public ArrayList<Message> getMessages(User user, Server server) {
        return messageDal.getMessages(user, server);
    }
}
