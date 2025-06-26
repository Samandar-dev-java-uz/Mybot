package uz.pdp.model;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.model.User.*;

public  class Mybot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        System.out.println(chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (!message.hasText()) return;
        String text = message.getText();
        if (text != null && !text.isEmpty()) {
            UserInformation userInformation = listUser.getOrDefault(chatId,
                    UserInformation.builder()
                            .chadId(chatId)
                            .state(START)
                            .build());

            String state = userInformation.getState();

            switch (state) {
                case User.START:
                    if (text.equals("/start")) {
                        sendMessage.setText("Enter your first name:");
                        userInformation.setState(ENTER_FIRSTNAME);
                        listUser.put(chatId, userInformation);
                    }
                    break;

                case User.ENTER_FIRSTNAME:
                    userInformation.setName(text);
                    listUser.put(chatId, userInformation);
                    sendMessage.setText("Enter your last name:");
                    userInformation.setState(ENTER_LASTNAME);
                    listUser.put(chatId, userInformation);
                    break;

                case User.ENTER_LASTNAME:
                    userInformation.setLastname(text);
                    sendMessage.setText("Enter age" );
                    userInformation.setState(ENTER_AGE);
                    listUser.put(chatId, userInformation);
                    break;
                case ENTER_AGE:
                    try{
                        int age = Integer.parseInt(text);
                        userInformation.setAge(age);
                        sendMessage.setText("Enter your phone ");
                        userInformation.setState(ENTER_PHONE_NUMBER);
                        listUser.put(chatId,userInformation);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case ENTER_PHONE_NUMBER:
                    userInformation.setPhoneNumber(text);
                    sendMessage.setText("Enter password ");
                    userInformation.setState(ENTER_PASSWORD);
                    listUser.put(chatId,userInformation);
                    break;

                case ENTER_PASSWORD:
                    userInformation.setPassword(text);
                    sendMessage.setText(" you successful register ");
                    userInformation.setState(START);
                    sendMessage.setText(
                            "your name -> "+userInformation.getName() +"\n"+
                                    "your lastname -> "+ userInformation.getLastname() +"\n"+
                                    "your age ->"+ userInformation.getAge()+"\n"+
                                    "your phone number ->" + userInformation.getPhoneNumber()+"\n"+
                                    "your password ->"+ userInformation.getPassword() );
                    listUser.put(chatId,userInformation);
                    break;

            }

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {
            sendMessage.setText("Xatolik: matn topilmadi.");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }



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
