import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;


public class Bot2  extends AbilityBot{
    protected Bot2(String botToken, String botUsername, DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
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
}
