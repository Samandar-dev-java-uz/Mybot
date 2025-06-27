package uz.pdp.model;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Mybot extends TelegramLongPollingBot {
    static Integer back = 1;
    SendPhoto photo = new SendPhoto();


    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            if (update.getMessage().hasContact()) {
                Contact contact = update.getMessage().getContact();
                System.out.println(contact.getFirstName() + "\t" + contact.getLastName());
                return;
            }

            if (update.getMessage().hasText()) {
                Message message = update.getMessage();
                Long chatId = message.getChatId();
                String text = message.getText();
                SendMessage sendMessage = getSendMessage(chatId, text);


                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private SendMessage getSendMessage(Long chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        switch (text) {
            case "/start" -> {
                sendMessage.setText(" hush kelibsiz Choy hona botga ");
                back = 2;
                addButten(sendMessage, ButtenList.meni);
            }
            case "\uD83D\uDCCB Meni" ->{
                sendMessage.setText("  select food or drink");
                back =3;
                addButten(sendMessage,ButtenList.foods);
            }
            case "osh" ->{

                photo.setChatId(chatId);
                photo.setCaption("Bu bizning mazali oshimiz 🍛");
                photo.setPhoto(new InputFile("https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Osh_plov.jpg/640px-Osh_plov.jpg"));
                try {
                    execute(photo);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                sendMessage.setText("select osh");
                addButten(sendMessage,ButtenList.osh);


            }
            case "shurva" ->{
                sendMessage.setText("  select osh");

                addButten(sendMessage,ButtenList.shurva);
            }
            case "qozon kabob" ->{
                sendMessage.setText("  select qozon kabob");

                addButten(sendMessage,ButtenList.qozonKabob);
            }
            case "manti" ->{
                sendMessage.setText("  select manti");
                addButten(sendMessage,ButtenList.manti);
            }
            case "somsa" ->{
                sendMessage.setText("  select  somsa ");

                addButten(sendMessage,ButtenList.somsa);
            }
            case "shashlik" ->{
                sendMessage.setText("  select shashlik");

                addButten(sendMessage,ButtenList.shashlik);
            }
            case "ichimliklar" ->{
                sendMessage.setText("  select  drink");

                addButten(sendMessage,ButtenList.ichimliklar);
            }
            case "\uD83D\uDD01 back" ->{
                if (back == 3) {
                    back = 2;
                    sendMessage.setText("Orqaga: menyuga qaytdingiz");
                    addButten(sendMessage, ButtenList.foods);
                } else if (back == 2) {
                    back = 1;
                    sendMessage.setText("Orqaga: asosiy menyuga qaytdingiz");
                    addButten(sendMessage, ButtenList.meni);
                } else {
                    sendMessage.setText("Siz bosh menyudasiz");
                    addButten(sendMessage, ButtenList.meni);
                }
            }

        }
        return sendMessage;
    }

    private void addButten(SendMessage sendMessage, String[][] meni) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        for (String[] strings : meni) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (String s : strings) {
                keyboardRow.add(s);
            }
            keyboardRowList.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

    }


    @Override
    public String getBotUsername() {
        return "mychoyhonabot.";
    }

    @Deprecated
    public String getBotToken() {
        return "8042024410:AAHphWiR33H-Q3o1cWEybfYFoWLmIzIGgus";
    }
}
