package kz.tiger.zone.tigerzoneshopbot.repository;

import kz.tiger.zone.tigerzoneshopbot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByIsBlockedFalseAndTelegramChatId(Long chatId);

    Boolean existsByTelegramChatId(Long telegramChatId);
}
