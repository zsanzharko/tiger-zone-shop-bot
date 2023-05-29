package kz.tiger.zone.tigerzoneshopbot.bot.service;

import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramGeneralService {
    Profile registerUser(Long chatId, String userName);

    void sendMenu(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendFAQ(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendGuarantee(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendReview(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendSupport(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendBackEditMenu(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendProfile(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendCategories(Profile profile, AbsSender sender) throws TelegramApiException;

    void sendItemsInCategory(Profile profile, Integer categoryId, AbsSender sender) throws TelegramApiException;
}
