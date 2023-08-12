package hu.webuni.config;

import hu.webuni.webclientservice.WebClientService;
import hu.webuni.webclientservice.WebClientServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LibConfiguration {

    private final WebClient webClient;

    public LibConfiguration(WebClient webClient) {
        this.webClient = webClient;
    }

    @Bean
    public WebClientServiceInterface webClientService() {
        return new WebClientService(webClient);
    }


}
