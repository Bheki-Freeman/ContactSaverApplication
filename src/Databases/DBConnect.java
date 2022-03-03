package Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private String database_url;
    private String database_user, database_pass;
    private String server;
    private Connection conn;

    public DBConnect(){
        server = "localhost";
        database_url = "customer_details";
        database_user = "Freeman";
        database_pass = "PassFreeman18";
    }

    //private connect
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database_url,  database_user, database_pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return  conn;
    }
}
