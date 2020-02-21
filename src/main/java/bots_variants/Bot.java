package bots_variants;

import commands.Buttons;
import commands.Command;
import commands.CommandManager;
import files.Files;
import files.Stickers;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static constants.R.BOT_TOKEN;
import static constants.R.BOT_USERNAME;

public class Bot extends TelegramLongPollingBot implements AbstractBot {

    private CommandManager commandManager;
    private Buttons buttons;
    private ArrayList<String> stickers;

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public Bot() {
        // setButtons(new SendMessage());
        // setInline();

        commandManager = new CommandManager(this);
        buttons = new Buttons();
        buttons.createUserKeyboard(commandManager);
        stickers = new ArrayList<>();
        System.out.println(Files.resources);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String userName = message.getFrom().getUserName();
        if (message.hasSticker()) {
            String folder = "stickers\\ywy.txt";
            ArrayList<String> stickers = Files.readFile(folder);
            String sticker = message.getSticker().getFileId();
            if (!stickers.contains(sticker)) {
                int i = stickers.size() + 1;
                Files.writeToFile(folder, "sticker" + i + " : " + sticker);
                System.out.println(userName + " added sticker");
            }
        }
        if (message.hasPhoto()) {
            String folder = "img\\images.txt";
            ArrayList<String> photos = Files.readFile(folder);
            int i = photos.size() + 1;
            List<PhotoSize> photoSet = message.getPhoto();
            for (PhotoSize photo : photoSet) {
                if (!photos.contains(photo.getFileId())) {
                    Files.writeToFile(folder, "image" + i + " : " + photo.getFileId());
                    System.out.println(userName + " added image");
                    i++;
                }
            }
        }
        if (message.hasVideo()) {
            String folder = "videos\\vid.txt";
            ArrayList<String> videos = Files.readFile(folder);
            int i = videos.size() + 1;
            Video video = message.getVideo();
            if (!videos.contains(video.getFileId())) {
                Files.writeToFile(folder, "video" + i + " : " + video.getFileId());
                System.out.println(userName + " added video");
                i++;
            }
        }
        SendMessage sendStart = new SendMessage();
        sendStart.setChatId(message.getChatId().toString());
        sendStart.setReplyMarkup(buttons.getReplyKeyboard());
        sendStart.setText("wellcome, " + userName + "!");
        try {
            if (message.hasText()) {
                if (message.getText().contains("start")) {
                    System.out.println(userName + " connected.");
                    execute(sendStart);
                }
                for (Command command : commandManager.getCommands()) {
                    if (command.isName(message.getText())) {
                        execute(command.action(message));
                    }
                }
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //log(Level.SEVERE, "Exception: ", e.toString());
        }
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
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public BotApiMethod executeMessage(Object sendObject) throws TelegramApiException {
        SendMessage sendMessage = (SendMessage) sendObject;
        execute(sendMessage);
        return null;
    }
}
