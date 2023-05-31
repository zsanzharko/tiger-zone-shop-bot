package kz.tiger.zone.tigerzoneshopbot.bot;

import kz.tiger.zone.tigerzoneshopbot.bot.command.CommandResolver;
import kz.tiger.zone.tigerzoneshopbot.bot.enums.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.enums.CommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.enums.MessageType;
import kz.tiger.zone.tigerzoneshopbot.bot.service.TelegramGeneralService;
import kz.tiger.zone.tigerzoneshopbot.bot.service.TelegramGeneralServiceImpl;
import kz.tiger.zone.tigerzoneshopbot.service.category.ShopCategoryService;
import kz.tiger.zone.tigerzoneshopbot.service.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

import static kz.tiger.zone.tigerzoneshopbot.bot.config.TelegramMessageConfig.CALLBACK_DELIMITER;

@Component
@Slf4j
public class ShopBot extends TelegramLongPollingBot {
    private final String botUsername;

    private final CommandResolver commandResolver = new CommandResolver();
    private final TelegramGeneralService generalService;

    public ShopBot(@Value("${bot.username}") String botUsername,
                   @Value("${bot.token}") String botToken,
                   ShopCategoryService categoryService,
                   UserService userService) {
        super(botToken);
        this.botUsername = botUsername;
        this.generalService = new TelegramGeneralServiceImpl(categoryService, userService);
    }

    @SneakyThrows(TelegramApiException.class)
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            var profile = generalService.registerUser(message.getChatId(), message.getFrom().getUserName());
            if (message.hasText()) {
                CommandType commandType = commandResolver.resolve(message.getText());

                if (Objects.requireNonNull(commandType) == CommandType.MENU) {
                    generalService.sendMenu(profile, this, MessageType.MESSAGE);
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callback = update.getCallbackQuery();
            var profile = generalService.registerUser(callback.getMessage().getChatId(), callback.getFrom().getUserName());
            CallbackCommandType commandType = commandResolver.resolveCallback(callback.getData());
            // FIXME: 5/29/2023 CallbackCommandType.ordinal()" because "commandType" is null
            //  can invoke when command has not in commandType
            if (commandType != null) {
                switch (commandType) {
                    case FAQ -> generalService.sendFAQ(profile, this);
                    case GUARANTEE -> generalService.sendGuarantee(profile, this);
                    case REVIEWS -> generalService.sendReview(profile, this);
                    case SUPPORT -> generalService.sendSupport(profile, this);
                    case BACK_MENU -> generalService.sendBackEditMenu(profile, this);
                    case PROFILE -> generalService.sendProfile(profile, this);
                    case SHOP -> generalService.sendCategories(profile, this);
                }
                return;
            }
            if (callback.getData()
                    .substring(0, callback.getData().indexOf(CALLBACK_DELIMITER))
                    .contains("category")) {
                Integer categoryId = Integer.valueOf(callback.getData().substring(
                        callback.getData().indexOf(CALLBACK_DELIMITER) + 1));
                generalService.sendItemsInCategory(profile, categoryId, this);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
