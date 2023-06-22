package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.NotSavedSubEntityException;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * На основе полученного ID получает из БД данные и заполняет на их основе объект message
     * @param id ID сообщения, введённое пользователем
     * @return Optional содержащий объект message
     */
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
                throw new NotSavedSubEntityException("Пользователя или чата с таким ID нет в системе.");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }

        return Optional.of(message);
    }

    /**
     * Вносит в БД данные из объекта message.<br>
     * Записывает сгенерированный message_id в message.
     * @param message Message
     */
    @Override
    public void save(Message message) {
        try (Connection conn = dataSource.getConnection()) {
            checkId(message, conn.createStatement());

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO messages (message_author, message_room, message_text, message_datetime) " +
                                                            "VALUES (?, ?, ?, ?)",
                                                            Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, message.getAuthor().getId());
            stmt.setInt(2, message.getRoom().getId());
            stmt.setString(3, message.getText());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getDate().replace('T', ' ')));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int messageId = generatedKeys.getInt(1);
                message.setId(messageId);
            }

        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }


    /**
     * Проверяет наличие пользователя и сообщения в БД.<br>
     * При отсутствии бросает исключение NotSavedSubEntityException
     * @param message Message
     * @param statement Statement
     */
    private void checkId(Message message, Statement statement) {
        try {
            ResultSet idExist = statement.executeQuery("SELECT * FROM users, chatrooms " +
                                                        "WHERE user_id = " + message.getAuthor().getId() +
                                                        " AND chatroom_id = " + message.getRoom().getId());

            if (!idExist.next())
                throw new NotSavedSubEntityException("Пользователя или чата с таким ID нет в системе.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
