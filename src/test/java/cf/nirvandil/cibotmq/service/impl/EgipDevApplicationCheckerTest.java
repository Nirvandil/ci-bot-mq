package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.config.AppCheckProperties;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class EgipDevApplicationCheckerTest {

    @Test
    public void isUp() {
        RestTemplateBuilder mockBuilder = Mockito.mock(RestTemplateBuilder.class);
        RestTemplate mockTemplate = Mockito.mock(RestTemplate.class);
        Mockito.when(mockTemplate.getForObject(anyString(), any()))
                .thenReturn("{\"status\": \"UP\"}");
        Mockito.when(mockBuilder.build())
                .thenReturn(mockTemplate);
        EgipDevApplicationChecker checker = new EgipDevApplicationChecker(
                mockBuilder,
                new AppCheckProperties("http://localhost:8081", "/actuator/health", 1)
        );
        Assertions.assertThat(checker.isUp()).isTrue();
    }
}