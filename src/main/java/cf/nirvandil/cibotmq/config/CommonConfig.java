package cf.nirvandil.cibotmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
public class CommonConfig {

    @Bean
    public TelegramBotsApi botsApi() {
        return new TelegramBotsApi();
    }

}
