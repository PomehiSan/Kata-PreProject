package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS user(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), last_name VARCHAR(20), age INT)";
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS user";
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = String.format("INSERT INTO user(name, last_name, age) VALUES ('%s', '%s', '%s')", name, lastName, age);
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = String.format("DELETE FROM user WHERE id = %s", id);
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = String.format("SELECT * FROM user");
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                users.add(new User(result.getString("name"),
                        result.getString("last_name"),
                        result.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = String.format("TRUNCATE TABLE user");
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
