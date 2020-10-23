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
public class Server {

    private int id = -1;
    private String name;
    private String ip;
    private int port;
    private int owner;
    private String code;

    public Server(int id, String name, String ip, int port, int owner, String code) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.owner = owner;
        this.code = code;
    }

    public Server(String name, int port, int owner) {
        this.name = name;
        this.port = port;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }
}
