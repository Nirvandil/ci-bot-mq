package cf.nirvandil.cibotmq.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("bot")
public class BotProperties {
    private String token;
    private String chatId;
    private String userName;
}
