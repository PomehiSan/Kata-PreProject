package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static String url = null;
    private static String name = null;
    private static String password = null;

    private static boolean initConnectionData() {
        try (FileInputStream file = new FileInputStream("src/main/resources/dbconfig.properties")) {
            Properties prop = new Properties();
            prop.load(file);
            url = prop.getProperty("db.host");
            name = prop.getProperty("db.name");
            password = prop.getProperty("db.password");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Connection getConnection() {
        if (initConnectionData()) {
            try {
                return DriverManager.getConnection(url, name, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        if (initConnectionData()) {
            Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
            Configuration config = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, url);
            settings.put(Environment.USER, name);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            config.setProperties(settings);
            config.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();
            return config.buildSessionFactory(serviceRegistry);
        }
        return null;
    }
}
