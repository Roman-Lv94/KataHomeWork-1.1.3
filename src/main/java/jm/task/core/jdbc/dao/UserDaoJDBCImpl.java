package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(64), " +
                "lastName VARCHAR(64), " +
                "age TINYINT)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя в базу данных");
            Logger.getLogger(UserDaoJDBCImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователей из базы данных", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении в базу данных", e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            System.out.println("User c id" + id + "удален из базы данных");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалениии пользователя из базы данных", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении списка пользователей из базы данных", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("База данных была очищена");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при очистке базы данных", e);
        }
    }
}
