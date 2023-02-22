package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                "(ID int not null auto_increment," +
                "NAME VARCHAR(50)," +
                "LASTNAME VARCHAR(50)," +
                "AGE TINYINT," +
                "PRIMARY KEY (ID))";
        System.out.println("--------------------------------------------");

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        System.out.println("Создана таблица USERS с колонками ID, NAME, LAST NAME, AGE.");
    }

    public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS USERS";
        System.out.println("--------------------------------------------");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        System.out.println("Таблица USERS удалена.");
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        User user = new User(name, lastName, age);
        System.out.println("--------------------------------------------");
        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        System.out.println("User с именем " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM USERS WHERE ID=?";
        System.out.println("--------------------------------------------");

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        System.out.println("User с ID " + id + " удален из базы данных.");
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> usersArrayList = new ArrayList<>();


        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        System.out.println("--------------------------------------------");
        System.out.println("Содержание таблицы USERS:");
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                System.out.println(user);

                usersArrayList.add(user);
            }
        } catch (SQLException e) {
            throw e;
        }
        return usersArrayList;
    }


    public void cleanUsersTable() throws SQLException {
        String sql = "DELETE FROM USERS";
        System.out.println("--------------------------------------------");

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        System.out.println("Таблица USERS очищена.");
    }
}
