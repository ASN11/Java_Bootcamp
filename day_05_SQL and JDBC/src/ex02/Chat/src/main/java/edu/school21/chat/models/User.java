package edu.school21.chat.models;

import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private List<Chatroom> createdChats;
    private List<Chatroom> userWriteChats;

    public User() {}
    public User(int id, String login, String password, List<Chatroom> createdChats, List<Chatroom> userWriteChats) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChats = createdChats;
        this.userWriteChats = userWriteChats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedChats() {
        return createdChats;
    }

    public void setCreatedChats(List<Chatroom> createdChats) {
        this.createdChats = createdChats;
    }

    public List<Chatroom> getUserWriteChats() {
        return userWriteChats;
    }

    public void setUserWriteChats(List<Chatroom> userWriteChats) {
        this.userWriteChats = userWriteChats;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdChats=" + createdChats +
                ", userWriteChats=" + userWriteChats +
                '}';
    }
}
