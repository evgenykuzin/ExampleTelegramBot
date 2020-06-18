package commands;

import database.Database;
import database.DatabaseManager;
import files.MyFiles;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utilites.BotNull;

import java.util.ArrayList;
import java.util.Random;

public class CommandManager {
    private ArrayList<Command> commands;
    private Buttons buttons;

    public CommandManager(AbsSender abstractBot) {
        commands = new ArrayList<>();
        buttons = new Buttons();
        buttons.createMarksKeyboard();
        addCommand(new Command("Помощь", "Ты можешь отправить мне фото, видео или стикер и я сохраню это в своей базе." +
                " Любому человеку я могу рандомно отправить это фото/видео/стикер из своей базы по соответствующим командам."));

        addCommand(new Command("random_meme", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                String chatId = message.getChatId().toString();
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                buttons.createMarksKeyboard();
                //sendPhoto.setReplyMarkup(buttons.getInlineKeyboard());
                int randInt = new Random().nextInt(DatabaseManager.countMemes());
                String image = DatabaseManager.getMemeEntry(randInt).getFileId();
                System.out.println(image);
                flashPhoto = image;
                sendPhoto.setPhoto(image);
                try {
                    abstractBot.execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return new BotNull();
            }

        }));

        addCommand(new Command("random_video", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                String chatId = message.getChatId().toString();
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                int randInt = new Random().nextInt(DatabaseManager.countVideos());
                String video = DatabaseManager.getVideoEntry(randInt).getFileId();
                sendVideo.setVideo(video);
                try {
                    abstractBot.execute(sendVideo);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return new BotNull();
            }

        }));

        addCommand(new Command("random_sticker", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                String chatId = message.getChatId().toString();
                SendSticker sendSticker = new SendSticker();
                sendSticker.setChatId(chatId);
                int randInt = new Random().nextInt(DatabaseManager.countStickers());
                String sticker = DatabaseManager.getStickerEntry(randInt).getFileId();
                sendSticker.setSticker(sticker);
                try {
                    abstractBot.execute(sendSticker);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return new BotNull();
            }
            //return new BotNull();
        }));

    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public boolean contains(String name) {
        for (Command command : commands) {
            if (command.getName().equals(name)) return true;
        }
        return false;
    }

    public SendMessage sendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        return sendMessage;
    }

    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    public String flashPhoto = "";

}
