package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.User;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {

    private static final String URL = "jdbc:postgresql://localhost:5555/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);

        DataSource dataSource = new HikariDataSource(config);
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        User creator = new User(2, "user", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(5, "room", creator, new ArrayList<>());
        Message message = new Message(null, creator, room, "Hello!", LocalDateTime.now().toString());
        messagesRepository.save(message);
        System.out.println(message.getId());
    }
}
