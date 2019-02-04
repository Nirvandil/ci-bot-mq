package cf.nirvandil.cibotmq.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("bamboo")
public class BambooProperties {
    private String user;
    private String password;
    private String baseUrl;
    private Integer delaySeconds;
}
