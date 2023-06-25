package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final DataSource dataSource;
    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("hikariDS") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = new User();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
            } else {
                System.out.println("Пользователя с таким ID нет в системе.");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }

        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt("id"), resultSet.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
        return userList;
    }

    @Override
    public void save(User entity) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (id, email) VALUES (?, ?)");

            stmt.setInt(1, entity.getId());
            stmt.setString(2, entity.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    @Override
    public void update(User entity) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET email = ? WHERE id = ?");

            stmt.setInt(2, entity.getId());
            stmt.setString(1, entity.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = new User();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
            } else {
                System.out.println("Пользователя с таким email нет в системе.");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }

        return Optional.of(user);
    }

    /**
     * Нет реализации
     */
    @Deprecated
    @Override
    public void save(User entity, String temporaryPassword) {}

    @Deprecated
    @Override
    public void deleteAll() {}
}
