package kz.tiger.zone.tigerzoneshopbot.bot.service;

import kz.tiger.zone.tigerzoneshopbot.bot.enums.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.enums.MessageType;
import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import kz.tiger.zone.tigerzoneshopbot.service.category.ShopCategoryService;
import kz.tiger.zone.tigerzoneshopbot.service.user.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
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
    private final UserService userService;
    private final ShopCategoryService categoryService;

    public TelegramGeneralServiceImpl(ShopCategoryService categoryService,
                                      UserService userService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public Profile registerUser(Long chatId, String userName) {
        return userService.registerUser(new Profile(chatId, userName));
    }

    public void sendMenu(Profile profile, AbsSender sender, MessageType messageType) throws TelegramApiException {
        SendPhoto menuMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/menu.png")))
                .replyMarkup(KeyboardFactory.withMenu(menu))
                .caption("Меню")
                .build();
        if (messageType == MessageType.CALLBACK) {
            deleteMessageId(profile, sender);
        }
        var savedMessage = sender.execute(menuMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendFAQ(Profile profile, AbsSender sender) throws TelegramApiException {
        SendPhoto faqMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/faq.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption("FAQ")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(faqMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendGuarantee(Profile profile, AbsSender sender) throws TelegramApiException {
        SendPhoto guaranteeMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/guarantees.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption("Гарантии")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(guaranteeMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendReview(Profile profile, AbsSender sender) throws TelegramApiException {
        SendPhoto guaranteeMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/reviews.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption("Отзывы")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(guaranteeMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendSupport(Profile profile, AbsSender sender) throws TelegramApiException {
        SendPhoto supportMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/support.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption("Поддержка")
                .parseMode("Markdown")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(supportMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendProfile(Profile profile, AbsSender sender) throws TelegramApiException {
        final String profileText = String.format("""
                Профиль:
                Пользователь: %s
                """, profile.getUsername());
        SendPhoto profileMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/profile.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption(profileText)
                .parseMode("Markdown")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(profileMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    public void sendBackEditMenu(Profile profile, AbsSender sender) throws TelegramApiException {
        SendPhoto menuMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/menu.png")))
                .replyMarkup(KeyboardFactory.withMenu(menu))
                .caption("Меню")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(menuMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendCategories(Profile profile, AbsSender sender) throws TelegramApiException {
        SendPhoto supportMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/shop.png")))
                .replyMarkup(KeyboardFactory.withCategories(categoryService.getAllEnabledCategories()))
                .caption("Shop")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(supportMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendItemsInCategory(Profile profile, Integer categoryId, AbsSender sender) throws TelegramApiException {
        SendPhoto supportMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/shop.png")))
                .replyMarkup(KeyboardFactory.withProducts(categoryService.getAllEnabledItems(categoryId)))
                .caption("Shop")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(supportMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    private void deleteMessageId(Profile profile, AbsSender sender) throws TelegramApiException {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .build();
        sender.execute(deleteMessage);
    }

    private File readFileFromResources(String filePath) {
        return new File("src/main/resources/" + filePath);
    }
}
