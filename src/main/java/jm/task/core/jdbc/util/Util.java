package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД


    public static SessionFactory getSessionFactory() {

            try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/usersdb?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }
/*
    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    } */



    public static Connection getMySQLConnection() {

        String connectionURL = "jdbc:mysql://localhost:3306/usersdb?useSSL=false";
        String userName = "root";
        String password = "root";

        Connection conn;

        try {
            conn =  DriverManager.getConnection(connectionURL, userName, password);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void rollbackQuietly(Connection connection) {
        if(connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }


}
