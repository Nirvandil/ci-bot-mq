package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.config.AppCheckProperties;
import cf.nirvandil.cibotmq.service.IApplicationChecker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Objects;

@Slf4j
@Service
public class EgipDevApplicationChecker implements IApplicationChecker {
    private final RestTemplate restTemplate;
    private final AppCheckProperties properties;

    @Autowired
    public EgipDevApplicationChecker(RestTemplateBuilder builder, AppCheckProperties properties) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(properties.getCheckUrl()));
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Override
    @SneakyThrows
    public boolean isUp() {
        Integer waitSeconds = properties.getWaitInterval();
        log.debug("Sleeping for {} seconds before shouldCheck", waitSeconds);
        Thread.sleep(waitSeconds * 1000L);
        String checkPath = properties.getCheckPath();
        log.debug("Checking url {} for UP answer", properties.getCheckUrl() + checkPath);
        String answer;
        try {
            answer = restTemplate.getForObject(checkPath, String.class);
        } catch (Exception e) {
            log.error("Can't check application status", e);
            return false;
        }
        return Objects.requireNonNull(answer).contains("UP");
    }
}
