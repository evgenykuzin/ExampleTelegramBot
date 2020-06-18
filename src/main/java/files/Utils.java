package files;

import java.io.File;
import java.util.function.Supplier;

import bots_variants.Bot;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Utils {
    public static File loadFileFromInternet(String path){
        File output = null;
       TelegramFileDownloader loader = new TelegramFileDownloader(new Supplier<String>() {
           @Override
           public String get() {
               return new Bot().getBotToken();
           }
       });

        try {
           output = loader.downloadFile(MyFiles.file1.getAbsolutePath());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return output;
    }

}
