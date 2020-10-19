/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duchat.entity;

/**
 *
 * @author dursun
 */
public class Message {
    private int id;
    private int server;
    private String sendername;
    private int sender;
    private String text;
    private String timestamp;

    public Message(int id, int server, String sendername, int sender, String text, String timestamp) {
        this.id = id;
        this.server = server;
        this.sendername = sendername;
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }
}
