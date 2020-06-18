package database;

import java.sql.*;
import java.util.Objects;

public interface Database {
    default Connection initConnection() throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        String name = "root";
        String pass = "1357";
        Class.forName("com.mysql.jdbc.Driver");
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

    ResultSet selectAll() throws SQLException;

    void update(String... values) throws SQLException;

    void delete(String fileId) throws SQLException;

    int count() throws SQLException;
}
