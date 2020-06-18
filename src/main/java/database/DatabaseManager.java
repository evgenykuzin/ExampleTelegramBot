package database;

import utilites.Entry;

import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static Database databaseMemes = new DatabaseMemesImpl();
    private static Database databaseStickers = new DatabaseStickersImpl();
    private static Database databaseVideos = new DatabaseVideosImpl();

//    public User getUser(int id) throws SQLException {
//        User user = null;
//        ResultSet resultSet = databaseManager.select("Users", "id = " + id);
//        while (resultSet.next()) {
//            String name = resultSet.getString("name");
//            String pass = resultSet.getString("password");
//            Blob blob = resultSet.getBlob("img");
//            user = new User(name, pass, blob.toString());
//        }
//        return user;
//    }

    public static void addMemeEntry(Entry entry) {
        try {
            databaseMemes.insert(entry.getFileId(),
                    entry.getAuthorName(),
                    entry.getAuthorLink(),
                    entry.getMsgDate(),
                    entry.getFileBlob(),
                    entry.getChatId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addStickerEntry(Entry entry) {
        try {
            databaseStickers.insert(
                    entry.getFileId(),
                    entry.getAuthorName(),
                    entry.getAuthorLink(),
                    entry.getMsgDate(),
                    entry.getFileBlob(),
                    entry.getChatId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addVideoEntry(Entry entry) {
        try {
            databaseVideos.insert(
                    entry.getFileId(),
                    entry.getAuthorName(),
                    entry.getAuthorLink(),
                    entry.getMsgDate(),
                    entry.getFileBlob(),
                    entry.getChatId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Entry getMemeEntry(int id) {
        ResultSet resultSet;
        Entry entry = null;
        try {
            resultSet = databaseMemes.select(id);
            entry = getEntry(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entry;
    }

    public static Entry getStickerEntry(int id) {
        ResultSet resultSet;
        Entry entry = null;
        try {
            resultSet = databaseStickers.select(id);
            entry = getEntry(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entry;
    }

    public static Entry getVideoEntry(int id) {
        ResultSet resultSet;
        Entry entry = null;
        try {
            resultSet = databaseVideos.select(id);
            entry = getEntry(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entry;
    }

    private static Entry getEntry(ResultSet resultSet) throws SQLException {
        Entry entry = null;
        while (resultSet.next()) {
            String fileId = resultSet.getString("file_id");
            String authorName = resultSet.getString("author_name");
            String authorLink = resultSet.getString("author_link");
            Date msgDate = resultSet.getDate("msg_date");
            Blob fileBlob = resultSet.getBlob("file");
            String chatId = resultSet.getString("chat_id");
            entry = new Entry(fileId, authorName, authorLink, msgDate, fileBlob, chatId);
        }
        return entry;
    }

    public static void deleteMeme(String fileId) {
        try {
            databaseMemes.delete(fileId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int countMemes() {
        try {
            return databaseMemes.count();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static int countStickers() {
        try {
            return databaseStickers.count();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static int countVideos() {
        try {
            return databaseVideos.count();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

}
