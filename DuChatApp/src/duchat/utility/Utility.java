/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.utility;

import duchat.app.frmLogin;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dursun
 */
public class Utility {

    public static String passwordEncoder(String password) {
        MessageDigest digest;
        String encodedPassword = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            encodedPassword = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encodedPassword;
    }
    
    public static void log(){
        
    }
}
