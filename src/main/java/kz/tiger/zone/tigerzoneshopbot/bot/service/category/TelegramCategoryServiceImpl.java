package kz.tiger.zone.tigerzoneshopbot.bot.service.category;

import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import kz.tiger.zone.tigerzoneshopbot.service.category.ShopCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramCategoryServiceImpl implements TelegramCategoryService {
    private final ShopCategoryService categoryService;

    @Override
    public void sendCategories(String id, DefaultAbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withCategories(categoryService.getAllEnabledCategories());
        SendMessage message = SendMessage.builder()
                .chatId(id)
                .text("Shop")
                .replyMarkup(keyboard)
                .build();

        sender.execute(message);
    }
}
