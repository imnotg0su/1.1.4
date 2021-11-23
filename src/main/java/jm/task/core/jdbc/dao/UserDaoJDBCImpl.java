package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        String s = "CREATE TABLE User (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR (45), lastName VARCHAR (45), age TINYINT)";
        try(PreparedStatement ps = new Util().getConnection().prepareStatement(s)) {
            ps.executeUpdate(s);
            System.out.println("Таблица создана.");
        } catch (SQLException e) {
            System.err.println("Ошибка в создании таблицы" + e);
        }
    }

    public void dropUsersTable() {
        try(PreparedStatement ps = new Util().getConnection().prepareStatement("DROP TABLE IF EXISTS User")) {
            ps.executeUpdate("DROP TABLE IF EXISTS User");
            System.out.println("Таблица удалена.");
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = new Util().getConnection().prepareStatement("INSERT INTO User (name, lastName, age) VALUES (?,?,?)"))  {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Пользователь не сохранён" + e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = new Util().getConnection().prepareStatement("DELETE FROM User WHERE id = ?")) {
            ps.setLong(1, id);
            ps.execute();
            System.out.println("Пользователь удалён.");
        } catch (SQLException e) {
            System.err.println("Пользователь не удалён" + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement ps = new Util().getConnection().prepareStatement("SELECT * FROM User")) {
            ResultSet rs = ps.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                list.add(user);

            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения списка пользователей" + e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement ps = new Util().getConnection().prepareStatement("DELETE FROM User")) {
            ps.execute();
            System.out.println("Таблица очищена.");
        } catch (SQLException e) {
            System.out.println("Ошибка очищения таблицы" + e);
        }
    }
}
