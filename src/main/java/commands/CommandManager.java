package commands;

import files.Files;
import files.Stickers;
import files.Utils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import utilites.BotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CommandManager {
    private ArrayList<Command> commands;
    private Buttons buttons;
    public CommandManager(AbsSender abstractBot){
        commands = new ArrayList<>();
        buttons = new Buttons();
        buttons.createMarksKeyboard();
        addCommand(new Command("Помощь", "Ты можешь отправить мне фото, видео или стикер и я сохраню это в своей базе." +
                " Любому человеку я могу рандомно отправить это фото/видео/стикер из своей базы по соответствующим командам. "));

        addCommand(new Command("random_meme", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                String chatId = message.getChatId().toString();
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                buttons.createMarksKeyboard();
                //sendPhoto.setReplyMarkup(buttons.getInlineKeyboard());
                ArrayList<String> images = Files.readFile("img\\images.txt");
                while (true) {
                    for (String image : images) {
                        int randInt = new Random().nextInt(images.size());
                        int randInt2 =  new Random().nextInt(images.size());
                        if (randInt == randInt2 && !image.isEmpty()) {
                            sendPhoto.setPhoto(image.split(" : ")[1]);
                            try {
                                abstractBot.execute(sendPhoto);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            return new BotNull();
                        }
                    }
                }
            }
        }));

        addCommand(new Command("random_video", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                String chatId = message.getChatId().toString();
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                ArrayList<String> videos = Files.readFile("videos\\vid.txt");
                while (true) {
                    for (String video : videos) {
                        int randInt = new Random().nextInt(videos.size());
                        int randInt2 =  new Random().nextInt(videos.size());
                        if (randInt == randInt2 && !video.isEmpty()) {
                            sendVideo.setVideo(video.split(" : ")[1]);
                            try {
                                abstractBot.execute(sendVideo);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            return new BotNull();
                        }
                    }
                }
            }
        }));

        addCommand(new Command("random_sticker", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                String chatId = message.getChatId().toString();
                SendSticker sendSticker = new SendSticker();
                sendSticker.setChatId(chatId);
                ArrayList<String> stickers = Files.readFile("stickers\\ywy.txt");
                while (true) {
                    for (String sticker : stickers) {
                        int randInt = new Random().nextInt(stickers.size());
                        int randInt2 =  new Random().nextInt(stickers.size());
                        if (randInt == randInt2 && !sticker.isEmpty()) {
                            sendSticker.setSticker(sticker.split(" : ")[1]);
                            try {
                                abstractBot.execute(sendSticker);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            return new BotNull();
                        }
                    }
                }
                //return new BotNull();
            }
        }));

    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public ArrayList<Command> getCommands(){
        return commands;
    }

    public boolean contains(String name){
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

}
