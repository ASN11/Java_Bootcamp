package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;
    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message message = new Message();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT *, to_char(message_datetime, 'YYYY-MM-DD HH24:MI:SS') AS time " +
                                                        "FROM messages " +
                                                        "JOIN chatrooms c on c.chatroom_id = messages.message_room " +
                                                        "JOIN users u on u.user_id = messages.message_author " +
                                                        "WHERE message_id = ?");

            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                User user = AddUser(resultSet);
                Chatroom chatroom = AddChatroom(resultSet);
                message = AddMessage(resultSet, user, chatroom);
            } else {
                System.out.println("Пользователя с таким ID нет в системе.");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }

        return Optional.of(message);
    }

    private Message AddMessage(ResultSet resultSet, User user, Chatroom chatroom) throws SQLException {
        return new Message(
                resultSet.getInt("message_id"),
                user,
                chatroom,
                resultSet.getString("message_text"),
                resultSet.getString("time")
        );
    }

    private Chatroom AddChatroom(ResultSet resultSet) throws SQLException {
        return new Chatroom(
                resultSet.getInt("chatroom_id"),
                resultSet.getString("chatroom_name"),
                null,
                null
        );
    }

    private User AddUser(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getInt("user_id"),
            resultSet.getString("login"),
            resultSet.getString("password"),
            null,
            null
        );
    }
}
