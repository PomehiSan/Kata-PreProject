package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
}
