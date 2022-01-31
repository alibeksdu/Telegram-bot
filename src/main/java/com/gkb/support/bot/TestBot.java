package com.gkb.support.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class TestBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "MkbSupportBot";
    }

    @Override
    public String getBotToken() {
        return "5049215815:AAHbKBMP-1sX7wWeNV1DbNU0Le7S8bgCUmE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();

        if (update.hasMessage()) {
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            sendMessage.setText(showInfo(update.getMessage(), sendMessage));

            try {
                try {
                    execute(sendMessage);
                } catch (TelegramApiRequestException e) {
                    sendMessage.setText("You are sent text too long time, pls retry after 2 minutes");
                    execute(sendMessage);
                }

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            String text = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            sendMessage.setText(infoCallback(text, sendMessage, update.getMessage()));
            sendMessage.setChatId(chatId + "");
            try {
                try {
                    execute(sendMessage);
                } catch (TelegramApiRequestException e) {
                    sendMessage.setText("You are sent text too long time, pls retry after 2 minutes");
                    execute(sendMessage);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String showInfo(Message message, SendMessage sendMessage) {
        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                String text = "Здравствуйте, " + message.getFrom().getFirstName() + "!\n" +
                        "1) Ошибка загрузки пакетов\n" +
                        "2) Ошибка доступа БДКИ\n" +
                        "3) Контракт не корректный\n" +
                        "4) Вопрос четвертый";
                setInline(sendMessage);
                return text;
            }
        }
        return "I dont understand you! \nOkay bro, I will help you \nJust send me this command: /help";
    }

    private void setInline(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("1");
        inlineKeyboardButton.setCallbackData("1");
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("2");
        inlineKeyboardButton2.setCallbackData("2");
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("3");
        inlineKeyboardButton3.setCallbackData("3");
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("4");
        inlineKeyboardButton4.setCallbackData("4");

        inlineKeyboardButtons.add(inlineKeyboardButton);
        inlineKeyboardButtons.add(inlineKeyboardButton2);
        inlineKeyboardButtons.add(inlineKeyboardButton3);
        inlineKeyboardButtons.add(inlineKeyboardButton4);


        buttons.add(inlineKeyboardButtons);

        inlineKeyboardMarkup.setKeyboard(buttons);
    }

    private void setAnswerInline(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons2 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons3 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons4 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Да");
        inlineKeyboardButton.setCallbackData("yes");
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Нет");
        inlineKeyboardButton2.setCallbackData("no");
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Отправить специалисту");
        inlineKeyboardButton3.setCallbackData("sendSpec");
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("Начать заново");
        inlineKeyboardButton4.setCallbackData("again");

        inlineKeyboardButtons.add(inlineKeyboardButton);
        inlineKeyboardButtons2.add(inlineKeyboardButton2);
        inlineKeyboardButtons3.add(inlineKeyboardButton3);
        inlineKeyboardButtons4.add(inlineKeyboardButton4);


        buttons.add(inlineKeyboardButtons);
        buttons.add(inlineKeyboardButtons2);
        buttons.add(inlineKeyboardButtons3);
        buttons.add(inlineKeyboardButtons4);

        inlineKeyboardMarkup.setKeyboard(buttons);
    }

    private String infoCallback(String text, SendMessage sendMessage, Message message) {
        if (text.equals("1")) {
            String answer = "Ответ 1го вопроса" + "\n\n\nОтвет был полезным?";
            setAnswerInline(sendMessage);
            return answer;
        }
        if (text.equals("2")) {
            String answer = "Ответ 2го вопроса" + "\n\n\nОтвет был полезным?";
            setAnswerInline(sendMessage);
            return answer;
        }
        if (text.equals("3")) {
            String answer = "Ответ 3го вопроса" + "\n\n\nОтвет был полезным?";
            setAnswerInline(sendMessage);
            return answer;
        }
        if (text.equals("yes") || text.equals("no")) {
            String answer = "Спасибо за ваш ответ!";
            return answer;
        }
        if (text.equals("sendSpec")) {
            String answer = "Ваш запрос был отправлен!";
            return answer;
        }
        if (text.equals("again")) {
            String answer = "/start";
            return answer;
        }
        return text;
    }

    public static void main(String[] args) throws TelegramApiException {
        TestBot bot = new TestBot();
        TelegramBotsApi t = new TelegramBotsApi(DefaultBotSession.class);
        t.registerBot(bot);
    }
}
