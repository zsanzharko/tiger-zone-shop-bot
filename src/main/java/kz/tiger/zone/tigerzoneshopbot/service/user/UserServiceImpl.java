package kz.tiger.zone.tigerzoneshopbot.service.user;

import kz.tiger.zone.tigerzoneshopbot.entity.UserEntity;
import kz.tiger.zone.tigerzoneshopbot.model.Profile;
import kz.tiger.zone.tigerzoneshopbot.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepository;

    @Override
    public Profile registerUser(Profile newProfile) {
        if (newProfile.getChatId() == null) throw new RuntimeException("User telegram id is null");
        if (userRepository.existsByTelegramChatId(newProfile.getChatId())) {
            var eUser = userRepository.findUserEntityByIsBlockedFalseAndTelegramChatId(newProfile.getChatId());
            if (eUser == null) throw new RuntimeException("User is blocked");
            Profile profile = new Profile();
            profile.setChatId(eUser.getTelegramChatId());
            profile.setUsername(eUser.getTelegramUsername());
            profile.setLastMessageId(eUser.getTelegramLastMessageId());
            return profile;
        }
        UserEntity user = new UserEntity();
        user.setId(null);
        user.setIsBlocked(false);
        user.setTelegramChatId(newProfile.getChatId());
        user.setTelegramUsername(newProfile.getUsername());
        user.setTelegramLastMessageId(newProfile.getLastMessageId());
        var sUser = userRepository.save(user);
        Profile profile = new Profile();
        profile.setChatId(sUser.getTelegramChatId());
        profile.setUsername(sUser.getTelegramUsername());
        return profile;
    }

    @Override
    public Profile getUser(Long chatId) {
        var sUser = userRepository.findUserEntityByIsBlockedFalseAndTelegramChatId(chatId);
        return new Profile(
                sUser.getTelegramChatId(),
                sUser.getTelegramUsername(),
                sUser.getTelegramLastMessageId());
    }

    @Override
    public void saveLastMessageId(Long chatId, Integer sentMessageId) {
        if (userRepository.existsByTelegramChatId(chatId)) {
            var user = userRepository.findUserEntityByIsBlockedFalseAndTelegramChatId(chatId);
            user.setTelegramLastMessageId(sentMessageId);
            userRepository.save(user);
        }
    }
}
