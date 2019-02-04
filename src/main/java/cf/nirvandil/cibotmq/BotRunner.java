package cf.nirvandil.cibotmq;

import cf.nirvandil.cibotmq.service.impl.CiBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotRunner {
    private final CiBot bot;
    private final TelegramBotsApi botsApi;

    @PostConstruct
    private void init() {
        log.info("Registering telegram bot");
        CompletableFuture.supplyAsync(() -> {
            try {
                return botsApi.registerBot(bot);
            } catch (TelegramApiRequestException e) {
                log.error("Can't register bot!", e);
                throw new RuntimeException(e);
            }
        }).thenAccept(session -> log.debug("Telegram bot registered, session is running - {}", session.isRunning()));
    }
}