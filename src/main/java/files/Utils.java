package files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.FileHandler;

import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import static constants.R.BOT_TOKEN;

public class Utils {
    public static File loadFileFromInternet(String path){
        File output = null;
       TelegramFileDownloader loader = new TelegramFileDownloader(new Supplier<String>() {
           @Override
           public String get() {
               return BOT_TOKEN;
           }
       });

        try {
           output = loader.downloadFile(Files.file1.getAbsolutePath());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return output;
    }

}
