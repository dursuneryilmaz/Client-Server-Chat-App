/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.dal;
import java.sql.*;
/**
 *
 * @author dursun
 */
public class DbHelper {
    private final String userName="appuser";
    private final String password="12345";
    private final String dbUrl="jdbc:mysql://192.168.16.108:3306/duchat?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbUrl,userName,password);
    }
    
    public void showErrorMessage(SQLException exception){
        System.out.println("Error : "+exception.getMessage());
        System.out.println("Error code : "+exception.getErrorCode());
    }  
}


