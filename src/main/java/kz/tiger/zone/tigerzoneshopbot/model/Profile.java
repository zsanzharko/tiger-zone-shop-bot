package kz.tiger.zone.tigerzoneshopbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private Long chatId;
    private String username;
    private Integer lastMessageId;

    public Profile(Long chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }
}
