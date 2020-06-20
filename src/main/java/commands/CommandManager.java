package commands;

import bots_variants.AbstractBot;
import bots_variants.Bot;
import database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utilites.BotNull;

import java.util.ArrayList;
import java.util.Random;

public class CommandManager {
    private ArrayList<Command> commands;
    private Buttons buttons;
    private Bot bot;
    public String flashPhoto = "";
    public final static String HELP_TEXT = "Ты можешь отправить мне фото, видео или стикер и я сохраню это в своей базе." +
            " Любому человеку я могу рандомно отправить это фото/видео/стикер из своей базы по соответствующим командам.";

    public CommandManager(Bot bot) {
        this.bot = bot;
        commands = new ArrayList<>();
        buttons = new Buttons();
        buttons.createUserKeyboard(this);
        addCommand(new Command("Помощь", HELP_TEXT));

        addCommand(new Command("random_meme", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                int randInt = new Random().nextInt(DatabaseManager.countMemes());
                String image = DatabaseManager.getMemeEntry(randInt).getFileId();
                bot.sendPhoto(message.getChatId(), image, null);
                return new BotNull();
            }
        }));

        addCommand(new Command("random_video", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                int randInt = new Random().nextInt(DatabaseManager.countVideos());
                String video = DatabaseManager.getVideoEntry(randInt).getFileId();
                bot.sendVideo(message.getChatId(), video, null);
                return new BotNull();
            }
        }));

        addCommand(new Command("random_sticker", new Command.ActionSetter() {
            @Override
            public BotApiMethod action(Message message) {
                int randInt = new Random().nextInt(DatabaseManager.countStickers());
                String sticker = DatabaseManager.getStickerEntry(randInt).getFileId();
                bot.sendSticker(message.getChatId(), sticker, null);
                return new BotNull();
            }
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

    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

}
