package kz.tiger.zone.tigerzoneshopbot.bot.service.menu;

import kz.tiger.zone.tigerzoneshopbot.bot.command.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class TelegramMenuServiceImpl implements TelegramMenuService {
    private static final Map<String, CallbackCommandType> menu = Map.of(
            "Магазин", CallbackCommandType.SHOP,
            "Профиль", CallbackCommandType.PROFILE,
            "FAQ", CallbackCommandType.FAQ,
            "Гарантии", CallbackCommandType.GUARANTEE,
            "Отзывы", CallbackCommandType.REVIEWS,
            "Поддержка", CallbackCommandType.SUPPORT
    );

    @Override
    public void sendMenu(String chatId, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withMenu(menu);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Меню")
                .replyMarkup(keyboard)
                .build();

        sender.execute(message);
    }
}
