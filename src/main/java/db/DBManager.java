package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    private static DBManager instance = null;
    private Connection connection = null;


    private static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/webshop",
                    "root",
                    "");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return getInstance().connection;
    }
}
