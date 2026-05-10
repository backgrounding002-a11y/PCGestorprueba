package com.pcgestor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private static Connection connection = null;
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                InputStream input = DatabaseConnection.class.getClassLoader()
                        .getResourceAsStream("database.properties");
                
                if (input == null) {
                    throw new RuntimeException("No se pudo encontrar database.properties");
                }
                
                props.load(input);
                
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                throw new RuntimeException("Error al conectar con la base de datos", e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }
    }
}
