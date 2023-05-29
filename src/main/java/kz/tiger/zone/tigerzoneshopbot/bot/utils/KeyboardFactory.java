package kz.tiger.zone.tigerzoneshopbot.bot.utils;

import kz.tiger.zone.tigerzoneshopbot.bot.command.CallbackCommandType;
import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;
import kz.tiger.zone.tigerzoneshopbot.model.ItemModel;
import kz.tiger.zone.tigerzoneshopbot.model.ShopModel;
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
        List<List<InlineKeyboardButton>> rowsInline = setRowsInline(categoryModelList);
        return getBackRowKeyboard(inlineKeyboard, rowsInline, CallbackCommandType.BACK_MENU);
    }

    public static ReplyKeyboard withProducts(List<ItemModel> itemModelList) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = setRowsInline(itemModelList);
        return getBackRowKeyboard(inlineKeyboard, rowsInline, CallbackCommandType.SHOP);
    }

    private static ReplyKeyboard getBackRowKeyboard(InlineKeyboardMarkup inlineKeyboard,
                                                    List<List<InlineKeyboardButton>> rowsInline,
                                                    CallbackCommandType commandType) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("🔙 Назад");
        button.setCallbackData(commandType.toString());
        rowInline.add(button);
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

    public static ReplyKeyboard withBackToMenu() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        return getBackRowKeyboard(inlineKeyboard, rowsInline, CallbackCommandType.BACK_MENU);
    }


    private static List<List<InlineKeyboardButton>> setRowsInline(List<? extends ShopModel> categoryModelList) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button;

        for (int i = 0; i < categoryModelList.size(); i++) {
            var model = categoryModelList.get(i);
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
        return rowsInline;
    }
}
