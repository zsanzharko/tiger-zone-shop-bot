package kz.tiger.zone.tigerzoneshopbot.bot.service.profile;

import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramProfileService {

    Profile registerUser(Long chatId, String username);

    void sendProfile(Profile profile, AbsSender sender) throws TelegramApiException;

    void saveLastMessageId(Long chatId, Integer sentMessageId);
}
