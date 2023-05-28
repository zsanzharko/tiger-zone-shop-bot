package kz.tiger.zone.tigerzoneshopbot.bot.service.menu;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramMenuService {

    void sendMenu(String chatId, AbsSender sender) throws TelegramApiException;
}
