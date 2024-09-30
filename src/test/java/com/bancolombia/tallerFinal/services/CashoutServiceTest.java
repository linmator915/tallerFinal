package com.bancolombia.tallerFinal.services;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.domain.User;
import com.bancolombia.tallerFinal.domain.repositories.CashoutRepository;
import com.bancolombia.tallerFinal.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class CashoutServiceTest {

    @InjectMocks
    CashoutService cashoutService;
    @InjectMocks
    UserService userService;

    @Mock
    private CashoutRepository cashoutRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testcrearCashout(){

        Long userId = 1L;

        User userInput = new User();
        userInput.setId(1L);
        userInput.setNombre("Pepita Perez");
        userInput.setBalance(300.50);

        Cashout cashoutInput = new Cashout();
        cashoutInput.setId(123L);
        cashoutInput.setUserId(1L);
        cashoutInput.setAmount(200.50);

        Cashout cashoutOutput = new Cashout();
        cashoutOutput.setId(123L);
        cashoutOutput.setUserId(1L);
        cashoutOutput.setAmount(200.50);

        when(userRepository.findByUserId(userId)).thenReturn(Mono.just(userInput));
        when(userService.obtenerPorId(userId)).thenReturn(Mono.just(userInput));
        when(userService.validarBalance(userInput.getId(),cashoutInput)).thenReturn(Mono.just("OK"));
        when(userService.updateBalance(userInput.getId(),cashoutInput)).thenReturn(Mono.just(userInput));
        when(cashoutRepository.save(cashoutInput)).thenReturn(Mono.just(cashoutOutput));

        StepVerifier.create(cashoutService.crearCashout(cashoutInput))
                .expectNext(cashoutOutput)
                .verifyComplete();
    }

    @Test
    public void testObtenerCashoutsPorUser() {

        Long userId = 1L;

        Cashout cashoutInput1 = new Cashout();
        cashoutInput1.setId(123L);
        cashoutInput1.setUserId(1L);
        cashoutInput1.setAmount(200.50);

        Cashout cashoutInput2 = new Cashout();
        cashoutInput1.setId(345L);
        cashoutInput2.setUserId(1L);
        cashoutInput2.setAmount(200.50);

        when(cashoutRepository.findByUserId(userId)).thenReturn(Flux.just(cashoutInput1, cashoutInput2));

        StepVerifier.create(cashoutService.obtenerCashoutsPorUser(1L))
                .expectNext(cashoutInput1)
                .expectNext(cashoutInput2)
                .verifyComplete();
    }
}
