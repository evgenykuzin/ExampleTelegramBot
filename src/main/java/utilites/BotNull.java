package utilites;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.Serializable;

public class BotNull extends BotApiMethod {

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public Serializable deserializeResponse(String s) throws TelegramApiRequestException {
        return null;
    }

    @Override
    public void validate() throws TelegramApiValidationException {

    }
}
