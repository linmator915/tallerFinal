package com.bancolombia.tallerFinal.controllers;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.services.CashoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/cashouts")
public class CashoutController {

    @Autowired
    private final CashoutService cashoutService;

    @Autowired
    public CashoutController(CashoutService cashoutService) {
        this.cashoutService = cashoutService;
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getUserById(@PathVariable("userId") Long id){
        return cashoutService.obtenerCashoutsPorUser(id);
    }

    @PostMapping
    public Mono<Cashout> createCashout(@Valid @RequestBody Cashout cashout) {
        return cashoutService.crearCashout(cashout);
    }

}
