package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.List;

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
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);

        List<User> users = usersRepository.findAll(0, 2);

        for (User user : users) {
            System.out.println(user);
        }
    }
}
