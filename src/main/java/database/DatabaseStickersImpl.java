package database;

import java.sql.*;

public class DatabaseStickersImpl implements Database{
    private Connection conn = null;
    public DatabaseStickersImpl() {
        try {
            conn = initConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Object... objects) throws SQLException {
        String v1 = (String) objects[0];
        String v2 = (String) objects[1];
        String v3 = (String) objects[2];
        Date date = (Date) objects[3];
        Blob blob = (Blob) objects[4];
        PreparedStatement s = conn.prepareStatement(
                "insert into stickers (file_id, " +
                        "author_name, " +
                        "author_link, " +
                        "msg_date, " +
                        "file)" +
                        " values (?,?,?,?,?)");
        s.setString(1, v1);
        s.setString(2, v2);
        s.setString(3, v3);
        s.setDate(4, date);
        s.setBlob(5, blob);
        s.executeUpdate();
    }

    @Override
    public void insertStrings(String... values) throws SQLException {

    }

    @Override
    public void insertDate(Date date) throws SQLException {

    }

    @Override
    public void insertBlob(Blob blob) throws SQLException {

    }

    @Override
    public ResultSet select(int id) throws SQLException {
        PreparedStatement statement =
                conn.prepareStatement("select * from stickers where id = ?");
        statement.setInt(1, id);
        return statement.executeQuery();
    }

    @Override
    public ResultSet selectAll() throws SQLException {
        PreparedStatement statement =
                conn.prepareStatement("select * from stickers");
        return statement.executeQuery();    }

    @Override
    public void update(String... values) {

    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement =
                conn.prepareStatement("delete from stickers where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public void delete(String fileID) throws SQLException {
        PreparedStatement statement =
                conn.prepareStatement("delete from stickers where file_id = ?");
        statement.setString(1, fileID);
        statement.executeUpdate();
    }

    @Override
    public int count() throws SQLException {
        ResultSet resultSet =
                conn.createStatement().executeQuery("SELECT count(file_id) FROM stickers");
        resultSet.next();
        return resultSet.getInt(1);
    }
}
