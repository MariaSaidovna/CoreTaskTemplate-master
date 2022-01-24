package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getMySQLConnection();

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS userstable (id bigint AUTO_INCREMENT NOT NULL, name varchar(100) NOT NULL, lastName varchar(100) NOT NULL, age tinyint(3) NOT NULL, primary key (id))");
           connection.commit();
        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement.executeUpdate("DROP TABLE IF EXISTS userstable");
            connection.commit();

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userstable (name, lastName, age) VALUES (?,?,?)")) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userstable WHERE id = ?")) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = connection.createStatement()) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            List<User> list = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT id, name, lastName, age FROM userstable");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                list.add(user);
            }
            connection.commit();
            return list;

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement.executeUpdate("DELETE FROM userstable");
            connection.commit();

        } catch (SQLException e) {
            Util.rollbackQuietly(connection);
            e.printStackTrace();
        }
    }
}
