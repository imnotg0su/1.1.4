package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private final String url = "jdbc:mysql://localhost:3306/1.1.3";
    private final String userName = "tratata";
    private final String password = "tratata";
    private Connection connection;

    public Util () {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Соединение установлено.");
        } catch (SQLException e) {
            System.err.println("Ошибка соединения.");
        }
    }

    public Connection getConnection () {
        return connection;
    }

    private static SessionFactory sessionFactory;

    static {
         Properties properties = new Properties();
         properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/1.1.3");
         properties.setProperty(Environment.USER, "tratata");
         properties.setProperty(Environment.PASS, "tratata");
         properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
         properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
         properties.setProperty(Environment.HBM2DDL_AUTO, "update");
         properties.setProperty(Environment.SHOW_SQL, "true");
         //StandardServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(properties).build();
         //sessionFactory = new MetadataSources(sr);
        try {
            sessionFactory = new Configuration().addProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Нет соединения." + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


