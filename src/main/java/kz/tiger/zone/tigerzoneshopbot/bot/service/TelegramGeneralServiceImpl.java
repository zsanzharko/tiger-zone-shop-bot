package kz.tiger.zone.tigerzoneshopbot.bot.service;

import kz.tiger.zone.tigerzoneshopbot.bot.enums.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.enums.MessageType;
import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import kz.tiger.zone.tigerzoneshopbot.service.category.ShopCategoryService;
import kz.tiger.zone.tigerzoneshopbot.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.*;
import java.util.Map;

@Slf4j
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
        final String text = getTextFrom(
                readFileFromResources("text/FAQ.txt"), "FAQ");
        SendPhoto faqMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/faq.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption(text)
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(faqMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendGuarantee(Profile profile, AbsSender sender) throws TelegramApiException {
        final String text = getTextFrom(
                readFileFromResources("text/Guarantee.txt"), "Гарантии");
        SendPhoto guaranteeMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/guarantees.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption(text)
                .parseMode("Markdown")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(guaranteeMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendReview(Profile profile, AbsSender sender) throws TelegramApiException {
        final String text = getTextFrom(
                readFileFromResources("text/Review.txt"), "Отзывы");
        SendPhoto guaranteeMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/reviews.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption(text)
                .parseMode("Markdown")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(guaranteeMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendSupport(Profile profile, AbsSender sender) throws TelegramApiException {
        final String text = getTextFrom(
                readFileFromResources("text/Support.txt"), "Поддержка");
        SendPhoto supportMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(new InputFile(readFileFromResources("image/support.png")))
                .replyMarkup(KeyboardFactory.withBackToMenu())
                .caption(text)
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
                Пользователь: **%s**
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
                .caption("Магазин")
                .build();
        deleteMessageId(profile, sender);
        var savedMessage = sender.execute(supportMessage);
        userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
    }

    @Override
    public void sendCategory(Profile profile, Integer categoryId, AbsSender sender) throws TelegramApiException {
        var products = categoryService.getAllEnabledItems(categoryId);
        var category = categoryService.getCategory(categoryId);
        String mainText = "Магазин";

        SendPhoto categoryMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .photo(resolveInputFile(category.getImageUrl(), "image/shop.png"))
                .replyMarkup(KeyboardFactory.withProducts(products))
                .caption(mainText)
                .build();
        deleteMessageId(profile, sender);
        try {
            var savedMessage = sender.execute(categoryMessage);
            userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
        } catch (TelegramApiRequestException e) {
            if (e.getMessage().contains("wrong remote file identifier specified: Wrong padding in the string")) {
                SendPhoto categoryExceptionMessage = SendPhoto.builder()
                        .chatId(profile.getChatId())
                        .photo(new InputFile(readFileFromResources("image/shop.png")))
                        .replyMarkup(KeyboardFactory.withProducts(products))
                        .caption(mainText)
                        .build();
                var savedMessage = sender.execute(categoryExceptionMessage);
                userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
            }
        }
    }

    @Override
    public void sendProduct(Profile profile, Integer productId, AbsSender sender) throws TelegramApiException {
        var product = categoryService.getProduct(productId);

        final String messageText = String.format("""
                Продукт: %s
                
                Описание: %s
                
                Цена: Бесплатно
                """, product.getTitle(), product.getDescription());

        SendPhoto productMessage = SendPhoto.builder()
                .chatId(profile.getChatId())
                .caption(messageText)
                .photo(resolveInputFile(product.getImageUrl(), "image/shop.png"))
                .replyMarkup(KeyboardFactory.withProductBuy())
                .build();
        deleteMessageId(profile, sender);

        try {
            var savedMessage = sender.execute(productMessage);
            userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
        } catch (TelegramApiRequestException e) {
            if (e.getMessage().contains("wrong remote file identifier specified: Wrong padding in the string")) {
                SendPhoto productExceptionMessage = SendPhoto.builder()
                        .chatId(profile.getChatId())
                        .caption(messageText)
                        .photo(new InputFile(readFileFromResources("image/shop.png")))
                        .replyMarkup(KeyboardFactory.withProductBuy())
                        .build();
                var savedMessage = sender.execute(productExceptionMessage);
                userService.saveLastMessageId(profile.getChatId(), savedMessage.getMessageId());
            }
        }
    }

    private void deleteMessageId(Profile profile, AbsSender sender) throws TelegramApiException {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(profile.getChatId())
                .messageId(profile.getLastMessageId())
                .build();
        sender.execute(deleteMessage);
    }

    private InputFile resolveInputFile(String imageUrl, String defaultFilePath) {
        if (imageUrl == null || imageUrl.isBlank()) {
            log.warn("Image url is not support. Resolve to default image");
            return new InputFile(readFileFromResources(defaultFilePath));
        }
        return new InputFile(imageUrl);
    }

    private File readFileFromResources(String filePath) {
        return new File("src/main/resources/" + filePath);
    }

    private String getTextFrom(File file, String defaultText) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            StringBuilder builder = new StringBuilder(defaultText + "\n");
            String st;
            while ((st = br.readLine()) != null) {
                builder.append(st);
            }
            return builder.toString();
        } catch (IOException e) {
            return defaultText;
        }
    }
}
