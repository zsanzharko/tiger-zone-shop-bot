package kz.tiger.zone.tigerzoneshopbot.bot.service.category;

import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import kz.tiger.zone.tigerzoneshopbot.service.category.ShopCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramCategoryServiceImpl implements TelegramCategoryService {
    private final ShopCategoryService categoryService;

    @Override
    public void sendCategories(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withCategories(categoryService.getAllEnabledCategories());
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("Shop")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    @Override
    public void sendItemsInCategory(Profile profile, Integer categoryId, AbsSender sender)
            throws TelegramApiException {
        var keyboard = KeyboardFactory.withProducts(categoryService.getAllEnabledItems(categoryId));
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("Products")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }
}
