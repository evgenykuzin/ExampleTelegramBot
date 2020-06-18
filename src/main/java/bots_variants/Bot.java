package bots_variants;

import commands.Buttons;
import commands.Command;
import commands.CommandManager;
import database.DatabaseManager;
import files.Stickers;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utilites.Entry;

import java.io.FileInputStream;
import java.io.IOException;
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
        // setButtons(new SendMessage());
        // setInline();
        loadProps();
        System.out.println(botName);
        commandManager = new CommandManager(this);
        buttons = new Buttons();
        buttons.createUserKeyboard(commandManager);
        stickers = new ArrayList<>();
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
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String userName = message.getFrom().getUserName();
        if (message.hasText()) {
            System.out.println("userName : '" + userName + "' write : '" + message.getText() + "'");
            if (message.getText().equals("del")) {
                String folder = "C:\\Users\\JekaJops\\IntelliJIDEAProjects\\ExampleTelegramBot\\src\\main\\java\\files\\resources\\img\\images.txt";
                //MyFiles.deleteString(commandManager.flashPhoto, folder);
                DatabaseManager.deleteMeme(commandManager.flashPhoto);
                sendMsg(message, "Спасибо! мем удален.");
            }

        }
        if (message.hasSticker()) {
            Sticker sticker = message.getSticker();
            Blob blob = null;
            DatabaseManager.addStickerEntry(new Entry(
                    sticker.getFileId(),
                    userName,
                    "t.me//" +userName,
                    new Date(message.getDate()),
                    blob,
                    String.valueOf(message.getChatId())
            ));
            System.out.println(userName + " added sticker");
            SendSticker sendSticker = new SendSticker();
            sendSticker.setChatId(EVGENY_KUZIN_ID);
            sendSticker.setSticker(sticker.getFileId());
            try {
                sendMsg(Long.parseLong(EVGENY_KUZIN_ID), "User " + userName + " send this:");
                execute(sendSticker);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        if (message.hasPhoto()) {
            List<PhotoSize> photoSet = message.getPhoto();
            for (PhotoSize photo : photoSet) {
                Blob blob = null;
                DatabaseManager.addMemeEntry(new Entry(
                        photo.getFileId(),
                        userName,
                        "t.me//" +userName,
                        new Date(message.getDate()),
                        blob,
                        String.valueOf(message.getChatId())
                ));
                System.out.println(userName + " added image");
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(EVGENY_KUZIN_ID);
                sendPhoto.setPhoto(photo.getFileId());
                try {
                    sendMsg(Long.parseLong(EVGENY_KUZIN_ID), "User " + userName + " send this:");
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        if (message.hasVideo()) {
            Video video = message.getVideo();
            Blob blob = null;
            DatabaseManager.addVideoEntry(new Entry(
                    video.getFileId(),
                    userName,
                    "t.me//" +userName,
                    new Date(message.getDate()),
                    blob,
                    String.valueOf(message.getChatId())
            ));
            System.out.println(userName + " added video");
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(EVGENY_KUZIN_ID);
            sendVideo.setVideo(video.getFileId());
            try {
                sendMsg(Long.parseLong(EVGENY_KUZIN_ID), "User " + userName + " send this:");
                execute(sendVideo);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        SendMessage sendButtons = new SendMessage();
        sendButtons.setChatId(message.getChatId().
                toString());
        sendButtons.setReplyMarkup(buttons.getReplyKeyboard());
        sendButtons.setText(".");
        try {
            if (message.hasText()) {
                if (message.getText().contains("start")) {
                    System.out.println(userName + " connected.");
                    sendMsg(message, "wellcome, " + userName + "!");
                    execute(sendButtons);
                }
                for (Command command : commandManager.getCommands()) {
                    if (command.isName(message.getText())) {
                        execute(command.action(message));
                        execute(sendButtons);
                    }
                }
            }

        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendMsg(long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    public synchronized void sendMsg(Message message, String s) {
        sendMsg(message.getChatId(), s);
    }

    public synchronized void sendSticker(Message message, int id) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(message.getChatId().toString());
        sendSticker.setSticker(Stickers.sticker1);
        try {
            execute(sendSticker);
        } catch (TelegramApiException e) {
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    public static synchronized ReplyKeyboardMarkup getButtons() {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        KeyboardButton hellowBtn = new KeyboardButton("Привет");

        keyboardFirstRow.add(hellowBtn);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        KeyboardButton helpBtn = new KeyboardButton("Помощь");
        keyboardSecondRow.add(helpBtn);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        KeyboardButton fuckBtn = new KeyboardButton("иди нахуй");
        keyboardThirdRow.add(fuckBtn);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
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
