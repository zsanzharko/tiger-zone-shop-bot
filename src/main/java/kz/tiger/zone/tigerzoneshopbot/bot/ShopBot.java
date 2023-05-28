package kz.tiger.zone.tigerzoneshopbot.bot;

import kz.tiger.zone.tigerzoneshopbot.bot.command.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.command.CommandResolver;
import kz.tiger.zone.tigerzoneshopbot.bot.command.CommandType;
import kz.tiger.zone.tigerzoneshopbot.bot.service.category.TelegramCategoryService;
import kz.tiger.zone.tigerzoneshopbot.bot.service.menu.TelegramMenuService;
import kz.tiger.zone.tigerzoneshopbot.bot.service.menu.TelegramMenuServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class ShopBot extends TelegramLongPollingBot {
    private final String botUsername;

    private final CommandResolver commandResolver = new CommandResolver();
    private final TelegramCategoryService categoryService;
    private final TelegramMenuService menuService;

    public ShopBot(@Value("${bot.username}") String botUsername,
                   @Value("${bot.token}") String botToken,
                   TelegramCategoryService categoryService) {
        super(botToken);
        this.botUsername = botUsername;
        this.categoryService = categoryService;
        this.menuService = new TelegramMenuServiceImpl();
    }

    @SneakyThrows(TelegramApiException.class)
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                CommandType commandType = commandResolver.resolve(message.getText());

                switch (commandType) {
                    case MENU -> {
                        menuService.sendMenu(String.valueOf(message.getChatId()), this);
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callback = update.getCallbackQuery();

            CallbackCommandType commandType = commandResolver.resolveCallback(callback.getData());

            switch (commandType) {
                case SHOP -> {
                    categoryService.sendCategories(String.valueOf(callback.getMessage().getChatId()), this);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
