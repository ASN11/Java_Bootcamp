package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import javax.sql.DataSource;
import java.util.Optional;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message ID");
        Long userInput = Long.valueOf(scanner.nextLine());

        DataSource dataSource = new HikariDataSource(config);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> message = messagesRepository.findById(userInput);

        message.ifPresent(System.out::println);
    }
}
