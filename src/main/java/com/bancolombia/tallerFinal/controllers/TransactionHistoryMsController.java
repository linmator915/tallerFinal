package com.bancolombia.tallerFinal.controllers;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.services.CashoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Validated
@RestController
@RequestMapping("/transaction-history")
public class TransactionHistoryMsController {

    private final CashoutService cashoutService;

    @Autowired
    public TransactionHistoryMsController(CashoutService cashoutService) {
        this.cashoutService = cashoutService;
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getTransactions(@PathVariable("userId") Long id){
        return cashoutService.obtenerCashoutsPorUser(id);
    }
}
