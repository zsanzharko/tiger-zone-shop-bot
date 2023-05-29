package kz.tiger.zone.tigerzoneshopbot.service.user;

import kz.tiger.zone.tigerzoneshopbot.model.Profile;

public interface UserService {

    Profile registerUser(Profile newProfile);

    Profile getUser(Long chatId);

    void saveLastMessageId(Long chatId, Integer sentMessageId);
}
