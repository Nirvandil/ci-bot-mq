package cf.nirvandil.cibotmq.service.impl;

import cf.nirvandil.cibotmq.config.BambooProperties;
import cf.nirvandil.cibotmq.service.IBambooClient;
import cf.nirvandil.cibotmq.web.dto.BuildResultDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
public class BambooClient implements IBambooClient {

    private final RestTemplate restTemplate;
    private final BambooProperties properties;

    @Autowired
    public BambooClient(RestTemplateBuilder builder, BambooProperties properties) {
        this.properties = properties;
        RestTemplate template = builder
                .basicAuthentication(properties.getUser(), properties.getPassword())
                .interceptors((r, b, e) -> {
                    HttpHeaders headers = r.getHeaders();
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
                    return e.execute(new HttpRequestWrapper(r) {
                        @Override
                        public URI getURI() {
                            URI uri = super.getURI();
                            return UriComponentsBuilder.fromUri(uri)
                                    .queryParam("expand", "changes.change.files")
                                    .queryParam("os_authType", "basic")
                                    .build().toUri();
                        }
                    }, b);
                })
                .build();
        template.setUriTemplateHandler(new DefaultUriBuilderFactory(properties.getBaseUrl()));
        this.restTemplate = template;
    }

    @Override
    @SneakyThrows
    public Optional<BuildResultDTO> getBuildResult(String buildName, Long buildNum) {
        log.debug("Received request for checking build result of {} number {}", buildName, buildNum);
        Thread.sleep(properties.getDelaySeconds() * 1000L);
        BuildResultDTO resultDTO;
        try {
            resultDTO = restTemplate.getForObject("/" + buildName + "/" + buildNum, BuildResultDTO.class);
        } catch (Exception e) {
            log.error("Can't get build result for build {} number {}: {}", buildName, buildNum, e);
            return Optional.empty();
        }
        return Optional.ofNullable(resultDTO);
    }
}
