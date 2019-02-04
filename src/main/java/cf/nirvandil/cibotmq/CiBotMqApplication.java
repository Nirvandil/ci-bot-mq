package cf.nirvandil.cibotmq;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@Slf4j
@EnableAdminServer
@SpringBootApplication
public class CiBotMqApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(CiBotMqApplication.class, args);
    }

}

