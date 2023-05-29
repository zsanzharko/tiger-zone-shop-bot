package kz.tiger.zone.tigerzoneshopbot.bot.service.profile;

import kz.tiger.zone.tigerzoneshopbot.bot.utils.KeyboardFactory;
import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import kz.tiger.zone.tigerzoneshopbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramProfileServiceImpl implements TelegramProfileService {
    private final UserService userService;

    @Override
    public Profile registerUser(Long chatId, String username) {
        Profile user = new Profile(chatId, username);
        return userService.registerUser(user);
    }

    @Override
    public void sendProfile(Profile profile, AbsSender sender) throws TelegramApiException {
        var keyboard = KeyboardFactory.withBackToMenu();

        final String profileText = String.format("""
                Профиль:
                Пользователь: %s
                """, profile.getUsername());

        EditMessageText message = EditMessageText.builder()
                .chatId(String.valueOf(profile.getChatId()))
                .messageId(profile.getLastMessageId())
                .text(profileText)
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

        sender.execute(message);
    }

    @Override
    public void saveLastMessageId(Long chatId, Integer sentMessageId) {
        userService.saveLastMessageId(chatId, sentMessageId);
    }
}
