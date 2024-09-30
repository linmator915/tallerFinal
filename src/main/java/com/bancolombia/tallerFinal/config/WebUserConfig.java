package com.bancolombia.tallerFinal.config;

import com.bancolombia.tallerFinal.exceptions.Error400Exception;
import com.bancolombia.tallerFinal.services.interfaces.IPaymentRestUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class WebUserConfig {

    @Bean
    public IPaymentRestUser paymentRestUser(WebClient webClient){
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(IPaymentRestUser.class);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("http://localhost:8085")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, userResponse -> {
                    return userResponse.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new Error400Exception(error)));
                })
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, userResponse -> {
                    return userResponse.bodyToMono(String.class)
                            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                            .flatMap(error -> Mono.error(new RuntimeException(error)));
                })
                .build();
    }
}
