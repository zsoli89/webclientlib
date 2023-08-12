package hu.webuni.webclientservice;

import hu.webuni.config.LibConfiguration;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class WebClientService implements WebClientServiceInterface{

    private static final Logger logger = LoggerFactory.getLogger(WebClientService.class);
    private static final String BASE_URL = "http://localhost:%s/";
    private static final String CALL_INFO = "Call {%s}";
    public static final String INTERNAL_ERROR = "Error during call url {%s} : {%s}";

    private final WebClient webClient;

    public WebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String callGetMicroservice(String port, String endpoint) {
        String url = BASE_URL.formatted(port) + endpoint;
        logger.info(CALL_INFO.formatted(url));
        try {
            return webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException exception) {
            throw new RuntimeException(INTERNAL_ERROR.formatted(endpoint, exception.getResponseBodyAsString()));
        }
    }

    public Long callPostMicroservice(String port, String endpoint, String object) {
        String url = BASE_URL.formatted(port) + endpoint;
        logger.info(CALL_INFO.formatted(url));
        try {
            String response = webClient
                    .post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(object), String.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return new JSONObject(response).getLong("id");
        } catch (WebClientResponseException exception) {
            throw new RuntimeException(INTERNAL_ERROR.formatted(url, exception.getResponseBodyAsString()));
        }
    }

}
