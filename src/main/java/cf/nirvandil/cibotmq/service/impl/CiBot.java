package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.config.BotProperties;
import cf.nirvandil.cibotmq.service.ICiBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class CiBot extends TelegramLongPollingBot implements ICiBot {
    private final BotProperties botProperties;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update {}", update.toString());
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUserName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public boolean sendToDevChat(String message) {
        String chatId = botProperties.getChatId();
        log.debug("Sending message\n {} \nto dev chat {}", message, chatId);
        try {
            sendApiMethod(new SendMessage(chatId, message));
            log.debug("Message sent successfully to {}", chatId);
            return true;
        } catch (Exception  e) {
            log.error("Can't send message to chat {}", chatId, e);
            return false;
        }
    }
}
