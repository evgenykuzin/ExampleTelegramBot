package bots_variants;

import commands.Buttons;
import commands.Command;
import commands.CommandManager;
import database.DatabaseManager;
import files.Utils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utilites.Entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot implements AbstractBot {

    private CommandManager commandManager;
    private Buttons buttons;
    private ArrayList<String> stickers;
    private static String EVGENY_KUZIN_ID = "328018558";
    private Properties botProps;
    private String botName;
    private String botToken;

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public Bot() {
        loadProps();
        System.out.println(botName);
        commandManager = new CommandManager(this);
        buttons = new Buttons();
        buttons.createUserKeyboard(commandManager);
        System.out.println("bot run!");
    }

    private void loadProps() {
        try {
            String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            String appConfigPath = rootPath + "properties/bot.properties";
            botProps = new Properties();
            botProps.load(new FileInputStream(appConfigPath));
            botName = botProps.getProperty("bot.name");
            botToken = botProps.getProperty("bot.token");
        } catch (Exception e) {
            e.printStackTrace();
            botName = "example_kuzin_bot";
            botToken = "828485060:AAFX2dLbpFzchsyufap_rrQEGKQVMsjjptQ";
        }
    }

    private void onFile(Message message, String userName) {
        class CountObj {
            private int symb = 0;
            private int words = 0;

            public int getSymb() {
                return symb;
            }

            public void incSymb(int c) {
                symb += c;
            }

            public int getWords() {
                return words;
            }

            public void incWords(int c) {
                words += c;
            }
        }
        CountObj countObj = new CountObj();
        if (message.hasDocument()) {
            Document document = message.getDocument();
            String filePath = document.getFileName();
            String url = "https://api.telegram.org/bot" + botToken + "/getFile?file_id=" + document.getFileId();
            //File file = Utils.loadFileFromInternet(url, new File(filePath));
            File file = null;
            try {
                file = Utils.loadFileFromInternet(document.getFileName(), document.getFileId(), botToken);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Path path = file.toPath();
            List<String> lines = null;
            try {
                lines = Files.readAllLines(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert lines != null;
            StringBuilder sp = new StringBuilder();
            lines.forEach(sp::append);
            System.out.println(sp.toString());
            lines.forEach(s -> {
                countObj.incSymb(s.replace(" ", "").length());
                countObj.incWords(s.split(" ").length);
            });
            String text = "символов: " + countObj.getSymb() + "\nслов: " + countObj.getWords();
            sendText(message.getChatId(), text, null);
        }
    }

    private void onPhoto(Message message, String userName) {
        if (message.hasPhoto()) {
            List<PhotoSize> photoSet = message.getPhoto();
            for (PhotoSize photo : photoSet) {
                Blob blob = null;
                DatabaseManager.addMemeEntry(new Entry(
                        photo.getFileId(),
                        userName,
                        "t.me//" + userName,
                        new Date(message.getDate()),
                        blob,
                        String.valueOf(message.getChatId())
                ));
                System.out.println(userName + " added image");
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(EVGENY_KUZIN_ID);
                sendPhoto.setPhoto(photo.getFileId());
                try {
                    sendText(Long.parseLong(EVGENY_KUZIN_ID), "User " + userName + " send this:", null);
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onSticker(Message message, String userName) {
        if (message.hasSticker()) {
            Sticker sticker = message.getSticker();
            Blob blob = null;
            DatabaseManager.addStickerEntry(new Entry(
                    sticker.getFileId(),
                    userName,
                    "t.me//" + userName,
                    new Date(message.getDate()),
                    blob,
                    String.valueOf(message.getChatId())
            ));
            System.out.println(userName + " added sticker");
            SendSticker sendSticker = new SendSticker();
            sendSticker.setChatId(EVGENY_KUZIN_ID);
            sendSticker.setSticker(sticker.getFileId());
            try {
                sendText(Long.parseLong(EVGENY_KUZIN_ID), "User " + userName + " send this:", null);
                execute(sendSticker);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    private void onVideo(Message message, String userName) {
        if (message.hasVideo()) {
            Video video = message.getVideo();
            Blob blob = null;
            DatabaseManager.addVideoEntry(new Entry(
                    video.getFileId(),
                    userName,
                    "t.me//" + userName,
                    new Date(message.getDate()),
                    blob,
                    String.valueOf(message.getChatId())
            ));
            System.out.println(userName + " added video");
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(EVGENY_KUZIN_ID);
            sendVideo.setVideo(video.getFileId());
            try {
                sendText(Long.parseLong(EVGENY_KUZIN_ID), "User " + userName + " send this:", null);
                execute(sendVideo);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void onText(Message message, String userName) {
        if (message.hasText()) {
            System.out.println("userName : '" + userName + "' write : '" + message.getText() + "'");
            if (message.getText().equals("del")) {
                DatabaseManager.deleteMeme(commandManager.flashPhoto);
                sendText(message.getChatId(), "Спасибо! мем удален.", null);
            }
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        sendText(message.getChatId(), "test heroku server", null);
        String userName = message.getFrom().getUserName();
        onFile(message, userName);
        onText(message, userName);
        onSticker(message, userName);
        onPhoto(message, userName);
        onVideo(message, userName);
        try {
            if (message.hasText()) {
                if (message.getText().contains("start")) {
                    System.out.println(userName + " connected.");
                    sendText(message.getChatId(), "wellcome, " + userName + "!", null);
                    sendText(message.getChatId(), CommandManager.HELP_TEXT, null);
                }
                for (Command command : commandManager.getCommands()) {
                    if (command.isName(message.getText())) {
                        execute(command.action(message));
                    }
                }
            }
        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendText(long chatId, String s, Buttons buttons) {
        if (buttons == null) buttons = this.buttons;
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(buttons.getReplyKeyboard());
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    public synchronized void sendSticker(long chatId, String sticker, Buttons buttons) {
        if (buttons == null) buttons = this.buttons;
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(chatId);
        sendSticker.setReplyMarkup(buttons.getReplyKeyboard());
        sendSticker.setSticker(sticker);
        try {
            execute(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    public synchronized void sendPhoto(long chatId, String photo, Buttons buttons) {
        if (buttons == null) buttons = this.buttons;
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setReplyMarkup(buttons.getReplyKeyboard());
        sendPhoto.setPhoto(photo);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    public synchronized void sendVideo(long chatId, String video, Buttons buttons) {
        if (buttons == null) buttons = this.buttons;
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        sendVideo.setReplyMarkup(buttons.getReplyKeyboard());
        sendVideo.setVideo(video);
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    private void setInline() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("Кнопка").setCallbackData(17 + ""));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
    }

    public synchronized void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        answerCallbackQuery(answer.getCallbackQueryId(), answer.getText());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod executeMessage(Object sendObject) throws TelegramApiException {
        SendMessage sendMessage = (SendMessage) sendObject;
        execute(sendMessage);
        return null;
    }

}
