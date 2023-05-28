package kz.tiger.zone.tigerzoneshopbot.bot.command;

public class CommandResolver {
    public CommandType resolve(String text) {
        if (text.equals("/start")) {
            return CommandType.MENU;
        }
        // FIXME: 5/28/2023 need create Exception
        return null;
    }

    public CallbackCommandType resolveCallback(String text) {
        try {
            return CallbackCommandType.valueOf(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
