package database;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class DatabaseMemesImpl implements Database{
    private Connection conn = null;
    private String table = "users";
    public DatabaseMemesImpl() {
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
        String cid = (String) objects[5];
        PreparedStatement s = conn.prepareStatement(
                "insert into memes (file_id, " +
                        "author_name, " +
                        "author_link, " +
                        "msg_date, " +
                        "file," +
                        "chat_id)" +
                        " values (?,?,?,?,?,?)");
        s.setString(1, v1);
        s.setString(2, v2);
        s.setString(3, v3);
        s.setDate(4, date);
        s.setBlob(5, blob);
        s.setString(6, cid);
        s.executeUpdate();
    }

    @Override
    public void insertStrings(String... values) throws SQLException {
        String v1 = values[0];
        String v2 = values[1];
        String v3 = values[2];
        PreparedStatement s = conn.prepareStatement(
                "insert into memes (file_id, author_name, author_link) values (?,?,?)");
        s.setString(1, v1);
        s.setString(2, v2);
        s.setString(3, v3);

    }

    @Override
    public void insertDate(Date date) throws SQLException {
        PreparedStatement s = conn.prepareStatement("insert into memes (msg_date) value (?)");
        s.setDate(1, date);
        s.executeUpdate();
    }

    @Override
    public void insertBlob(Blob blob) throws SQLException {
        PreparedStatement s = conn.prepareStatement("insert into memes (file) value (?)");
        s.setBlob(1, blob);
        s.executeUpdate();
    }

    public static Blob createBlob(File file, Connection conn) throws SQLException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Blob blob = conn.createBlob();
        try (OutputStream outputStream = blob.setBinaryStream(1)) {
            if (image == null) {
                System.out.println("blob - null");
            } else ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blob;
    }
    @Override
    public ResultSet select(int id) throws SQLException{
        PreparedStatement statement =
                conn.prepareStatement("select * from memes where id = ?");
        statement.setInt(1, id);
        return statement.executeQuery();
    }

    @Override
    public ResultSet selectAll() throws SQLException{
        PreparedStatement statement =
                conn.prepareStatement("select * from memes");
        return statement.executeQuery();
    }

    @Override
    public void update(String... values) throws SQLException{

    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement =
                conn.prepareStatement("delete from memes where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public void delete(String fileID) throws SQLException {
        PreparedStatement statement =
                conn.prepareStatement("delete from memes where file_id = ?");
        statement.setString(1, fileID);
        statement.executeUpdate();
    }

    @Override
    public int count() throws SQLException {
        ResultSet resultSet =
                conn.createStatement().executeQuery("SELECT count(file_id) FROM memes");
        resultSet.next();
        return resultSet.getInt(1);
    }
}
