package database;

import java.sql.*;
import java.util.Objects;

public interface Database {
    default Connection initConnection() throws ClassNotFoundException {
        String url = "jdbc:mysql://b6fb2f3f67ee5b:62546e31@us-cdbr-east-05.cleardb.net/heroku_7f0a8e6802ba731?reconnect=true";
        String name = "b6fb2f3f67ee5b";
        String pass = "62546e31";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, name, pass);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conn;
    }

    public static final String UsersTableLabel = "Users";

    void insert(Object... objects) throws SQLException;

    void insertStrings(String... values) throws SQLException;

    void insertDate(Date date) throws SQLException;

    void insertBlob(Blob blob) throws SQLException;

    ResultSet select(int id) throws SQLException;

    ResultSet selectRandom() throws SQLException;

    ResultSet selectAll() throws SQLException;

    void update(String... values) throws SQLException;

    void delete(String fileId) throws SQLException;

    int count() throws SQLException;
}
