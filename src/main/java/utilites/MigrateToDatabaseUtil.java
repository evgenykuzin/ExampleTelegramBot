package utilites;

import database.Database;
import database.DatabaseManager;
import database.DatabaseMemesImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class MigrateToDatabaseUtil {

    public static void main(String[] args) throws FileNotFoundException, SQLException, ClassNotFoundException {
        migrateMemes();
        migrateStickers();
        migrateVideos();
    }

    public static void migrateMemes() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String memesPath = "C:\\Users\\JekaJops\\IntelliJIDEAProjects\\ExampleTelegramBot\\src\\main\\java\\files\\resources" +
                "\\img\\images.txt";
        Set<Entry> entrySet = getEntrySet(memesPath);
        for (Entry entry : entrySet) {
            System.out.println(entry.toString());
            DatabaseManager.addMemeEntry(entry);
        }
    }

    public static void migrateStickers() throws SQLException, ClassNotFoundException, FileNotFoundException {
        DatabaseManager databaseManager = new DatabaseManager();
        String stickersPath = "C:\\Users\\JekaJops\\IntelliJIDEAProjects\\ExampleTelegramBot\\src\\main\\java\\files\\resources" +
                "\\stickers\\ywy.txt";
        Set<Entry> entrySet = getEntrySet(stickersPath);
        for (Entry entry : entrySet) {
            System.out.println(entry.toString());
            DatabaseManager.addStickerEntry(entry);
        }
    }

    public static void migrateVideos() throws SQLException, ClassNotFoundException, FileNotFoundException {
        DatabaseManager databaseManager = new DatabaseManager();
        String videosPath = "C:\\Users\\JekaJops\\IntelliJIDEAProjects\\ExampleTelegramBot\\src\\main\\java\\files\\resources" +
                "\\videos\\vid.txt";
        Set<Entry> entrySet = getEntrySet(videosPath);
        for (Entry entry : entrySet) {
            System.out.println(entry.toString());
            DatabaseManager.addVideoEntry(entry);
        }
    }

    public static Set<Entry> getEntrySet(String filePath) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
        Stream<String> linesStream = reader.lines();
        Set<Entry> entrySet = new HashSet<>();
        linesStream.forEach(string -> {
            Entry entry = new Entry();
            String[] split = string.split(" : ");
            entry.setFileId(split[1]);
            if (split.length > 2) {
                entry.setAuthorName(split[2]);
            } else {
                entry.setAuthorName("unrecognized");
            }
            entry.setAuthorLink("t.me//" + entry.getAuthorName());
            entry.setMsgDate(new Date(111111));
            entry.setFileBlob(null);
            entrySet.add(entry);
        });

        return entrySet;
    }


}
