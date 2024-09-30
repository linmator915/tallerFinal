package com.bancolombia.tallerFinal.controllers;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.services.CashoutService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CashoutController.class, CashoutService.class, GlobalHandleError.class})
@WebFluxTest(CashoutController.class)
public class CashoutControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private CashoutService cashoutService;

    @InjectMocks
    private CashoutController cashoutController;

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(cashoutController).build();
    }*/

    @Test
    void crearCashout() {

        Cashout cashoutInput = new Cashout();
        cashoutInput.setUserId(1L);
        cashoutInput.setAmount(1200.0);

        Cashout cashoutIOutput = new Cashout();
        cashoutIOutput.setUserId(1L);
        cashoutIOutput.setAmount(1200.0);

        when(cashoutService.crearCashout(cashoutInput)).thenReturn(Mono.just(cashoutIOutput));

        webTestClient.post()
                .uri("/cashouts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cashoutInput)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cashout.class);
                //.isEqualTo(cashout);
    }

    @Test
    void obtenerPorId(){

        Long userId = 1L;
        Cashout cashout1 = new Cashout();
        cashout1.setId(123L);
        cashout1.setUserId(1L);
        cashout1.setAmount(2300.0);

        Cashout cashout2 = new Cashout();
        cashout2.setId(345L);
        cashout2.setUserId(1L);
        cashout2.setAmount(2600.0);

        when(cashoutService.obtenerCashoutsPorUser(userId))
                .thenReturn(Flux.just(cashout1, cashout2));

        webTestClient.get()
                .uri("/cashouts/user/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cashout.class)
                .hasSize(2);
                //.contains(cashout1, cashout2);
    }
}
