package cf.nirvandil.cibotmq.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("app")
public class AppCheckProperties {
    private String checkUrl;
    private String checkPath;
    private Integer waitInterval;
}
