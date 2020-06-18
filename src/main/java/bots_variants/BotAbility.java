package bots_variants;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

import static constants.R.BOT_TOKEN;
import static constants.R.BOT_USERNAME;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;


public class BotAbility extends AbilityBot {
    public BotAbility(String botToken, String botUsername, DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
    }

    public BotAbility() {
        super(BOT_TOKEN, BOT_USERNAME);
    }


    @Override
    public int creatorId() {
        return 12345678;
    }
    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        System.out.println(updates.get(0).getMessage().toString());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("");
        sendMessage.setChatId(updates.get(0).getMessage().getChatId());
        try {
            sayHelloWorld();
            execute(sendMessage);
        } catch (TelegramApiException tae) {
            tae.printStackTrace();
        }
    }
}
