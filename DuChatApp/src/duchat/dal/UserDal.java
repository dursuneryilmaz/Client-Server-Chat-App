/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.dal;

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
public class UserDal {

    final DbHelper DB_HELPER = new DbHelper();
    Connection connectionString;

    public User logIn(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "SELECT * FROM user WHERE username=? AND password=?";
        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setEmail(rs.getString("email"));
            }

            connectionString.close();
        } catch (SQLException ex) {
        }

        /* try {
        connectionString = DB_HELPER.getConnection();
        String queryGiris = "UPDATE user SET online=? WHERE id=?";
        PreparedStatement statementGiris = connectionString.prepareStatement(queryGiris);
        
        statementGiris.setString(1, "1");
        statementGiris.setString(2, Integer.toString(userId));
        boolean durum = statementGiris.execute();
        if (durum) {
        userId = -1;
        }
        } catch (SQLException e) {
        }*/
        return user;
    }

    public boolean register(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "INSERT INTO user (username,password,email) VALUES(?,?,?)";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            if (1 == statement.executeUpdate()) {
                success = true;
            }
        } catch (SQLException ex) {
        }

        return success;
    }

    public int userStatusCheck(User user) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        int userId = -1;

        String query = "SELECT * FROM user WHERE username=?";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setString(1, user.getUsername());
            ResultSet rs = statement.executeQuery();

            if (rs.first()) {
                userId = Integer.parseInt(rs.getString("id"));
            }

            connectionString.close();
        } catch (SQLException ex) {
        }

        return userId;
    }

    public boolean logOut(int id) {
        try {
            connectionString = DB_HELPER.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDal.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean success = false;

        String query = "UPDATE user SET online=? WHERE id=?";

        try {
            PreparedStatement statement = connectionString.prepareStatement(query);
            statement.setString(1, "0");
            statement.setInt(2, id);

            boolean result = statement.execute();

            if (result) {
                success = true;
            }

            connectionString.close();
        } catch (SQLException ex) {
        }

        return success;
    }

}
