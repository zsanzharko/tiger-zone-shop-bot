package kz.tiger.zone.tigerzoneshopbot.bot.utils;

import kz.tiger.zone.tigerzoneshopbot.bot.command.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kz.tiger.zone.tigerzoneshopbot.bot.config.TelegramMessageConfig.CALLBACK_DELIMITER;
import static kz.tiger.zone.tigerzoneshopbot.bot.config.TelegramMessageConfig.MESSAGE_COLUMNS;

public class KeyboardFactory {

    public static ReplyKeyboard withCategories(List<CategoryModel> categoryModelList) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button;

        for (int i = 0; i < categoryModelList.size(); i++) {
            CategoryModel model = categoryModelList.get(i);
            button = new InlineKeyboardButton();
            if (i % MESSAGE_COLUMNS == 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }
            button.setText(model.getTitle());
            button.setCallbackData("category" + CALLBACK_DELIMITER + model.getId());
            rowInline.add(button);
        }
        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard withMenu(Map<String, CallbackCommandType> menu) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button;
        int i = 0;
        for (Map.Entry<String, CallbackCommandType> typeEntry : menu.entrySet()) {
            button = new InlineKeyboardButton();
            if (i % MESSAGE_COLUMNS == 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }
            button.setText(typeEntry.getKey());
            button.setCallbackData(typeEntry.getValue().toString());
            rowInline.add(button);
            i++;
        }
        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}
