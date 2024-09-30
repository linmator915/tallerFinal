package com.bancolombia.tallerFinal.services.interfaces;

import com.bancolombia.tallerFinal.domain.Cashout;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IPaymentRestUser {
    @PostExchange("/payment")
    Mono<String> guardarPayment(@RequestBody Cashout cashout);
}
