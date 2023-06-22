package edu.school21.chat.models;

import java.util.List;

public class Chatroom {
    private int id;
    private String name;
    private User owner;
    private List<Message> ChatMessages;

    public Chatroom() {}
    public Chatroom(int id, String name, User owner, List<Message> chatMessages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        ChatMessages = chatMessages;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getChatMessages() {
        return ChatMessages;
    }

    public void setChatMessages(List<Message> chatMessages) {
        ChatMessages = chatMessages;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", ChatMessages=" + ChatMessages +
                '}';
    }
}
