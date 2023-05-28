package kz.tiger.zone.tigerzoneshopbot.bot.service.category;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramCategoryService {
    void sendCategories(String id, DefaultAbsSender sender) throws TelegramApiException;
}
