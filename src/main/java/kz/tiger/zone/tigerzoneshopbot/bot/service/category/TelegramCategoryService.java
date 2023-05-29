package kz.tiger.zone.tigerzoneshopbot.bot.service.category;

import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramCategoryService {
    void sendCategories(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendItemsInCategory(Profile profile, Integer categoryId, AbsSender sender) throws TelegramApiException;
}
