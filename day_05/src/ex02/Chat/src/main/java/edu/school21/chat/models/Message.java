package edu.school21.chat.models;

public class Message {
    private Integer id;
    private User author;
    private Chatroom room;
    private String text;
    private String date;

    public Message() {}
    public Message(Integer id, User author, Chatroom room, String text, String date) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message : {\n" +
                "  id=" + id + ",\n" +
                "  author=" + author + ",\n" +
                "  room=" + room + ",\n" +
                "  text='" + text + '\'' + ",\n" +
                "  date= " + date + "\n" +
                '}';
    }
}
