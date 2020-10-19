/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.service;

import duchat.dal.UserDal;
import duchat.entity.User;

/**
 *
 * @author dursun
 */
public class UserService {

    final UserDal userDal = new UserDal();

    public User logIn(User user) {
        return userDal.logIn(user);
    }

    public boolean register(User user) {
        if (!"".equals(user.getEmail().trim()) && !"".equals(user.getPassword().trim()) && !"".equals(user.getUsername().trim())) {
            return userDal.register(user);
        }
        return false;
    }

    public int userStatusCheck(User user) {
        return userDal.userStatusCheck(user);
    }

    public boolean logOut(int id) {
        return userDal.logOut(id);
    }
}
