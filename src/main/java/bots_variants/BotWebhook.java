package bots_variants;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotWebhook extends TelegramWebhookBot implements AbstractBot {
    String botName;
    String botToken;

    public BotWebhook(String botName, String botToken){
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Well, all information looks like noise until you break the code.");
            return sendMessage;
        }
        return null;
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
    public String getBotPath() {
        return ""; //arbitrary path to deliver updates on, username is an example.
    }

    @Override
    public BotApiMethod executeMessage(Object sendObject) throws TelegramApiException {
        return null;
    }



//    @Override
//    public BotApiMethod executeMessage(SendMessage sendMessage) throws TelegramApiException {
//        return sendMessage;
//    }
}
