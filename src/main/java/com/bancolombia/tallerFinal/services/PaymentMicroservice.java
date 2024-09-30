package com.bancolombia.tallerFinal.services;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.exceptions.Error400Exception;
import com.bancolombia.tallerFinal.services.interfaces.IPaymentRestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class PaymentMicroservice{

    private final IPaymentRestUser iPaymentRestUser;

    @Autowired
    public PaymentMicroservice(IPaymentRestUser iPaymentRestUser) {
        this.iPaymentRestUser = iPaymentRestUser;
    }

    /*public Mono<String> guardarPayment(Cashout cashout) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
        return webClient.post()
                .bodyValue(cashout)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(savedCashout -> {
                    System.out.println("Approved");
                });
    }*/

    public Function<Cashout, Mono<String>> hacerPayment() {
        return cashout -> iPaymentRestUser.guardarPayment(cashout)
                .doOnSuccess(response -> System.out.println("Approved"))
                .doOnError(throwable -> System.err.println("Rejected"));
    }
}
