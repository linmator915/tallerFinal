package com.bancolombia.tallerFinal.services;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.domain.repositories.CashoutRepository;
import com.bancolombia.tallerFinal.domain.repositories.UserRepository;
import com.bancolombia.tallerFinal.exceptions.CashoutsException;
import com.bancolombia.tallerFinal.services.interfaces.IPaymentRestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class CashoutService{

    @Autowired
    private final CashoutRepository cashoutRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final PaymentMicroservice paymentMicroservice;

    @Autowired
    public CashoutService(CashoutRepository cashoutRepository, UserService userService, PaymentMicroservice paymentMicroservice) {
        this.cashoutRepository = cashoutRepository;
        this.userService = userService;
        this.paymentMicroservice = paymentMicroservice;
    }

    public Mono<Cashout> crearCashout(Cashout cashout) {
        return userService.obtenerPorId(cashout.getUserId())
            .flatMap(user ->
                userService.validarBalance(user.getId(), cashout)
                    .flatMap(validationMessage -> {
                        paymentMicroservice.hacerPayment();
                        return Mono.just(cashout);
                    })
                    .flatMap(paymentResponse -> {
                    return userService.updateBalance(user.getId(), cashout)
                        .flatMap(updatedUser -> {
                            return cashoutRepository.save(cashout)
                                    .doOnSuccess(savedCashout -> System.out.println("Cashout creado exitosamente: " + savedCashout));
                        });
                    }))
                    .onErrorResume(throwable -> {
                        System.err.println("Error en la creación del cashout: " + throwable.getMessage());
                        return Mono.error(throwable);
                    });
    }

    public Flux<Cashout> obtenerCashoutsPorUser(Long id) {
        return cashoutRepository.findByUserId(id)
                .switchIfEmpty(Mono.error(new CashoutsException("El cliente no tiene debitos")));
    }

}
