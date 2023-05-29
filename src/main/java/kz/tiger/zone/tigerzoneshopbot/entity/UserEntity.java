package kz.tiger.zone.tigerzoneshopbot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "telegram_chat_id")
    private Long telegramChatId;
    @Column(name = "telegram_username")
    private String telegramUsername;
    @Column(name = "telegram_last_message_id")
    private Integer telegramLastMessageId;
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false;

    public UserEntity(Long chatId, String username) {
        this.telegramChatId = chatId;
        this.telegramUsername = username;
    }
}
