package kz.tiger.zone.tigerzoneshopbot.bot.service;

import kz.tiger.zone.tigerzoneshopbot.bot.command.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.service.category.TelegramCategoryService;
import kz.tiger.zone.tigerzoneshopbot.bot.service.profile.TelegramProfileService;
import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class TelegramGeneralServiceImpl implements TelegramGeneralService {
    private static final Map<String, CallbackCommandType> menu = Map.of(
            "🏪 Магазин", CallbackCommandType.SHOP,
            "👤 Профиль", CallbackCommandType.PROFILE,
            "⁉️ FAQ", CallbackCommandType.FAQ,
            "🔐 Гарантии", CallbackCommandType.GUARANTEE,
            "📃 Отзывы", CallbackCommandType.REVIEWS,
            "📞 Поддержка", CallbackCommandType.SUPPORT
    );
    private final TelegramCategoryService categoryService;
    private final TelegramProfileService profileService;


    public TelegramGeneralServiceImpl(TelegramCategoryService categoryService,
                                      TelegramProfileService profileService) {
        this.profileService = profileService;
        this.categoryService = categoryService;
    }

    public void sendMenu(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withMenu(menu);
        SendMessage message = SendMessage.builder()
                .chatId(profile.getChatId())
                .text("Меню")
                .replyMarkup(keyboard)
                .build();

        var sentMessage = sender.execute(message);
        Integer sentMessageId = sentMessage.getMessageId();
        profileService.saveLastMessageId(profile.getChatId(), sentMessageId);
    }

    @Override
    public void sendFAQ(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withBackToMenu();
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("FAQ")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    @Override
    public void sendGuarantee(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withBackToMenu();
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("Гарантии")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    @Override
    public void sendReview(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withBackToMenu();
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("Отзывы")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    @Override
    public void sendSupport(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withBackToMenu();
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("Поддержка")
                .parseMode("Markdown")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    public void sendBackEditMenu(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withMenu(menu);
        EditMessageText message = EditMessageText.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .text("Меню")
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    @Override
    public void sendCategories(Profile profile, AbsSender sender) throws TelegramApiException {
        categoryService.sendCategories(profile, sender);
    }

    @Override
    public void sendItemsInCategory(Profile profile, Integer categoryId, AbsSender sender) throws TelegramApiException {
        categoryService.sendItemsInCategory(profile, categoryId, sender);
    }

    public Profile registerUser(Long chatId, String username) {
        return profileService.registerUser(chatId, username);
    }

    public void sendProfile(Profile profile, AbsSender sender) throws TelegramApiException {
        profileService.sendProfile(profile, sender);
    }
}
