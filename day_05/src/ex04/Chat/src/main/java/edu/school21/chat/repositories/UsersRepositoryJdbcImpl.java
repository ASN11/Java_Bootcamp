package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final DataSource dataSource;
    private final String sql = "WITH userslimit AS (SELECT * FROM users OFFSET ? LIMIT ?) " +
            "SELECT * FROM userslimit " +
            "JOIN chatrooms c on userslimit.user_id = c.chatroom_owner " +
            "JOIN messages m on userslimit.user_id = m.message_author " +
            "JOIN chatrooms c2 on c2.chatroom_id = m.message_room " +
            "ORDER BY user_id";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Получает из БД данные всех пользователей<br>
     * Заполняет List<Chatroom> createdChats и List<Chatroom> userWriteChats у объекта User
     * @param page номер страницы выборки
     * @param size размер страницы выборки
     * @return List<User>
     */
    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();
        Set<Integer> userId = new HashSet<>();
        Set<Integer> createdChatId = new HashSet<>();
        Set<Integer> userWriteChatsId = new HashSet<>();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            int startIndex = page * size;
            stmt.setInt(1, startIndex);
            stmt.setInt(2, size);

           ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {

                if (!userId.contains(resultSet.getInt("user_id"))) {
                    users.add(AddUser(resultSet));
                    userId.add(resultSet.getInt("user_id"));
                    createdChatId.clear();
                    userWriteChatsId.clear();
                }

                if (!createdChatId.contains(resultSet.getInt(4))) {
                    users.get(users.size()-1).getCreatedChats().add(AddCreatedChatroom(resultSet));
                    createdChatId.add(resultSet.getInt(4));
                }

                if (!userWriteChatsId.contains(resultSet.getInt(9))) {
                    users.get(users.size()-1).getUserWriteChats().add(AddWriteChatroom(resultSet));
                    userWriteChatsId.add(resultSet.getInt(9));
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
        return users;
    }

    private User AddUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("user_id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    /**
     * @param resultSet ResultSet
     * @return Chatroom (который был создан пользователем)
     * @throws SQLException SQLException
     */
    private Chatroom AddCreatedChatroom(ResultSet resultSet) throws SQLException {
        return new Chatroom(
                resultSet.getInt(4),
                resultSet.getString(5),
                null,
                null
        );
    }

    /**
     * @param resultSet ResultSet
     * @return Chatroom (в котором писал пользователь)
     * @throws SQLException SQLException
     */
    private Chatroom AddWriteChatroom(ResultSet resultSet) throws SQLException {
        return new Chatroom(
                resultSet.getInt(12),
                resultSet.getString(13),
                null,
                null
        );
    }
}
