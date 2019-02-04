package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.config.BambooProperties;
import cf.nirvandil.cibotmq.web.dto.BuildResultDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BambooClientTest {

    @Test
    public void getBuildResult() {
        RestTemplateBuilder builder = mock(RestTemplateBuilder.class);
        RestTemplate template = mock(RestTemplate.class);
        when(builder.basicAuthentication(anyString(), anyString()))
                .thenReturn(builder);
        when(builder.interceptors(any(ClientHttpRequestInterceptor.class)))
                .thenReturn(builder);
        when(builder.build())
                .thenReturn(template);
        when(template.getForObject(anyString(), any()))
                .thenReturn(BuildResultDTO.empty(123L));
        BambooClient client = new BambooClient(builder, new BambooProperties("1", "2", "localhost", 1));
        Optional<BuildResultDTO> result = client.getBuildResult("test", 123L);
        Assertions.assertThat(result.isPresent())
                .isTrue();
        when(template.getForObject(anyString(), any()))
                .thenThrow(new RuntimeException());
        Assertions.assertThat(client.getBuildResult("test", 123L).isPresent())
                .isFalse();
    }
}