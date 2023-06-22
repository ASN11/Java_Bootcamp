package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

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

        Optional<Message> messageOptional = messagesRepository.findById(7L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDate(LocalDateTime.now().toString());
            messagesRepository.update(message);
        }
    }
}
